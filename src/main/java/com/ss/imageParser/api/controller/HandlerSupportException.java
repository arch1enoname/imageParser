package com.ss.imageParser.api.controller;

import com.ss.Except4Support;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

@ControllerAdvice(annotations = Controller.class)
@Slf4j
public class HandlerSupportException {
    private final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    private final String EXCEPTION_PAGE = "exceptionPage";
    private final Random random = new Random();

    @ExceptionHandler(Except4Support.class)
    public ModelAndView handleConstraintViolationException(Except4Support exception, Model model) {
        ModelAndView modelAndView = new ModelAndView(EXCEPTION_PAGE);
        Long exceptionId = random.nextLong();
        model.addAttribute(ERROR_MESSAGE_ATTRIBUTE, exception + "Id ошибки: " + exceptionId);
        log.error(exception.getMessage4Support());
        return modelAndView;
    }

}
