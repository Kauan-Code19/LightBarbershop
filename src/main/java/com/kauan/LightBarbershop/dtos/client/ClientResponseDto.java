package com.kauan.LightBarbershop.dtos.client;

import com.kauan.LightBarbershop.entities.client.ClientEntity;
import com.kauan.LightBarbershop.entities.client.ClientLevelEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class ClientResponseDto {

    private UUID id;

    private String name;

    private String email;

    private ClientLevelEnum membership_level;

    public ClientResponseDto(ClientEntity client) {
        id = client.getId();
        name = client.getName();
        email = client.getEmail();
        membership_level = client.getMembership_level();
    }
}
