package com.app.presentation.dto;

import com.app.persistence.model.EGender;
import com.app.persistence.model.EStatus;
import jakarta.validation.constraints.*;

public record SaveCustomerDTO(
        @NotNull(message = "La identificación es obligatoria")
        @Positive(message = "La identificación debe ser un número positivo")
        Long identification,

        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotNull(message = "El género es obligatorio")
        EGender gender,

        @Min(value = 18, message = "La edad mínima es 18 años")
        @Max(value = 120, message = "La edad máxima es 120 años")
        Integer age,

        @NotBlank(message = "La dirección es obligatoria")
        String address,

        @NotNull(message = "El teléfono es obligatorio")
        String phone,

        @NotBlank(message = "La contraseña es obligatoria")
        String password,

        @NotNull(message = "El estado es obligatorio")
        EStatus status) { }
