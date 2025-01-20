package com.api.challenge.forohub.domain.respuesta.validations;

import com.api.challenge.forohub.domain.respuesta.Respuesta;
import com.api.challenge.forohub.domain.respuesta.RespuestaRepository;
import com.api.challenge.forohub.domain.respuesta.dto.ActualizarRespuestaDTO;
import com.api.challenge.forohub.domain.topico.Estado;
import com.api.challenge.forohub.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorSolucionDuplicada implements ValidacionRespuestaActualizada {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validate(ActualizarRespuestaDTO data, Long respuestaId) {
        if (data.solucion()){
            Respuesta respuesta = respuestaRepository.getReferenceById(respuestaId);
            var topicoResuelto = topicoRepository.getReferenceById(respuesta.getTopico().getId());
            if (topicoResuelto.getEstado() == Estado.CLOSED){
                throw new ValidationException("Este topico ya esta solucionado.");
            }
        }
    }
}
