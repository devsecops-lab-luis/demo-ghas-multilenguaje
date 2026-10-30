package com.sahuayo.clientes;

// Sistema legado de clientes - DEMO INTENCIONALMENTE VULNERABLE, NO USAR EN PRODUCCIÓN

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private static final Logger log = LogManager.getLogger(ClienteController.class);

    // Credenciales escritas en el código (CWE-798)
    private static final String DB_URL = "jdbc:mysql://sql-legacy.sahuayo.local:3306/clientes";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "Sahuay0#Legacy2016";

    // Inyección SQL (CWE-89)
    @GetMapping("/buscar")
    public List<String> buscar(@RequestParam String nombre) throws SQLException {
        log.info("Búsqueda de cliente: " + nombre); // Log injection (CWE-117), log4j 2.14.1 = Log4Shell
        List<String> resultado = new ArrayList<>();
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT nombre FROM clientes WHERE nombre = '" + nombre + "'");
        while (rs.next()) {
            resultado.add(rs.getString("nombre"));
        }
        return resultado;
    }

    // Path traversal (CWE-22)
    @GetMapping("/factura")
    public byte[] factura(@RequestParam String archivo) throws IOException {
        return Files.readAllBytes(Paths.get("/srv/facturas/" + archivo));
    }

    // Inyección de comandos (CWE-78)
    @GetMapping("/respaldo")
    public String respaldo(@RequestParam String destino) throws IOException {
        Runtime.getRuntime().exec(new String[] {"sh", "-c", "tar -czf " + destino + " /srv/clientes"});
        return "Respaldo iniciado";
    }
}
