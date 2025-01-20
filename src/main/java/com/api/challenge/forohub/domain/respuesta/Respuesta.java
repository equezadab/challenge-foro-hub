package com.api.challenge.forohub.domain.respuesta;

import com.api.challenge.forohub.domain.respuesta.dto.ActualizarRespuestaDTO;
import com.api.challenge.forohub.domain.respuesta.dto.RegistarRespuestaDTO;
import com.api.challenge.forohub.domain.topico.Topico;
import com.api.challenge.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "respuesta")
@Entity(name = "Respuesta")
@EqualsAndHashCode(of = "id")

public class Respuesta {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;
    private Boolean solucion;
    private Boolean borrado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    public Respuesta(RegistarRespuestaDTO registrarRespuestaDTO, Usuario usuario, Topico topico) {
        this.mensaje = registrarRespuestaDTO.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaActualizacion = LocalDateTime.now();
        this.solucion = false;
        this.borrado = false;
        this.usuario = usuario;
        this.topico = topico;
    }

    public void actualizarRespuesta(ActualizarRespuestaDTO actualizarRespuestaDTO) {
        // Actualizar solo si los valores no son nulos
        if (actualizarRespuestaDTO.mensaje() != null) {
            this.mensaje = actualizarRespuestaDTO.mensaje();
        }
        if (actualizarRespuestaDTO.solucion() != null) {
            this.solucion = actualizarRespuestaDTO.solucion();
        }
        if (actualizarRespuestaDTO.borrado() != null) {
            this.borrado = actualizarRespuestaDTO.borrado();
        }
        this.ultimaActualizacion = LocalDateTime.now();  // Actualizamos la fecha de la última actualización
    }

    public void eliminarRespuesta(){
        this.borrado = true;
    }
}
