package com.kauan.LightBarbershop.controllers.barber;

import com.kauan.LightBarbershop.dtos.barber.BarberDto;
import com.kauan.LightBarbershop.dtos.barber.BarberResponseDto;
import com.kauan.LightBarbershop.services.barber.BarberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/barber")
public class BarberController {

    @Autowired
    private BarberService barberService;

    @PostMapping
    public ResponseEntity<BarberResponseDto> createBarber(@Valid @RequestBody BarberDto barberDto) {
        BarberResponseDto barberResponseDto = barberService.createBarber(barberDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                .buildAndExpand(barberResponseDto.getId()).toUri();

        return ResponseEntity.created(uri).body(barberResponseDto);
    }
}
