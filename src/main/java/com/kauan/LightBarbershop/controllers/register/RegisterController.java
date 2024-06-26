package com.kauan.LightBarbershop.controllers.register;

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
@RequestMapping("/register")
public class RegisterController {

    private final BarberService barberService;

    @Autowired
    public RegisterController(BarberService barberService) {
        this.barberService = barberService;
    }

    @PostMapping("/barber")
    public ResponseEntity<BarberResponseDto> registerBarber(@Valid @RequestBody BarberDto barberDto) {
        if (barberService.barberExist(barberDto.getEmail())) return ResponseEntity.badRequest().build();

        BarberResponseDto barberResponseDto = barberService.createBarber(barberDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                .buildAndExpand(barberResponseDto.getId()).toUri();

        return ResponseEntity.created(uri).body(barberResponseDto);
    }
}
