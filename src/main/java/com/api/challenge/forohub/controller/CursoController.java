package com.api.challenge.forohub.controller;

import com.api.challenge.forohub.domain.curso.Curso;
import com.api.challenge.forohub.domain.curso.CursoRepository;
import com.api.challenge.forohub.domain.curso.dto.ActualizarCursoDTO;
import com.api.challenge.forohub.domain.curso.dto.ListarCursoDTO;
import com.api.challenge.forohub.domain.curso.dto.RegistroCursoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Curso")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Registrar un nuevo curso en la Base de Datos")
    public ResponseEntity<ListarCursoDTO> crearCurso(@RequestBody @Valid RegistroCursoDto registroCursoDto,
                                                      UriComponentsBuilder uriBuilder){
        Curso curso = new Curso(registroCursoDto);
        cursoRepository.save(curso);
        var uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(uri).body(new ListarCursoDTO(curso));

    }

    @GetMapping
    @Operation(summary = "Listar cursos activos paginados.")
    public ResponseEntity<Page<ListarCursoDTO>> listarCursos(
            @PageableDefault(size = 10, sort = "nombre") Pageable pageable) {
        // Obtener cursos activos paginados desde la base de datos
        Page<ListarCursoDTO> cursos = cursoRepository.findAllByActivoTrue(pageable)
                .map(ListarCursoDTO::new);

        // Devolver la respuesta con los cursos
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un curso por su ID.")
    public ResponseEntity<ListarCursoDTO> obtenerCursoPorId(@PathVariable Long id) {
        return cursoRepository.findById(id)
                .map(curso -> ResponseEntity.ok(new ListarCursoDTO(curso)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualizar un curso existente.")
    public ResponseEntity<ListarCursoDTO> actualizarCurso(@PathVariable Long id,
                                                          @RequestBody @Valid ActualizarCursoDTO actualizarCursoDTO) {
        // Buscar el curso por ID
        return cursoRepository.findById(id)
                .map(curso -> {
                    curso.actualizarDatos(actualizarCursoDTO); //Metodo para actualizar los datos
                    return ResponseEntity.ok(new ListarCursoDTO(curso));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Eliminar un curso existente .")
    public ResponseEntity<Object> eliminarCurso(@PathVariable Long id) {
        return cursoRepository.findById(id)
                .map(curso -> {
                    // Realizamos un soft delete, es decir, marcamos el curso como inactivo
                    curso.eliminarCurso();
                    return ResponseEntity.noContent().build(); // Respuesta HTTP 204: No Content
                })
                .orElse(ResponseEntity.notFound().build()); // Si no se encuentra el curso, se devuelve un 404
    }
}

