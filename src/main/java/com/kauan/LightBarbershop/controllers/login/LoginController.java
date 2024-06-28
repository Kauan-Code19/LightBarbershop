package com.kauan.LightBarbershop.controllers.login;

import com.kauan.LightBarbershop.dtos.login.LoginDto;
import com.kauan.LightBarbershop.dtos.login.LoginResponseDto;
import com.kauan.LightBarbershop.entities.barber.BarberEntity;
import com.kauan.LightBarbershop.entities.client.ClientEntity;
import com.kauan.LightBarbershop.services.token.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/barber")
    public ResponseEntity<LoginResponseDto> loginBarber(@Valid @RequestBody LoginDto loginDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());

        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateTokenBarber((BarberEntity) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/client")
    public ResponseEntity<LoginResponseDto> loginClient(@Valid @RequestBody LoginDto loginDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());

        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateTokenClient((ClientEntity) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
