package com.api.challenge.forohub.domain.topico.validations;

import com.api.challenge.forohub.domain.topico.dto.ActualizarTopicoDTO;

public interface ValidadorTopicoActualizado {
    void validate(ActualizarTopicoDTO data);
}
