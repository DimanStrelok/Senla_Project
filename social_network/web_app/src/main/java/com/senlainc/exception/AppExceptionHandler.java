package com.senlainc.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LogManager.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(AppException.class)
    protected ResponseEntity<String> handleAppException(AppException ex, WebRequest request) {
        log.error("app exception", ex);
        return ResponseEntity.badRequest().build();
    }
}
