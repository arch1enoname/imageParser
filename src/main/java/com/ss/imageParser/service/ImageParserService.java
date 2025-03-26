package com.ss.imageParser.service;

import com.ss.Except4Support;
import com.ss.imageParser.Msg;
import com.ss.imageParser.dtos.ImageDownloadRequestDto;
import com.ss.imageParser.confJs.ConfJsAppImageParser;
import com.ss.imageParser.confJs.ConfJsImageParser;
import com.ss.imageParser.exception.*;
import com.ss.imageParser.exception.support.CreatingFolderException;
import com.ss.imageParser.exception.support.ImageDownloadException;
import org.apache.hc.core5.http.HttpStatus;
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
    ConfJsAppImageParser config = ConfJsImageParser.getInstance().getApp();

    private final String USER_AGENT = config.getUserAgent();
    private final String REFERRER = config.getReferrer();
    private final String IMAGE_HTML_TAG = "img";
    private final String ATTRIBUTE = "abs:src";
    private final String DIRECTORY_PATH = config.getDirectory();
    private final int THREAD_COUNT = config.getThreadCount();
    private final String ADDITIONAL_PARAMETERS_MARK = "?";

    private final String ERROR_CREATING_FOLDER_CODE = "ERROR_CREATING_FOLDER_CODE";
    private final String ERROR_DOWNLOAD_PICTURE_CODE = "ERROR_DOWNLOAD_PICTURE_CODE";
    private final String ERROR_DOWNLOAD_PAGE_CODE = "ERROR_DOWNLOAD_PAGE_CODE";


    public void parseImages(ImageDownloadRequestDto imageDownloadRequestDto) throws IncorrectUrlFormatException, ForbiddenException, UnauthorizedException, ResourceNotFoundException {

        String url = imageDownloadRequestDto.getUrl();
        String path = DIRECTORY_PATH + imageDownloadRequestDto.getFolderName();
        createDirectory(path);

        try (ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT)) {

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
            throw new IncorrectUrlFormatException(Msg.i().getMessage((Msg.CODE_INCORRECT_URL_ERR)));
        } catch (NoSuchFileException e) {
            throw new CreatingFolderException(ERROR_CREATING_FOLDER_CODE, "Ошибка в процессе создания папки", e);
        } catch (HttpStatusException e) {
            handleHttpStatusException(e);
        } catch (IOException e) {
            throw new Except4Support(ERROR_DOWNLOAD_PAGE_CODE, "Ошибка при скачивании страницы", e);
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
            throw new Except4Support(ERROR_DOWNLOAD_PICTURE_CODE, "Ошибка при скачивании картинки.", e);
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
        if (fileName.contains(ADDITIONAL_PARAMETERS_MARK)) {
            return fileName.substring(0, fileName.indexOf(ADDITIONAL_PARAMETERS_MARK));
        }
        return fileName;
    }

    private void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            try {
                directory.mkdirs();
            } catch (RuntimeException e) {
                throw new Except4Support("ERR_CODE_004", "Ошибка при создании директории.", e);
            }
        }
    }

    private void handleHttpStatusException(HttpStatusException e) throws ForbiddenException, UnauthorizedException, ResourceNotFoundException {
        int statusCode = e.getStatusCode();
        String errorMessage = statusCode + " url: " + e.getUrl();

        if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
            throw new UnauthorizedException(Msg.i().getMessage(Msg.CODE_UNAUTHORIZED_HTTP_REQUEST));
        } else if (statusCode == HttpStatus.SC_FORBIDDEN) {
            throw new ForbiddenException(Msg.i().getMessage(Msg.CODE_FORBIDDEN_ERR));
        } else if (statusCode == HttpStatus.SC_NOT_FOUND) {
            throw new ResourceNotFoundException(Msg.i().getMessage(Msg.CODE_RESOURCE_NOT_FOUND_ERR));
        } else {
            throw new ImageDownloadException("ERR_CODE_005", "Ошибка при парсинге.", e);
        }
    }
}
