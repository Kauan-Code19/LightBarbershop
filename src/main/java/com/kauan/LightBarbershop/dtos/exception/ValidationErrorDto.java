package com.kauan.LightBarbershop.dtos.exception;

import lombok.Getter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorDto extends CustomErrorDto {

    private final List<FieldMessageDto> errors = new ArrayList<>();

    public ValidationErrorDto(Instant timestamp, Integer status, String error, String trace) {
        super(timestamp, status, error, trace);
    }

    public void addError(String fieldName, String message) {errors.add(new FieldMessageDto(fieldName, message));}
}