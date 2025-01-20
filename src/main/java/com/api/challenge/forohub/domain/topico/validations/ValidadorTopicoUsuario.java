package com.api.challenge.forohub.domain.topico.validations;

import com.api.challenge.forohub.domain.topico.dto.RegistarTopicoDTO;
import com.api.challenge.forohub.domain.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTopicoUsuario implements ValidadorTopicoCreado{
    @Autowired
    private UsuarioRepository UsuarioRepository;

    @Override
    public void validate(RegistarTopicoDTO data) {
        var existeUsuario = UsuarioRepository.existsById(data.usuarioId());
        if (!existeUsuario) {
            throw new ValidationException("Este usuario no existe");
        }

        var usuarioHabilitado = UsuarioRepository.findById(data.usuarioId()).get().getEnabled();
        if (!usuarioHabilitado) {
            throw new ValidationException("Este usuario fue deshabiliado.");
        }
    }

}
