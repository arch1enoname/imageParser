package com.ss.topt.service;

import com.ss.Except4Support;
import com.ss.topt.api.dtos.ImageDownloadRequestDto;
import com.ss.topt.exception.IncorrectUrlFormatException;
import com.ss.topt.exception.ResourceNotFoundException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ImageParserService {

    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
    private final String REFERRER = "https://www.google.com";
    private final String HTML_TAG = "img";
    private final String ATTRIBUTE = "abs:src";
    private final String PATH = "C:\\Users\\79053\\Desktop\\";

    public void parseImages(ImageDownloadRequestDto imageDownloadRequestDto) {

        String url = imageDownloadRequestDto.getUrl();
        String path = PATH + imageDownloadRequestDto.getFolderName();

        createDirectory(path);

        try (ExecutorService executor = Executors.newFixedThreadPool(10)) {

            Document doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .referrer(REFERRER)
                    .get();

            Elements images = doc.select(HTML_TAG);
            images = filterUniqueElements(images);

            for (Element image : images) {
                String imageUrl = image.attr(ATTRIBUTE);
                executor.submit(() -> downloadImage(imageUrl, path));
            }
            executor.shutdown();

            try {
                if (!executor.awaitTermination(30, TimeUnit.MINUTES)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {}
        } catch (MalformedURLException | IllegalArgumentException e) {
            throw new IncorrectUrlFormatException("Пользователь ввел url некорректного формата. " + url);
        } catch (NoSuchFileException e) {
            throw new Except4Support("ERR312", "Не удалось создать папку");
        } catch (HttpStatusException e) {
            throw new ResourceNotFoundException("agabuga");
        } catch (IOException e) {
            throw new Except4Support("ERR311", "Ошибка");
        }
    }


    private void downloadImage(String imageUrl, String path) {
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        fileName = removeAdditionalParameters(fileName);
        Path target = Paths.get(path, fileName);

        try {
            URL url = new URL(imageUrl);
            InputStream in = url.openStream();
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e){e.printStackTrace();}

    }

    private Elements filterUniqueElements(Elements elements) {
        Set<String> uniqueSrc = new HashSet<>();
        Elements uniqueImages = new Elements();

        for (Element image : elements) {
            String src = image.attr("src");
            if (uniqueSrc.add(src)) {
                uniqueImages.add(image);
            }
        }
        return uniqueImages;
    }

    private String removeAdditionalParameters(String fileName) {
        if (fileName.contains("?")) {
            return fileName.substring(0, fileName.indexOf("?"));
        }
        return fileName;
    }

    private void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
