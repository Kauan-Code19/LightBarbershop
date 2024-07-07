package com.kauan.LightBarbershop.controllers.exception;

import com.kauan.LightBarbershop.dtos.exception.CustomErrorDto;
import com.kauan.LightBarbershop.dtos.exception.ValidationErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorDto> methodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationErrorDto validationErrorDto = new ValidationErrorDto(Instant.now(),
                status.value(), "Dados Inválidos", request.getRequestURI());

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrorDto.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(validationErrorDto);
    }
}
