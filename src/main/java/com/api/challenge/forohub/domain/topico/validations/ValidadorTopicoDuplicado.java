package com.api.challenge.forohub.domain.topico.validations;

import com.api.challenge.forohub.domain.topico.TopicoRepository;
import com.api.challenge.forohub.domain.topico.dto.RegistarTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidadorTopicoDuplicado implements ValidadorTopicoCreado {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validate(RegistarTopicoDTO data) {
        var topicoDuplicado = topicoRepository.existsByTituloAndMensaje(data.titulo(), data.mensaje());
        if(topicoDuplicado){
            throw new ValidationException("Este topico ya existe" + topicoRepository.findByTitulo(data.titulo()).getId());

        }
    }
}
