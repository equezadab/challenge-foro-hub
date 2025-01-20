# Forohub (Challenge Alura)
ForoHub es una plataforma educativa diseñada con Spring Boot para fomentar el aprendizaje colaborativo mediante la creación, gestión y participación en foros. La aplicación implementa un sistema robusto y seguro para la administración de usuarios, temas, cursos y respuestas.

## Tecnologías utilizadas
Backend: Spring Boot
Base de datos: MySQL
Seguridad: Spring Security con JWT
Dependencias principales:
- Spring Data JPA
- Spring Web
- Spring Security
- Validation API

## Uso
Accede a la API en: http://localhost:8080.

### Endpoints principales 

- **`/usuarios`**  
  Gestión de usuarios: registro, autenticación y administración.

- **`/topicos`**  
  Creación y consulta de tópicos.

- **`/cursos`**  
  Gestión de cursos asociados a los foros.

- **`/api/respuestas`**  
  Manejo de respuestas a preguntas en los temas.
