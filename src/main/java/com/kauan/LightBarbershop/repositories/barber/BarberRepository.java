package com.kauan.LightBarbershop.repositories.barber;

import com.kauan.LightBarbershop.entities.barber.BarberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface BarberRepository extends JpaRepository<BarberEntity, UUID> {
    @Query("SELECT b FROM BarberEntity b " +
            "LEFT JOIN FETCH b.schedulings " +
            "LEFT JOIN FETCH b.workingHours WHERE b.email = :email")
    UserDetails findByEmailWithSchedulingsAndWorkingHours(@Param("email")String email);
}
