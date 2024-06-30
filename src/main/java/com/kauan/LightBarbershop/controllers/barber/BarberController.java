package com.kauan.LightBarbershop.controllers.barber;

import com.kauan.LightBarbershop.dtos.barber.BarberDto;
import com.kauan.LightBarbershop.dtos.barber.BarberPasswordResponseDto;
import com.kauan.LightBarbershop.dtos.barber.BarberResponseDto;
import com.kauan.LightBarbershop.services.barber.BarberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.UUID;

@RestController
@RequestMapping(value = "/barber")
public class BarberController {

    private final BarberService barberService;

    @Autowired
    public BarberController(BarberService barberService) {
        this.barberService = barberService;
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<BarberResponseDto> updateBarber(@PathVariable UUID uuid,
                                                          @Valid @RequestBody BarberDto barberDto) {
        // Atualizar barber e receber barberResponseDto
        BarberResponseDto barberResponseDto = barberService.updateBarber(uuid, barberDto);

        return ResponseEntity.ok(barberResponseDto);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<BarberResponseDto> getBarber(@PathVariable UUID uuid) {
        BarberResponseDto barberResponseDto = barberService.getBarber(uuid);

        return ResponseEntity.ok().body(barberResponseDto);
    }

    @GetMapping("/{uuid}/get-password")
    public ResponseEntity<BarberPasswordResponseDto> getPasswordBarber(@PathVariable UUID uuid) {
        BarberPasswordResponseDto barberPasswordResponseDto = barberService.getPasswordBarber(uuid);

        return ResponseEntity.ok().body(barberPasswordResponseDto);
    }
}
