package com.kauan.LightBarbershop.repositories.client;

import com.kauan.LightBarbershop.entities.client.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {
    UserDetails findByEmail(String email);
}
