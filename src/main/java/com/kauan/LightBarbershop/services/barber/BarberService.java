package com.kauan.LightBarbershop.services.barber;

import com.kauan.LightBarbershop.dtos.barber.BarberDto;
import com.kauan.LightBarbershop.dtos.barber.BarberResponseDto;
import com.kauan.LightBarbershop.entities.barber.BarberEntity;
import com.kauan.LightBarbershop.repositories.barber.BarberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BarberService {

    private final BarberRepository barberRepository;

    @Autowired
    public BarberService(BarberRepository barberRepository) {
        this.barberRepository = barberRepository;
    }

    @Transactional
    public BarberResponseDto createBarber(BarberDto barberDto) {
        BarberEntity barber = new BarberEntity();

        barber.setName(barberDto.getName());
        barber.setEmail(barberDto.getEmail());
        barber.setPassword(barberDto.getPassword());
        barber.setTelephone(barberDto.getTelephone());

        barberRepository.save(barber);

        return new BarberResponseDto(barber);
    }
}
