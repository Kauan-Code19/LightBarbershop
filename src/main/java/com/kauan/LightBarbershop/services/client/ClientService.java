package com.kauan.LightBarbershop.services.client;

import com.kauan.LightBarbershop.dtos.client.ClientDto;
import com.kauan.LightBarbershop.dtos.client.ClientResponseDto;
import com.kauan.LightBarbershop.entities.client.ClientEntity;
import com.kauan.LightBarbershop.entities.client.ClientLevelEnum;
import com.kauan.LightBarbershop.repositories.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public boolean clientExist(String email) {
        Optional<UserDetails> client = Optional.ofNullable(clientRepository.findByEmail(email));

        return client.isPresent();
    }

    @Transactional
    public ClientResponseDto createClient(ClientDto clientDto) {
        ClientEntity client =  new ClientEntity();

        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());

        String encryptedPassword = new Argon2PasswordEncoder(16, 32, 2, 65536, 2)
                .encode(clientDto.getPassword());

        client.setPassword(encryptedPassword);
        client.setMembership_level(ClientLevelEnum.BRONZE);

        clientRepository.save(client);

        return new ClientResponseDto(client);
    }
}
