package com.ss.topt.api.controller;

import com.ss.topt.api.dtos.ImageDownloadRequestDto;
import com.ss.topt.service.ImageParserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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

    private final ImageParserService imageParserService;

    @Autowired
    public ImageParserController(ImageParserService imageParserService) {
        this.imageParserService = imageParserService;
    }

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("imageDownloadRequestDto", new ImageDownloadRequestDto());
        return "image-download-form";
    }

    @PostMapping
    public String processForm(@Valid @ModelAttribute ImageDownloadRequestDto imageDownloadRequestDto, Model model) {
        imageParserService.parseImages(imageDownloadRequestDto);

        model.addAttribute("successMessage", "Форма успешно отправлена!");
        return "image-download-form";
    }
}

