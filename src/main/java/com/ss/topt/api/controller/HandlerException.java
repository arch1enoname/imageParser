package com.ss.topt.api.controller;

import com.ss.topt.exception.IncorrectUrlFormatException;
import com.ss.topt.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(annotations = Controller.class)
public class HandlerException {

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleConstraintViolationException(ConstraintViolationException exception, Model model) {
        ModelAndView modelAndView = new ModelAndView("exceptionPage");
        model.addAttribute("errorMessage", "Некорректные значения: " + exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(IncorrectUrlFormatException.class)
    public ModelAndView handleIncorrectUrlFormatException(IncorrectUrlFormatException exception, Model model) {
        ModelAndView modelAndView = new ModelAndView("exceptionPage");
        model.addAttribute("errorMessage", "IncorrectUrlFormat: " + exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(IncorrectUrlFormatException exception, Model model) {
        ModelAndView modelAndView = new ModelAndView("exceptionPage");
        model.addAttribute("errorMessage", "IncorrectUrlFormat: " + exception.getMessage());
        return modelAndView;
    }
}
