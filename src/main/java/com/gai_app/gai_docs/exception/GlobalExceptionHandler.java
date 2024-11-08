package com.gai_app.gai_docs.exception;

import feign.RetryableException;
import jakarta.persistence.EntityExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleEntityExistsException(EntityExistsException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidEnumException(HttpMessageNotReadableException ex) {
        String errorMessage = ex.getMessage();

        // Проверяем, есть ли в сообщении ошибка, связанная с полем 'categories' и перечислением Category
        if (errorMessage != null && errorMessage.contains("Category")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid category value provided in 'categories'. Allowed values are: A, B, C, D, M, DE, CE, BE.");
        }

        // Общее сообщение для других ошибок десериализации
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid request format. Please check your JSON structure and values.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Обработчик для неверного пути (ошибки 404)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        String message = "Resource not found. Please check the URL.";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    // Обработчик для недопустимого метода (ошибки 405)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<String> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
        String message = "HTTP method not supported for this endpoint. Supported methods are: " + ex.getSupportedHttpMethods();
        return new ResponseEntity<>(message, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<String> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
        String message = ex.getMessage() + ". " + "Acceptable format: " + ex.getSupportedMediaTypes();
        return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
    }

    // Обработчик для неподдерживаемого MIME типа (ошибки 415)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseEntity<String> handleUnsupportedMediaTypeException(HttpMediaTypeNotSupportedException ex) {
        String message = "Unsupported media type. Supported media types are: " + ex.getSupportedMediaTypes();
        return new ResponseEntity<>(message, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

//    Обработчик для недоступного сервиса (ошибки 503)
    @ExceptionHandler(RetryableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<String> handleRetryableException(RetryableException ex) {
        String service = ex.getMessage().split(" ")[0];
        String message = "Service " + service + " is unavailable. Please try again.";
        return new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
    }

    // Обработчик для непредвиденной ошибки сервера (ошибки 500)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        logger.debug(ex.toString());
        String message = "An unexpected error occurred: " + ex;
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
