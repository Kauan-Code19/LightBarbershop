package com.kauan.LightBarbershop.dtos.barber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BarberDto {

    @NotBlank
    @Size(max = 100, message = "O nome deve ter no maximo e 100 caracteres")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "O nome deve conter apenas letras")
    private String name;

    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "O email deve ser válido")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$",
            message = "A senha deve conter pelo menos uma letra maiúscula, uma minúscula," +
                    " um dígito, um caractere especial e ter no mínimo 8 caracteres")
    private String password;

    @NotBlank(message = "O número de telefone não pode estar em branco")
    @Pattern(regexp = "^\\+55\\s?(\\d{2}\\s?)?(9\\d{4})[- ]?\\d{4}$",
            message = "Formato de telefone inválido. Use o formato +55 00 90000-0000")
    private String telephone;
}
