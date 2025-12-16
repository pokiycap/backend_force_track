Conectar backend a Supabase (Postgres)
=====================================

Pasos rápidos para configurar la conexión entre Frontend, Backend y Supabase:

1) Conseguir credenciales de Supabase:
   - Host/DB URL: `jdbc:postgresql://<host>:5432/postgres?sslmode=require`
   - DB usuario: por defecto `postgres` (o el que te provea Supabase)
   - DB password: la contraseña del proyecto Supabase
   - Opcional: `SUPABASE_URL` y `SUPABASE_KEY` si quieres usar la API REST de Supabase desde el backend.

2) Opciones para suministrar las credenciales al backend (elige una):
   - Crear `src/main/resources/application-local.properties` con los valores (basado en `application-local.properties.example`).
   - O setear variables de entorno en tu sistema: `DB_URL`, `DB_USER`, `DB_PASSWORD`, `SUPABASE_URL`, `SUPABASE_KEY`.

3) Ejemplo (PowerShell) para ejecutar con variables de entorno temporales:
```powershell
$env:DB_URL = 'jdbc:postgresql://db.xxxxxx.supabase.co:5432/postgres?sslmode=require'
$env:DB_USER = 'postgres'
$env:DB_PASSWORD = 'tu_password'
.\mvnw.cmd spring-boot:run
```

4) Ajustes en el frontend:
   - En `app/src/main/java/com/example/forcetrack/config/ApiConfig.kt` asegúrate de usar la URL del backend desplegado (o `10.0.2.2:8080` para emulador).

5) Prueba:
   - Crear un usuario desde la app o con curl (ver README principal). Comprobar que las filas aparecen en Supabase (tabla `usuarios`).

Si quieres, pega aquí las credenciales (o dímelas por privado) y yo puedo:
- crear `src/main/resources/application-local.properties` con tus valores, y
- arrancar y probar la inserción desde la app y verificar en Supabase.
