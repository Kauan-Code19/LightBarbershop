package com.kauan.LightBarbershop.controllers.client;

import com.kauan.LightBarbershop.dtos.client.ClientDto;
import com.kauan.LightBarbershop.dtos.client.ClientPasswordResponseDto;
import com.kauan.LightBarbershop.dtos.client.ClientResponseDto;
import com.kauan.LightBarbershop.services.client.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.UUID;

@RestController
@RequestMapping(value = "/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable UUID uuid,
                                                          @Valid @RequestBody ClientDto clientDto) {

        // Atualizar client e receber barberResponseDto
        ClientResponseDto clientResponseDto = clientService.updateClient(uuid, clientDto);

        return ResponseEntity.ok(clientResponseDto);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ClientResponseDto> getClient(@PathVariable UUID uuid) {
        ClientResponseDto clientResponseDto = clientService.getClient(uuid);

        return ResponseEntity.ok(clientResponseDto);
    }

    @GetMapping("/{uuid}/get-password")
    public ResponseEntity<ClientPasswordResponseDto> getPasswordClient(@PathVariable UUID uuid) {
        ClientPasswordResponseDto clientPasswordResponseDto = clientService.getPasswordClient(uuid);

        return ResponseEntity.ok(clientPasswordResponseDto);
    }
}
