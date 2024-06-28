package com.kauan.LightBarbershop.services.user_details;

import com.kauan.LightBarbershop.repositories.barber.BarberRepository;
import com.kauan.LightBarbershop.repositories.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SqlUserDetailsService implements UserDetailsService {

    private final BarberRepository barberRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public SqlUserDetailsService(BarberRepository barberRepository, ClientRepository clientRepository) {
        this.barberRepository = barberRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = barberRepository.findByEmailWithSchedulingsAndWorkingHours(username);

        if (userDetails == null) {
            userDetails = clientRepository.findByEmail(username);
        }

        return userDetails;
    }
}
