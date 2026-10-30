# demo-ghas-sahuayo

> ⚠️ **Repositorio intencionalmente vulnerable** para demostrar GitHub Advanced Security (GHAS) y su integración con Microsoft Defender for Cloud. No desplegar.

Simula el stack tecnológico de la empresa: backend actual en Node.js, sistemas legados en C# y Java, y apps móviles en Kotlin y Swift. Todos los lenguajes son analizados por **CodeQL**, con **Copilot Autofix**.

| Carpeta | Lenguaje | Vulnerabilidades incluidas |
|---|---|---|
| `backend-node/` | JavaScript (Node.js / Express) | Inyección SQL, XSS, path traversal, inyección de comandos, redirección abierta, credenciales en código |
| `legacy-csharp/` | C# (ASP.NET Core) | Inyección SQL, path traversal, inyección de comandos, DES, contraseña en cadena de conexión |
| `legacy-java/` | Java (Spring) | Inyección SQL, path traversal, inyección de comandos, log injection, DES, credenciales en código |
| `mobile/android/` | Kotlin (Android) | WebView inseguro, inyección SQL, datos sensibles en logs, TLS sin validar, app debuggable |
| `mobile/ios/` | Swift (iOS) | Contraseña en UserDefaults, contraseña en logs, TLS 1.0, contraseña por HTTP |

| Archivo de dependencias | Ejemplos de librerías con CVE (Dependabot) |
|---|---|
| `backend-node/package.json` | lodash 4.17.15, axios 0.21.0, jsonwebtoken 8.5.0, minimist 1.2.0, express 4.16.0 |
| `legacy-java/pom.xml` | log4j-core 2.14.1 (Log4Shell), spring-webmvc 5.3.12, jackson-databind 2.9.8 |
| `legacy-csharp/PedidosLegacy.csproj` | Newtonsoft.Json 12.0.1, System.Data.SqlClient 4.8.0, log4net 2.0.8 |
| `mobile/android/app/build.gradle.kts` | okhttp 3.12.0, gson 2.8.5 |
