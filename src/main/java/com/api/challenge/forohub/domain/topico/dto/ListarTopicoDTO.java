package com.api.challenge.forohub.domain.topico.dto;

import com.api.challenge.forohub.domain.curso.Categoria;
import com.api.challenge.forohub.domain.topico.Estado;
import com.api.challenge.forohub.domain.topico.Topico;

import java.time.LocalDateTime;

public record ListarTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        LocalDateTime ultimaActualizacion,
        Estado estado,
        String usuario,
        String curso,
        Categoria categoriaCurso
) {
    public ListarTopicoDTO(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getUltimaActualizacion(),
                topico.getEstado(),
                topico.getUsuario().getUsername(),
                topico.getCurso().getNombre(),
                topico.getCurso().getCategoria()
        );
    }
}
