package com.api.challenge.forohub.domain.respuesta.validations;

import com.api.challenge.forohub.domain.respuesta.dto.RegistarRespuestaDTO;
import com.api.challenge.forohub.domain.topico.Estado;
import com.api.challenge.forohub.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorRespuestaTopico implements ValidadorRespuestaCreada {
    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validate(RegistarRespuestaDTO data) {
        var topicoExiste = topicoRepository.existsById(data.topicoId());

        if (!topicoExiste){
            throw new ValidationException("Este topico no existe.");
        }

        var topicoAbierto = topicoRepository.findById(data.topicoId()).get().getEstado();

        if(topicoAbierto != Estado.OPEN){
            throw new ValidationException("Este topico no esta abierto.");
        }

    }
}
