# Clientes API

Este es un proyecto de API RESTful desarrollado con **Spring Boot** que permite consultar información básica de clientes basada en el tipo y número de documento. La aplicación valida los tipos de documentos permitidos y proporciona respuestas detalladas con códigos HTTP apropiados.

## Características

- Consulta de información básica de clientes utilizando tipo y número de documento como entrada.
- Validación de tipos de documento permitidos:
    - **C**: Cédula de Ciudadanía.
    - **P**: Pasaporte.
- Manejo de excepciones con respuestas estructuradas y códigos HTTP:
    - **200 OK**: Consulta exitosa.
    - **400 Bad Request**: Tipo de documento inválido.
    - **404 Not Found**: Cliente no encontrado.
    - **500 Internal Server Error**: Errores inesperados.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.3.4**: Framework principal para el desarrollo de aplicaciones.
- **Maven**: Gestión de dependencias y construcción del proyecto.
- **SLF4J**: Manejo de logs.
- **Lombok**: Reducción de código repetitivo mediante anotaciones.
- **JUnit 5** y **Mockito**: Frameworks para pruebas unitarias.

## Requisitos

- **Java 17** o superior.
- **Maven 3.8+**.

## Instalación y Configuración

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/juanFajardo9999/client-back.git
2. **Compilar y empaquetar la aplicación**
   ```bash
   ./mvnw clean package
3. **Ejecutar Tests**
   ```bash
   ./mvnw test 
4. **Uso de la API**
   ```bash
   Puede usar el proyecto de FrontEnd ubicado en https://github.com/juanFajardo9999/api-client-front.git
   O tambien puede ingresar a la documentacion de Postman: https://documenter.getpostman.com/view/39096431/2sAYBYepVB