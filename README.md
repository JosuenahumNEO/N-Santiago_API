# N'Santiago API

API Spring Boot para autenticación y publicaciones de N'Santiago.

## Variables requeridas

```text
DATABASE_URL=jdbc:postgresql://HOST:PUERTO/DB
PGUSER=postgres
PGPASSWORD=CONTRASENA
JWT_SECRET=UNA_CLAVE_LARGA_ALEATORIA_DE_AL_MENOS_32_CARACTERES
JWT_EXPIRATION_MS=86400000
CORS_ALLOWED_ORIGINS=http://localhost:8081
UPLOAD_DIR=/app/uploads
PUBLIC_BASE_URL=https://tu-api.up.railway.app
```

La base de datos debe ser PostgreSQL creada en tu propio proyecto Railway.

## Ejecutar localmente

```bash
mvn spring-boot:run
```

Si Maven no está instalado, usa el wrapper incluido:

```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-21"
.\mvnw.cmd spring-boot:run
```

## Endpoints

```text
POST /api/v1/auth/registro
POST /api/v1/auth/login
POST /api/v1/auth/check-telefono
POST /api/v1/auth/solicitar-codigo-correo
POST /api/v1/auth/verificar-codigo-correo

GET  /api/v1/usuarios/me
PATCH /api/v1/usuarios/me

GET    /api/v1/publicaciones
GET    /api/v1/publicaciones/mias
GET    /api/v1/publicaciones/{id}
GET    /api/v1/publicaciones/fotos/{fotoId}
POST   /api/v1/publicaciones
POST   /api/v1/publicaciones/{id}/fotos
DELETE /api/v1/publicaciones/{id}
```

Las rutas protegidas requieren:

```text
Authorization: Bearer TU_TOKEN_JWT
```

## Crear el repositorio separado

Desde fuera de la carpeta actual del app:

```powershell
Copy-Item -Recurse `
  ".\NSantiago_API" `
  "C:\Users\Colibecas\Downloads\NSantiago_API"

cd "C:\Users\Colibecas\Downloads\NSantiago_API"
git init
git add .
git commit -m "Crear API de N'Santiago"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/TU_REPO.git
git push -u origin main
```

Esta copia puede mantenerse en otra ubicación; no la subas dentro del repositorio Android.

## Desplegar en Railway

1. Crea un nuevo proyecto Railway.
2. Agrega PostgreSQL.
3. Selecciona **Deploy from GitHub Repo** y elige el repositorio `N'Santiago-API`.
4. Configura estas variables en Railway:

```text
DATABASE_URL=jdbc:postgresql://PUBLIC_HOST:PUBLIC_PORT/railway
PGUSER=postgres
PGPASSWORD=UNA_CONTRASENA_NUEVA
JWT_SECRET=UNA_CLAVE_ALEATORIA_DE_AL_MENOS_32_CARACTERES
JWT_EXPIRATION_MS=86400000
UPLOAD_DIR=/app/uploads
PUBLIC_BASE_URL=https://TU_DOMINIO_RAILWAY
CORS_ALLOWED_ORIGINS=*
```

5. Railway construirá el Dockerfile y asignará `PORT` automáticamente.
6. Verifica `https://TU_DOMINIO_RAILWAY/`.

## Conectar el app Android

En `NetworkModule.kt`, cambia:

```kotlin
const val BASE_URL = "https://TU_DOMINIO_RAILWAY/"
```
