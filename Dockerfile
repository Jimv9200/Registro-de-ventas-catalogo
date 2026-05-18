# Usa una imagen base ligera de Java 17 (ajusta la versión si usas otra)
FROM eclipse-temurin:25-jdk-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo compilado al contenedor
# (Asegúrate de que la ruta coincida con el nombre de tu .jar)
COPY build/libs/catalogo-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que corre el microservicio
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]