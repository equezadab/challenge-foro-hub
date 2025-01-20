package com.api.challenge.forohub.domain.topico.validations;

import com.api.challenge.forohub.domain.curso.CursoRepository;
import com.api.challenge.forohub.domain.topico.dto.ActualizarTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorCursoActualizado implements ValidadorTopicoActualizado{

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public void validate(ActualizarTopicoDTO data) {
        if(data.cursoId() != null){
            var ExisteCurso = cursoRepository.existsById(data.cursoId());
            if (!ExisteCurso){
                throw new ValidationException("Este curso no existe");
            }

            var cursoHabilitado = cursoRepository.findById(data.cursoId()).get().getActivo();
            if(!cursoHabilitado){
                throw new ValidationException("Este curso no esta disponible en este momento.");
            }
        }

    }

}
