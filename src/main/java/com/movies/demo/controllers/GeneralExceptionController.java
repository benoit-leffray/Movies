package com.movies.demo.controllers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GeneralExceptionController {

    @ExceptionHandler(Exception.class)
    public String exception(Exception exception, Model model) {
        model.addAttribute("exception", exception);
        return "error/generalHandler";
    }
}