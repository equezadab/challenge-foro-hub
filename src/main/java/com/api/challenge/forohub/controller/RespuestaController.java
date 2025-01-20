package com.api.challenge.forohub.controller;

import com.api.challenge.forohub.domain.respuesta.Respuesta;
import com.api.challenge.forohub.domain.respuesta.RespuestaRepository;
import com.api.challenge.forohub.domain.respuesta.dto.ActualizarRespuestaDTO;
import com.api.challenge.forohub.domain.respuesta.dto.ListarRespuestaDTO;
import com.api.challenge.forohub.domain.respuesta.dto.RegistarRespuestaDTO;
import com.api.challenge.forohub.domain.respuesta.validations.ValidacionRespuestaActualizada;
import com.api.challenge.forohub.domain.respuesta.validations.ValidadorRespuestaCreada;
import com.api.challenge.forohub.domain.topico.Estado;
import com.api.challenge.forohub.domain.topico.Topico;
import com.api.challenge.forohub.domain.topico.TopicoRepository;
import com.api.challenge.forohub.domain.usuario.Usuario;
import com.api.challenge.forohub.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class RespuestaController {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    List<ValidadorRespuestaCreada> crearValidadores;

    @Autowired
    List<ValidacionRespuestaActualizada> actualizarValidadores;


    @PostMapping
    @Transactional
    @Operation(summary = "Registra una nueva respuesta en la base de datos, vinculada a un usuario y tema existente.")
    public ResponseEntity<ListarRespuestaDTO> crearRespuesta(@RequestBody @Valid RegistarRespuestaDTO crearRespuestaDTO,
                                                             UriComponentsBuilder uriBuilder) {
        // Validar el DTO (se supone que no genera consultas adicionales)
        crearValidadores.forEach(v -> v.validate(crearRespuestaDTO));

        // Cargar usuario y tópico una sola vez
        Usuario usuario = usuarioRepository.getReferenceById(crearRespuestaDTO.usuarioId());
        Topico topico = topicoRepository.findById(crearRespuestaDTO.topicoId()).get();
        Respuesta respuesta = new Respuesta(crearRespuestaDTO, usuario, topico);

        respuestaRepository.save(respuesta);

        // Construir la URI de respuesta
        var uri = uriBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(uri).body(new ListarRespuestaDTO(respuesta));
    }

    @GetMapping("/respuestas")
    @Operation(summary = "Obtiene todas las respuestas registradas")
    public ResponseEntity<Page<ListarRespuestaDTO>> leerTodasLasRespuestas(
            @PageableDefault(size = 10, sort = {"ultimaActualizacion"}, direction = Sort.Direction.ASC) Pageable pageable) {

        // Obtener todas las respuestas paginadas
        var pagina = respuestaRepository.findAll(pageable).map(ListarRespuestaDTO::new);

        // Responder con las respuestas paginadas
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lee una única respuesta por su ID")
    public ResponseEntity<ListarRespuestaDTO> leerUnaRespuesta(@PathVariable Long id){
        Respuesta respuesta = respuestaRepository.getReferenceById(id);

        var datosRespuesta = new ListarRespuestaDTO(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getUltimaActualizacion(),
                respuesta.getSolucion(),
                respuesta.getBorrado(),
                respuesta.getUsuario().getId(),
                respuesta.getUsuario().getUsername(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo()
        );
        return ResponseEntity.ok(datosRespuesta);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualiza el mensaje de la respuesta, la solucion o el estado de la respuesta.")
    public ResponseEntity<ListarRespuestaDTO> actualizarRespuesta(@RequestBody @Valid ActualizarRespuestaDTO actualizarRespuestaDTO, @PathVariable Long id){
        actualizarValidadores.forEach(v -> v.validate(actualizarRespuestaDTO, id));
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.actualizarRespuesta(actualizarRespuestaDTO);

        if(actualizarRespuestaDTO.solucion()){
            var temaResuelto = topicoRepository.getReferenceById(respuesta.getTopico().getId());
            temaResuelto.setEstado(Estado.CLOSED);
        }

        var datosRespuesta = new ListarRespuestaDTO(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getUltimaActualizacion(),
                respuesta.getSolucion(),
                respuesta.getBorrado(),
                respuesta.getUsuario().getId(),
                respuesta.getUsuario().getUsername(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo()
        );
        return ResponseEntity.ok(datosRespuesta);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina una respuesta por su Id")
    public ResponseEntity<?> borrarRespuesta(@PathVariable Long id){
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.eliminarRespuesta();
        return ResponseEntity.noContent().build();
    }
}
