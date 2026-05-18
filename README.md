# Catalogo Microservicio

Microservicio de catálogo de productos construido con Spring Boot, Spring Data JPA y MySQL.

## Descripción

Este proyecto implementa un microservicio de catálogo para gestionar productos, categorías, imágenes de producto y unidades de medida.

La aplicación está diseñada para ejecutarse en Java 25 y utiliza:

- Spring Boot 4.0.6
- Spring Web
- Spring Data JPA
- MySQL
- Jakarta Persistence
- Lombok

## Estructura principal

- `com.catalogo.catalogo.model` - Entidades JPA
- `com.catalogo.catalogo.repository` - Repositorios Spring Data JPA
- `com.catalogo.catalogo.service` - Lógica de negocio de servicios
- `src/main/resources/application.properties` - Configuración de Spring Boot

## Entidades clave

- `Producto`
- `Categoria`
- `ImagenProducto`
- `UnidadMedida`

## Requisitos

- Java 25
- Gradle
- MySQL 8 o superior

## Configuración local

El archivo de configuración por defecto está en `src/main/resources/application.properties`:

```properties
spring.application.name=catalogo
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/catalogo_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
```

> Si ejecutas con Docker Compose, las variables de entorno del contenedor están configuradas para conectarse a `mysql-db`.

## Compilar y ejecutar localmente

1. Construir el proyecto:

```bash
t ./gradlew clean build
```

2. Ejecutar la aplicación:

```bash
./gradlew bootRun
```

3. Acceder al microservicio en:

```text
http://localhost:8080
```

## Ejecutar con Docker

La aplicación incluye un `Dockerfile` y `docker-compose.yml` para levantar la base de datos y el servicio.

1. Construir y ejecutar:

```bash
docker compose up --build
```

2. Detener los contenedores:

```bash
docker compose down
```

## Notas importantes

- El servicio utiliza MySQL y crea el esquema automáticamente según las entidades.
- El `docker-compose.yml` expone MySQL en el puerto `3306` y el servicio en `8080`.
- El `Dockerfile` asume que el archivo JAR de la aplicación se genera en `build/libs/catalogo-0.0.1-SNAPSHOT.jar`.

## Mejoras sugeridas

- Añadir controladores REST (`@RestController`) para exponer los endpoints del catálogo.
- Completar la documentación de API con OpenAPI/Swagger.
- Agregar pruebas unitarias y de integración.
- Usar perfiles de Spring para separar configuración local y de producción.

## Licencia

Proyecto de ejemplo para catálogo de productos.
