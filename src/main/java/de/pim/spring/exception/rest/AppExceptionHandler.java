package de.pim.spring.exception.rest;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NoSuchElementException.class})
    protected ModelAndView handleException(NoSuchElementException exception, HttpServletRequest request, ModelAndView modelAndView) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        modelAndView.setViewName("error/404");
        modelAndView.addObject("error", status == null ? "404" : status);

        return modelAndView;
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        headers.add("Location", "error");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            List<String> fieldErrors = errors.getOrDefault(fieldName, new ArrayList<>());
            fieldErrors.add(error.getDefaultMessage());
            errors.put(fieldName, fieldErrors);
        });

        return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
    }
}
