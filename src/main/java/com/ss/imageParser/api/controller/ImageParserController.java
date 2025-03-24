package com.ss.imageParser.api.controller;

import com.ss.imageParser.api.dtos.ImageDownloadRequestDto;
import com.ss.imageParser.exception.ForbiddenException;
import com.ss.imageParser.exception.IncorrectUrlFormatException;
import com.ss.imageParser.exception.ResourceNotFoundException;
import com.ss.imageParser.exception.UnauthorizedException;
import com.ss.imageParser.service.ImageParserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ss.imageParser.api.controller.ControllerBase.ROUTE_BASE;

@Controller
@RequestMapping("/image-parser")
public class ImageParserController {

    private final String IMAGE_DOWNLOAD_ATTRIBUTE = "imageDownloadRequestDto";
    private final String IMAGE_DOWNLOAD_FORM_PAGE = "image-download-form";
    private final String SUCCESS_PAGE = "success";


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
    public String processForm(@Valid @ModelAttribute ImageDownloadRequestDto imageDownloadRequestDto, Model model) throws ForbiddenException, UnauthorizedException, IncorrectUrlFormatException, ResourceNotFoundException {
        imageParserService.parseImages(imageDownloadRequestDto);
        return SUCCESS_PAGE;
    }
}

