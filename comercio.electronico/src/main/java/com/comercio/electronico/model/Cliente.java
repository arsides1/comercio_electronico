package com.comercio.electronico.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("clientes")
public class Cliente {
    @Id
    private Long id;

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @NotBlank(message = "El email es requerido")
    @Email(message = "El formato del email no es válido")
    private String email;

    @NotBlank(message = "El teléfono es requerido")
    private String telefono;

    @NotBlank(message = "La dirección es requerida")
    private String direccion;

    private LocalDateTime fechaRegistro;
}
