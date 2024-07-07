package com.kauan.LightBarbershop.dtos.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FieldMessageDto {

    private String fieldName;
    private String message;
}
