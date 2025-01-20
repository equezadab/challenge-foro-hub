package com.api.challenge.forohub.domain.respuesta.validations;

import com.api.challenge.forohub.domain.respuesta.dto.RegistarRespuestaDTO;

public interface ValidadorRespuestaCreada {
    void validate(RegistarRespuestaDTO data);
}
