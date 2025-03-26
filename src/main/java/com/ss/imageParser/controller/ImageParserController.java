package com.ss.imageParser.controller;

import com.ss.ExceptInfoUser;
import com.ss.imageParser.dtos.ImageDownloadRequestDto;
import com.ss.imageParser.service.ImageParserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/image-parser")
public class ImageParserController {

    private final String IMAGE_DOWNLOAD_ATTRIBUTE = "imageDownloadRequestDto";
    private final String IMAGE_DOWNLOAD_FORM_PAGE = "image-download-form";
    private final String SUCCESS_PAGE = "success";
    private final String EXCEPTION_PAGE = "exception";
    private final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";


    private final ImageParserService imageParserService;

    @Autowired
    public ImageParserController(ImageParserService imageParserService) {
        this.imageParserService = imageParserService;
    }

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute(IMAGE_DOWNLOAD_ATTRIBUTE, new ImageDownloadRequestDto());
        return IMAGE_DOWNLOAD_FORM_PAGE;
    }

    @PostMapping
    public String processForm(@Valid @ModelAttribute ImageDownloadRequestDto imageDownloadRequestDto, Model model) {
        try {
            imageParserService.parseImages(imageDownloadRequestDto);
        } catch (ExceptInfoUser e) {
            model.addAttribute(ERROR_MESSAGE_ATTRIBUTE, e.getMessage());
            return EXCEPTION_PAGE;
        }
        return SUCCESS_PAGE;
    }
}

