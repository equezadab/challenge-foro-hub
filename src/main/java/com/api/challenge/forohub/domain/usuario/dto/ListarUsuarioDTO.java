package com.api.challenge.forohub.domain.usuario.dto;

import com.api.challenge.forohub.domain.usuario.Rol;
import com.api.challenge.forohub.domain.usuario.Usuario;

public record ListarUsuarioDTO(
        Long id,
        String username,
        Rol role,
        String nombre,
        String apellido,
        String email,
        Boolean enabled
) {
    public ListarUsuarioDTO(Usuario usuario){
        this(usuario.getId(),
                usuario.getUsername(),
                usuario.getRol(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
    }

}
