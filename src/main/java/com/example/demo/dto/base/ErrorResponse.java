package com.example.demo.dto.base;

import java.util.List;

public record ErrorResponse(int code, String message, List<Field> fields) {

    public record Field(String key, String value) {
    }
}
