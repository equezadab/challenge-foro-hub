CREATE TABLE respuesta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    borrado BIT(1) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    mensaje VARCHAR(255) NOT NULL,
    solucion BIT(1) NOT NULL,
    ultima_actualizacion DATETIME NOT NULL,
    topico_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    FOREIGN KEY (topico_id) REFERENCES topico(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);