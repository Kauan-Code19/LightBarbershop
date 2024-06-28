package com.kauan.LightBarbershop.services.barber;

import com.kauan.LightBarbershop.dtos.barber.BarberDto;
import com.kauan.LightBarbershop.dtos.barber.BarberResponseDto;
import com.kauan.LightBarbershop.entities.barber.BarberEntity;
import com.kauan.LightBarbershop.repositories.barber.BarberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class BarberService {

    private final BarberRepository barberRepository;

    @Autowired
    public BarberService(BarberRepository barberRepository) {
        this.barberRepository = barberRepository;
    }

    @Transactional(readOnly = true)
    public boolean barberExist(String email) {
        Optional<UserDetails> barber = Optional.ofNullable(barberRepository.findByEmailWithSchedulingsAndWorkingHours(email));

        return barber.isPresent();
    }

    @Transactional
    public BarberResponseDto createBarber(BarberDto barberDto) {
        BarberEntity barber = new BarberEntity();

        barber.setName(barberDto.getName());
        barber.setEmail(barberDto.getEmail());

        String encryptedPassword = new Argon2PasswordEncoder(16, 32, 2, 65536, 2)
                .encode(barberDto.getPassword());

        barber.setPassword(encryptedPassword);
        barber.setTelephone(barberDto.getTelephone());

        barberRepository.save(barber);

        return new BarberResponseDto(barber);
    }
}
