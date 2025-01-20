package com.api.challenge.forohub.domain.respuesta.validations;

import com.api.challenge.forohub.domain.respuesta.dto.ActualizarRespuestaDTO;

public interface ValidacionRespuestaActualizada {
    void validate(ActualizarRespuestaDTO data, Long respuestaId);
}
