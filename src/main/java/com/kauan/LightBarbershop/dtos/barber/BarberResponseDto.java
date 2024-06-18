package com.kauan.LightBarbershop.dtos.barber;

import com.kauan.LightBarbershop.entities.barber.BarberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class BarberResponseDto {

    private UUID id;

    private String name;

    private String email;

    private String password;

    private String telephone;

    public BarberResponseDto(BarberEntity barber) {
        id = barber.getId();
        name = barber.getName();
        email = barber.getEmail();
        password = barber.getPassword();
        telephone = barber.getTelephone();
    }
}
