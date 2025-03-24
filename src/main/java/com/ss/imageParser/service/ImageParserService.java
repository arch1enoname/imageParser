package com.ss.imageParser.service;

import com.ss.Except4Support;
import com.ss.imageParser.api.dtos.ImageDownloadRequestDto;
import com.ss.imageParser.confJs.ConfJsAppImageParser;
import com.ss.imageParser.confJs.ConfJsImageParser;
import com.ss.imageParser.exception.*;
import com.ss.imageParser.exception.support.CreatingFolderException;
import com.ss.imageParser.exception.support.ImageDownloadException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
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
    ConfJsAppImageParser config = ConfJsImageParser.getInstance().getApp();

    private final String USER_AGENT = config.getUserAgent();
    private final String REFERRER = config.getReferrer();
    private final String IMAGE_HTML_TAG = "img";
    private final String ATTRIBUTE = "abs:src";
    private String DIRECTORY_PATH = config.getDirectory();

    private final String ERROR_CREATING_FOLDER_CODE = "ERROR_CREATING_FOLDER_CODE";
    private final String ERROR_DOWNLOAD_PICTURE_CODE = "ERROR_DOWNLOAD_PICTURE_CODE";
    private final String ERROR_DOWNLOAD_PAGE_CODE = "ERROR_DOWNLOAD_PAGE_CODE";

    public void parseImages(ImageDownloadRequestDto imageDownloadRequestDto) {

        String url = imageDownloadRequestDto.getUrl();
        String path = DIRECTORY_PATH + imageDownloadRequestDto.getFolderName();
        createDirectory(path);

        try (ExecutorService executor = Executors.newFixedThreadPool(10)) {

            Document doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .referrer(REFERRER)
                    .get();

            Elements images = doc.select(IMAGE_HTML_TAG);
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
            } catch (InterruptedException e) {
            }
        } catch (MalformedURLException e) {
            throw new IncorrectUrlFormatException("Некорректный url: " + url);
        } catch (NoSuchFileException e) {
            throw new CreatingFolderException(ERROR_CREATING_FOLDER_CODE, "Ошибка в процессе создания папки");
        } catch (HttpStatusException e) {
            handleHttpStatusException(e);
        } catch (IOException e) {
            throw new Except4Support(ERROR_DOWNLOAD_PAGE_CODE, "Ошибка при скачивании страницы");
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
        } catch (IOException e) {
            throw new Except4Support(ERROR_DOWNLOAD_PICTURE_CODE, "Ошибка при скачивании картинки.");
         }

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

    private void handleHttpStatusException(HttpStatusException e) {
        int statusCode = e.getStatusCode();
        String errorMessage = statusCode + " url: " + e.getUrl();

        if (statusCode == 401) {
            throw new UnauthorizedException("Пользователь не авторизован. " + errorMessage);
        } else if (statusCode == 403) {
            throw new ForbiddenException("Доступ к запрашиваемому ресурсу запрещен");
        } else if (statusCode == 404) {
            throw new ResourceNotFoundException("Невозможно найти запрашиваемый ресурс. " + errorMessage);
        } else {
            throw new ImageDownloadException("Ошибка. ", errorMessage);
        }
    }
}
