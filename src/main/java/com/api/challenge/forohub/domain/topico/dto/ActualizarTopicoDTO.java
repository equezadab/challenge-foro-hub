package com.api.challenge.forohub.domain.topico.dto;

import com.api.challenge.forohub.domain.topico.Estado;

public record ActualizarTopicoDTO(
        String titulo,
        String mensaje,
        Estado estado,
        Long cursoId) {
}
