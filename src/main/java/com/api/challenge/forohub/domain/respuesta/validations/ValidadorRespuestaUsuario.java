package com.api.challenge.forohub.domain.respuesta.validations;

import com.api.challenge.forohub.domain.respuesta.dto.RegistarRespuestaDTO;
import com.api.challenge.forohub.domain.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorRespuestaUsuario implements ValidadorRespuestaCreada {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validate(RegistarRespuestaDTO data) {
        var usuarioExiste = usuarioRepository.existsById(data.usuarioId());

        if(!usuarioExiste){
            throw new ValidationException("Este usuario no existe");
        }

        var usuarioHabilitado = usuarioRepository.findById(data.usuarioId()).get().isEnabled();

        if(!usuarioHabilitado){
            throw new ValidationException("Este usuario no esta habilitado");
        }
    }

}

