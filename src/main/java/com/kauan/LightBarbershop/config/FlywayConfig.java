package com.kauan.LightBarbershop.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

import static java.lang.System.out;

@Configuration
public class FlywayConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    @Profile("dev")
    public Flyway flywayDev() {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration/dev")
                .load();
    }

    @Bean
    @Profile("prod")
    public Flyway flywayProd() {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration/prod")
                .load();
    }
}
