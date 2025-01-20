package com.api.challenge.forohub.domain.curso.dto;

import com.api.challenge.forohub.domain.curso.Categoria;

public record ActualizarCursoDTO (String nombre,
                                  Categoria categoria,
                                  Boolean activo) {
}
