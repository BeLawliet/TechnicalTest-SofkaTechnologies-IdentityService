# Identity Service

[![Java](https://img.shields.io/badge/Java-17-blue)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) 
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen)](https://spring.io/projects/spring-boot) 
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)  
[![Docker](https://img.shields.io/badge/Docker-ready-blue)](https://www.docker.com/)  
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)

---

## Descripción

`identity-service` es un microservicio desarrollado con **Java Spring Boot** que gestiona información de identidad de clientes.  
Permite crear, actualizar, listar y eliminar datos de clientes, incluyendo información personal y de estado.  

Este servicio está **dockerizado** y preparado para ejecutarse en entornos con contenedores, lo que facilita su despliegue e integración.

---

## Funcionalidades

- CRUD de clientes:
  - Crear nuevos clientes.
  - Actualizar información de clientes existentes.
  - Listar todos los clientes.
  - Eliminar clientes.
- Validación de datos usando **Spring Validation**.
- Conversión entre entidades y DTOs mediante **ModelMapper**.
- Manejo global de excepciones con `AppControllerAdvice`.
- Persistencia en **PostgreSQL** usando **Spring Data JPA**.

---

## Tecnologías y Dependencias

- **Java 17**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **Spring Web**
- **PostgreSQL**
- **Lombok** para reducir código boilerplate
- **ModelMapper** para mapping entre entidades y DTOs
- **Spring Boot Starter Validation** para validaciones
- **Spring Boot Starter Test** para pruebas unitarias
- **JaCoCo** para métricas de cobertura de tests

---

## Estructura del Proyecto

```
com.app
├─ advice
│  └─ AppControllerAdvice.java
├─ config
│  └─ AppConfig.java
├─ persistence
│  ├─ model
│  │  ├─ Customer.java
│  │  ├─ Person.java
│  │  ├─ EGender.java
│  │  └─ EStatus.java
│  └─ repository
│     └─ ICustomerRepository.java
├─ presentation
│  ├─ controller
│  │  └─ CustomerController.java
│  └─ dto
│     ├─ CustomerDTO.java
│     ├─ SaveCustomerDTO.java
│     ├─ UpdateCustomerDTO.java
│     └─ ResponseDTO.java
├─ service
│  ├─ ICustomerService.java
│  └─ impl
│     └─ CustomerServiceImpl.java
└─ IdentityServiceApplication.java
```

---

## Configuración

### Archivo `application.yml`

```yaml
server:
  port: ${SERVER_PORT:8080}

spring:
  application:
    name: identity-service

  datasource:
    url: ${DATABASE_URL}
    username: ${DATABASE_USER}
    password: ${DATABASE_PASSWORD}
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: none
```

---

## Construcción y Ejecución

1. **Construcción del proyecto y creación de imagen Docker**:
   ```bash
   mvn clean install -DskipTests
   docker build -t identity-service .
   ```

2. **Ejecución del contenedor**:
   ```bash
   docker run -p 8080:8080 -e SERVER_PORT=8080 -e DATABASE_URL="url" -e DATABASE_USER="user" -e DATABASE_PASSWORD="password" identity-service-container
   ```

---

## Endpoints principales

| Método | Endpoint                     | Descripción                        |
|--------|-------------------------------|------------------------------------|
| GET    | /api/v1/customers            | Listar todos los clientes          |
| POST   | /api/v1/customers            | Crear un nuevo cliente             |
| PUT    | /api/v1/customers/{id}       | Actualizar un cliente existente    |
| DELETE | /api/v1/customers/{id}       | Eliminar un cliente                |

---

## Pruebas Unitarias

- Se utilizan **Spring Boot Test** y **JUnit 5**.
- Cobertura de tests validada con **JaCoCo**.

---

## Autor

Andrés Suárez - Desarrollador Backend  
Email: elcostexd995@gmail.com
