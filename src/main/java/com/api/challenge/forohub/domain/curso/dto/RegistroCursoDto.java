package com.api.challenge.forohub.domain.curso.dto;

import com.api.challenge.forohub.domain.curso.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroCursoDto(@NotBlank String nombre,
                               @NotNull Categoria categoria) {
}
