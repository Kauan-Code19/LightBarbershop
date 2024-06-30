package com.kauan.LightBarbershop.services.barber;

import com.kauan.LightBarbershop.dtos.barber.BarberDto;
import com.kauan.LightBarbershop.dtos.barber.BarberPasswordResponseDto;
import com.kauan.LightBarbershop.dtos.barber.BarberResponseDto;
import com.kauan.LightBarbershop.entities.barber.BarberEntity;
import com.kauan.LightBarbershop.repositories.barber.BarberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class BarberService {

    private final BarberRepository barberRepository;

    @Autowired
    public BarberService(BarberRepository barberRepository) {
        this.barberRepository = barberRepository;
    }

    @Transactional(readOnly = true) // Transação apenas no modo leitura
    public boolean barberExist(String email) {
        // Verifica e retorna true se um barber com o parâmetro "email" existe no banco de dados
        Optional<UserDetails> barber = Optional.ofNullable(barberRepository
                .findByEmailWithSchedulingsAndWorkingHours(email));

        return barber.isPresent();
    }

    @Transactional
    public BarberResponseDto createBarber(BarberDto barberDto) {
        BarberEntity barber = new BarberEntity();

        // Definindo os atributos da entidade com barberDto
        barber.setName(barberDto.getName());
        barber.setEmail(barberDto.getEmail());

        String encryptedPassword = new Argon2PasswordEncoder(16, 32, 2, 65536, 2)
                .encode(barberDto.getPassword()); // Encriptografia da senha do barber

        barber.setPassword(encryptedPassword);
        barber.setTelephone(barberDto.getTelephone());

        barberRepository.save(barber); // Salvando entidade no banco de dados

        return new BarberResponseDto(barber);
    }

    @Transactional
    public BarberResponseDto updateBarber(UUID uuid, BarberDto barberDto) {
        BarberEntity barber = barberRepository.getReferenceById(uuid); // Buscando barber através do id

        // Flag para rastrear se houve alguma atualização
        AtomicBoolean updated = new AtomicBoolean(false);

        updateAttribute(barberDto.getName(), barber.getName(), updated, ()
                -> barber.setName(barberDto.getName())); // Atualiza o atributo 'name' se necessário

        updateAttribute(barberDto.getEmail(), barber.getEmail(), updated, ()
                -> barber.setEmail(barberDto.getEmail())); // Atualiza o atributo 'email' se necessário

        updateAttribute(barberDto.getTelephone(), barber.getTelephone(), updated, ()
                -> barber.setTelephone(barberDto.getTelephone()));// Atualiza o atributo 'telephone' se necessário

        updatePassword(barberDto.getPassword(), barber, updated); // Atualiza a senha se necessário

        if (updated.get()) {
            barberRepository.save(barber); // Salva a entidade apenas se houve alguma atualização
        }

        return new BarberResponseDto(barber);
    }

    // Método para verificar e atualizar atributos genéricos
    private void updateAttribute(Object newValue, Object currentValue,
                                 AtomicBoolean updated, Runnable updateAction) {
        if (!Objects.equals(newValue, currentValue)) {
            updateAction.run(); // Executa a ação de atualização (lambda)
            updated.set(true); // Marca que houve uma atualização
        }
    }

    // Método para verificar e atualizar a senha
    private void updatePassword(String newPassword, BarberEntity barber, AtomicBoolean updated) {
        if (newPassword != null && !new Argon2PasswordEncoder(16, 32, 2, 65536, 2)
                .matches(newPassword, barber.getPassword())) {

            // Encripta a nova senha
            String encryptedPassword = new Argon2PasswordEncoder(16, 32, 2, 65536, 2).encode(newPassword);

            barber.setPassword(encryptedPassword); // Define a senha encriptada na entidade 'barber'

            updated.set(true); // Marca que houve uma atualização
        }
    }

    @Transactional(readOnly = true) // Transação apenas no modo leitura
    public BarberResponseDto getBarber(UUID uuid) {
        BarberEntity barber = barberRepository.getReferenceById(uuid); // Buscando barber através do id

        return new BarberResponseDto(barber);
    }

    @Transactional(readOnly = true) // Transação apenas no modo leitura
    public BarberPasswordResponseDto getPasswordBarber(UUID uuid) {
        BarberEntity barber = barberRepository.getReferenceById(uuid); // Buscando barber através do id

        return new BarberPasswordResponseDto(barber.getPassword());
    }
}
