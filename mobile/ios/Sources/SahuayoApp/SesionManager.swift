// App móvil iOS - DEMO INTENCIONALMENTE VULNERABLE, NO USAR EN PRODUCCIÓN
import Foundation

public final class SesionManager {

    // Llave de API escrita en el código (CWE-798)
    let apiKey = "sahuayo-ios-api-key-2024-prod"

    public init() {}

    // Contraseña guardada en texto claro en UserDefaults (CWE-312)
    public func guardarCredenciales(usuario: String, password: String) {
        UserDefaults.standard.set(usuario, forKey: "usuario")
        UserDefaults.standard.set(password, forKey: "password")
        // Contraseña escrita en el log (CWE-532)
        NSLog("Inicio de sesión de %@ con password %@", usuario, password)
    }

    // Sesión que acepta TLS 1.0 (CWE-757)
    public func crearSesion() -> URLSession {
        let config = URLSessionConfiguration.default
        config.tlsMinimumSupportedProtocolVersion = .TLSv10
        return URLSession(configuration: config)
    }

    // Contraseña enviada por HTTP sin cifrar (CWE-319)
    public func iniciarSesion(usuario: String, password: String) {
        let url = URL(string: "http://api.sahuayo.local/login?usuario=\(usuario)&password=\(password)")!
        crearSesion().dataTask(with: url) { _, _, _ in }.resume()
    }
}
