package com.example.demo.exception;

import com.example.demo.dto.base.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(new ErrorResponse(-1, e.getMessage(), new ArrayList<>()));
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handle(BaseException e) {
        if (e.getHttpStatus().is5xxServerError()) {
            log.error(e.getMessage(), e);
        }
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(-1, e.getMessage(), e.getFields()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        List<ErrorResponse.Field> fields = new ArrayList<>();

        for (ConstraintViolation<?> violation : violations) {
            fields.add(new ErrorResponse.Field(violation.getPropertyPath().toString(), violation.getMessage()));
        }

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(-1, "Validation error", fields));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorResponse.Field> fields = new ArrayList<>();

        for (FieldError fieldError : e.getFieldErrors()) {
            fields.add(new ErrorResponse.Field(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(-1, "Validation error", fields));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Type genericParameterType = ex.getParameter().getGenericParameterType();
        String requiredType = genericParameterType.getTypeName();

        String message = String.format("parameter: %s, type: %s, input-value: %s, message: Invalid type of parameter is entered", ex.getName(), requiredType, ex.getValue());

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(-1, message, new ArrayList<>()));
    }
}
