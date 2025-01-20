package com.api.challenge.forohub.controller;

import com.api.challenge.forohub.domain.usuario.Usuario;
import com.api.challenge.forohub.domain.usuario.UsuarioRepository;
import com.api.challenge.forohub.domain.usuario.dto.ActualizarUsuarioDTO;
import com.api.challenge.forohub.domain.usuario.dto.ListarUsuarioDTO;
import com.api.challenge.forohub.domain.usuario.dto.RegistrarUsuarioDTO;
import com.api.challenge.forohub.domain.usuario.validations.ValidadorActualizarUsuario;
import com.api.challenge.forohub.domain.usuario.validations.ValidadorUsuarioCreado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuario", description = "Crear topicos y publica respuestas")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    List<ValidadorUsuarioCreado> crearValidador;

    @Autowired
    List<ValidadorActualizarUsuario> actualizarValidador;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo usuario en la BD")
    public ResponseEntity<ListarUsuarioDTO> crearUsuario(@RequestBody @Valid RegistrarUsuarioDTO crearUsuarioDTO,
                                                         UriComponentsBuilder uriBuilder){
        crearValidador.forEach(v -> v.validate(crearUsuarioDTO));

        String hashedPassword = passwordEncoder.encode(crearUsuarioDTO.password());
        Usuario usuario = new Usuario(crearUsuarioDTO, hashedPassword);

        usuarioRepository.save(usuario);
        var uri = uriBuilder.path("/usuarios/{username}").buildAndExpand(usuario.getUsername()).toUri();
        return ResponseEntity.created(uri).body(new ListarUsuarioDTO(usuario));
    }

    @GetMapping("/all")
    @Operation(summary = "Enumera todos los usuarios independientemente de su estado")
    public ResponseEntity<Page<ListarUsuarioDTO>> leerTodosUsuarios(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var pagina = usuarioRepository.findAll(pageable).map(ListarUsuarioDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping()
    @Operation(summary = "Lista solo usuarios habilitados")
    public ResponseEntity<Page<ListarUsuarioDTO>> leerUsuariosActivos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var pagina = usuarioRepository.findAllByEnabledTrue(pageable).map(ListarUsuarioDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un usuario por su ID")
    public ResponseEntity<?> leerUnUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario con ID " + id + " no encontrado"));

            var datosUsuario = new ListarUsuarioDTO(
                    usuario.getId(),
                    usuario.getUsername(),
                    usuario.getRol(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getEmail(),
                    usuario.getEnabled()
            );
            return ResponseEntity.ok(datosUsuario);

        } catch (EntityNotFoundException ex) {
            // Devuelve un mensaje en caso de que el usuario no sea encontrado
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Not Found");
            errorResponse.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualiza la contraseña, rol, nombre, apellido, correo electrónico o estado habilitado de un usuario por ID")
    public ResponseEntity<?> actualizarUsuario(
            @RequestBody @Valid ActualizarUsuarioDTO actualizarUsuarioDTO,
            @PathVariable Long id) {
        try {
            actualizarValidador.forEach(v -> v.validate(actualizarUsuarioDTO));

            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario con ID " + id + " no encontrado"));

            if (actualizarUsuarioDTO.password() != null) {
                String hashedPassword = passwordEncoder.encode(actualizarUsuarioDTO.password());
                usuario.actualizarUsuarioConPassword(actualizarUsuarioDTO, hashedPassword);
            } else {
                usuario.actualizarUsuario(actualizarUsuarioDTO);
            }

            var datosUsuario = new ListarUsuarioDTO(
                    usuario.getId(),
                    usuario.getUsername(),
                    usuario.getRol(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getEmail(),
                    usuario.getEnabled()
            );
            return ResponseEntity.ok(datosUsuario);

        } catch (EntityNotFoundException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Not Found");
            errorResponse.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Deshabilita a un usuario por ID")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario con ID " + id + " no encontrado"));

            usuario.eliminarUsuario(); // Asumiendo que deshabilita al usuario, no lo borra físicamente
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException ex) {
            // Devuelve un mensaje en caso de que el usuario no sea encontrado
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Not Found");
            errorResponse.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

}
