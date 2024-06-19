package com.kauan.LightBarbershop.repositories.barber;

import com.kauan.LightBarbershop.entities.barber.BarberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BarberRepository extends JpaRepository<BarberEntity, UUID> {
}
