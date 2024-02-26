## Spring Boot

1. Se incluye código fuente
2. Se incluye archivo Postman para pruebas y generación de token de autenticación
3. No se incluye script de base datos pues se crea en forma automática en memoria

# How to build and deploy
1. Bajar codigo fuente
2. Se incluye jar
3. Ejecutar java -jar auto-app-0.0.1-SNAPSHOT.jar Application.java
4. Importar archivo Postman para generar token ó usar Curl
curl --location --request POST 'http://localhost:8080/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username" : "francisco",
    "password" : "password"
}'

5. Crear usuario con Postman ó usar Curl (reemplazar TOKEN)
curl --location --request POST 'http://localhost:8080/api/users' \
--header 'Authorization: Bearer TOKEN' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "name",
    "email": "hola@hola.com",
    "password": "Aasdada12",
    "phones": [
        {
            "number": "3300412",
            "cityCode": "412",
            "countryCode": "1"
        }
    ]
}'

Swagger disponible en http://localhost:8080/swagger-ui/index.html
