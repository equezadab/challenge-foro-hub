package com.api.challenge.forohub.domain.curso;

import com.api.challenge.forohub.domain.curso.dto.ActualizarCursoDTO;
import com.api.challenge.forohub.domain.curso.dto.ListarCursoDTO;
import com.api.challenge.forohub.domain.curso.dto.RegistroCursoDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "curso")
@Entity(name = "Curso")
@EqualsAndHashCode(of = "id")

public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private Boolean activo;

    public Curso(RegistroCursoDto registroCursoDto) {
        this.nombre = registroCursoDto.nombre();
        this.categoria = registroCursoDto.categoria();
        this.activo = true;
    }

    public void actualizarDatos(ActualizarCursoDTO actualizarCursoDTO) {
        if (actualizarCursoDTO.nombre() != null) {
            this.nombre = actualizarCursoDTO.nombre();
        }
        if (actualizarCursoDTO.categoria() != null) {
            this.categoria = actualizarCursoDTO.categoria();
        }
        if (actualizarCursoDTO.activo() != null) {
            this.activo = actualizarCursoDTO.activo();
        }
    }

    public void eliminarCurso() {
        this.activo = false;
    }


}
