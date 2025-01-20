package com.api.challenge.forohub.domain.topico.validations;

import com.api.challenge.forohub.domain.curso.CursoRepository;
import com.api.challenge.forohub.domain.topico.dto.RegistarTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorCursoCreado implements ValidadorTopicoCreado{

        @Autowired
        private CursoRepository repository;

        @Override
        public void validate(RegistarTopicoDTO data) {
            var ExisteCurso = repository.existsById(data.cursoId());
            if(!ExisteCurso){
                throw new ValidationException("Este curso no existe.");
            }

            var cursoHabilitado = repository.findById(data.cursoId()).get().getActivo();
            if(!cursoHabilitado){
                throw new ValidationException("Este curso no esta disponible en este momento.");
            }
        }
}
