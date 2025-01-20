package com.api.challenge.forohub.domain.curso.dto;

import com.api.challenge.forohub.domain.curso.Categoria;
import com.api.challenge.forohub.domain.curso.Curso;

public record ListarCursoDTO(
        Long id,
        String nombre,
        Categoria categoria,
        Boolean activo
) {
    public ListarCursoDTO(Curso curso){
        this(curso.getId(),
                curso.getNombre(),
                curso.getCategoria(),
                curso.getActivo());
    }
}
