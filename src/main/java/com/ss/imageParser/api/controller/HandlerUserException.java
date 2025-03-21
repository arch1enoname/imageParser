package com.ss.imageParser.api.controller;

import com.ss.ExceptInfoUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(annotations = Controller.class)
public class HandlerUserException {
    private final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    private final String EXCEPTION_PAGE = "exceptionPage";

    @ExceptionHandler(BindException.class)
    public ModelAndView handleBindException(BindException exception, Model model) {
        ModelAndView modelAndView = new ModelAndView(EXCEPTION_PAGE);
        model.addAttribute(ERROR_MESSAGE_ATTRIBUTE, "Некорректные значения: " + exception.getFieldError().getDefaultMessage());
        return modelAndView;
    }

    @ExceptionHandler(ExceptInfoUser.class)
    public ModelAndView handleIncorrectUrlFormatException(ExceptInfoUser exception, Model model) {
        ModelAndView modelAndView = new ModelAndView(EXCEPTION_PAGE);
        model.addAttribute(ERROR_MESSAGE_ATTRIBUTE, exception.getMessage());
        return modelAndView;
    }

}
