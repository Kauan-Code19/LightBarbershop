package com.kauan.LightBarbershop.entities.barber;

import com.kauan.LightBarbershop.entities.scheduling.SchedulingEntity;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "barber")
public class BarberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    @OneToMany(mappedBy = "barber", cascade = CascadeType.ALL)
    private Set<SchedulingEntity> schedulings;

    @OneToMany(mappedBy = "barber", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<WorkingHoursEntity> workingHours;
}
