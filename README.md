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

- **`/respuestas`**  
  Manejo de respuestas a preguntas en los temas.


 ### Imágenes de ejemplo de tópicos

A continuación, se presentan ejemplos de cómo se visualizan los tópicos en ForoHub:

#### Registro de los tópicos
![Registro de un tópico](![RegistroTopicoPOST](https://github.com/user-attachments/assets/15772c8d-3ad3-407c-af9d-d76283d642e7)
)

#### Lista de tópicos
![Lista de los tópico](![ListarTopicosGET](https://github.com/user-attachments/assets/00f4f171-93d0-42c6-8a22-369420b76192)
)

#### Lista de un tópico por ID
![Lista de un tópico](![ListarTopicosIDGET](https://github.com/user-attachments/assets/ade51239-e590-4345-a263-29728394a8bd)
)

#### Actualización de un tópico por ID
![Actualización de un tópico por ID](![ActualizarTopicoPUT](https://github.com/user-attachments/assets/ef6c08b1-6ef1-4fa1-8584-eb7f176a0253)
)

#### Eliminación de un tópico por ID
![Eliminación de un tópico por ID](![BorrarTopicoDELETE](https://github.com/user-attachments/assets/8e712938-6bc5-4d62-aad0-922b0f811823)


)

