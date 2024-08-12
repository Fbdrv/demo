package com.example.demo.exception;

import com.example.demo.dto.base.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class BaseException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final List<ErrorResponse.Field> fields;

    public BaseException(String message, HttpStatus httpStatus, List<ErrorResponse.Field> fields) {
        super(message);
        this.httpStatus = httpStatus;
        this.fields = fields;
    }
}
