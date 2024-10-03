package com.example.Inventory.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.micrometer.common.annotation.AnnotationHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExcepetionHandler {
    public static final Logger logger = LogManager.getLogger(AnnotationHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public void entityNotFoundExceptionHandler(EntityNotFoundException exception){
        logger.error("Exception message" + exception.getMessage());
        logger.error("Cause: " + exception.getCause());
        exception.fillInStackTrace();
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public void principalExceptionHandler(Exception exception){
        logger.error("Exception message" + exception.getMessage());
        logger.error("Cause: " + exception.getCause());
        exception.fillInStackTrace();
    }

    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public void IdNotFoundExceptionHandler(IdNotFoundException ex){
        logger.error("Exception message" + ex.getMessage());
        logger.error("Cause: " + ex.getCause());
        ex.fillInStackTrace();

    }
}
