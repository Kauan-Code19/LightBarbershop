package com.kauan.LightBarbershop.services.client;

import com.kauan.LightBarbershop.dtos.client.ClientDto;
import com.kauan.LightBarbershop.dtos.client.ClientPasswordResponseDto;
import com.kauan.LightBarbershop.dtos.client.ClientResponseDto;
import com.kauan.LightBarbershop.entities.client.ClientEntity;
import com.kauan.LightBarbershop.entities.client.ClientLevelEnum;
import com.kauan.LightBarbershop.repositories.client.ClientRepository;
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
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true) // Transação apenas no modo leitura
    public boolean clientExist(String email) {
        // Verifica e retorna true se um client com o parâmetro "email" existe no banco de dados
        Optional<UserDetails> client = Optional.ofNullable(clientRepository.findByEmail(email));

        return client.isPresent();
    }

    @Transactional
    public ClientResponseDto createClient(ClientDto clientDto) {
        ClientEntity client =  new ClientEntity();

        // Definindo os atributos da entidade com clientDto
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());

        String encryptedPassword = new Argon2PasswordEncoder(16, 32, 2, 65536, 2)
                .encode(clientDto.getPassword()); // Encriptografia da senha do client

        client.setPassword(encryptedPassword);
        client.setMembership_level(ClientLevelEnum.BRONZE);

        clientRepository.save(client); // Salvando entidade no banco de dados

        return new ClientResponseDto(client);
    }

    @Transactional
    public ClientResponseDto updateClient(UUID uuid, ClientDto clientDto) {
        ClientEntity client = clientRepository.getReferenceById(uuid); // Buscando client através do id

        // Flag para rastrear se houve alguma atualização
        AtomicBoolean updated = new AtomicBoolean(false);

        updateAttribute(clientDto.getName(), client.getName(), updated, ()
                -> client.setName(clientDto.getName())); // Atualiza o atributo 'name' se necessário

        updateAttribute(clientDto.getEmail(), client.getEmail(), updated, ()
                -> client.setEmail(clientDto.getEmail())); // Atualiza o atributo 'email' se necessário

        updatePassword(clientDto.getPassword(), client, updated); // Atualiza a senha se necessário

        if (updated.get()) {
            clientRepository.save(client); // Salva a entidade apenas se houve alguma atualização
        }

        return new ClientResponseDto(client);
    }

    // Método para verificar e atualizar atributos genéricos
    private void updateAttribute(Object newValue, Object currentValue,
                                 AtomicBoolean updatet, Runnable updateAction) {

        if (!Objects.equals(newValue, currentValue)) {
            updateAction.run();  // Executa a ação de atualização (lambda)
            updatet.set(true); // Marca que houve uma atualização
        }
    }

    // Método para verificar e atualizar a senha
    private void updatePassword(String newPassword, ClientEntity client, AtomicBoolean updated) {
        if (newPassword != null && !new Argon2PasswordEncoder(16, 32, 2, 65536, 2)
                .matches(newPassword, client.getPassword())) {

            String encryptedPassword = new Argon2PasswordEncoder(16, 32, 2, 65536, 2)
                    .encode(newPassword);

            client.setPassword(encryptedPassword); // Define a senha encriptada na entidade 'client'

            updated.set(true); // Marca que houve uma atualização
        }
    }

    @Transactional(readOnly = true) // Transação apenas no modo leitura
    public ClientResponseDto getClient(UUID uuid) {
        ClientEntity client = clientRepository.getReferenceById(uuid); // Buscando barber através do id

        return new ClientResponseDto(client);
    }

    @Transactional(readOnly = true) // Transação apenas no modo leitura
    public ClientPasswordResponseDto getPasswordClient(UUID uuid) {
        ClientEntity client = clientRepository.getReferenceById(uuid); // Buscando barber através do id

        return new ClientPasswordResponseDto(client.getPassword());
    }
}
