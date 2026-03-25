package com.marcoswebproyectos.spring.integracionproyectos.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public String handleResponseStatusException(ResponseStatusException ex, Model model) {
        log.warn("Error HTTP {}: {}", ex.getStatusCode().value(), ex.getReason());
        model.addAttribute("codigo", ex.getStatusCode().value());
        model.addAttribute("mensaje", ex.getReason() != null ? ex.getReason() : ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex, Model model) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);
        model.addAttribute("codigo", 500);
        model.addAttribute("mensaje", "Ha ocurrido un error inesperado. Por favor, inténtalo de nuevo más tarde.");
        return "error";
    }
}
