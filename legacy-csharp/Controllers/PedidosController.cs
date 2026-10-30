// Sistema legado de pedidos - DEMO INTENCIONALMENTE VULNERABLE, NO USAR EN PRODUCCIÓN
using System.Data.SqlClient;
using System.Diagnostics;
using System.Security.Cryptography;
using System.Text;
using Microsoft.AspNetCore.Mvc;

namespace PedidosLegacy.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class PedidosController : ControllerBase
    {
        // Contraseña dentro de la cadena de conexión (CWE-798)
        private const string ConnectionString =
            "Server=sql-legacy.sahuayo.local;Database=Pedidos;User Id=sa;Password=Sahuay0#2017;";

        // Inyección SQL (CWE-89)
        [HttpGet("buscar")]
        public IActionResult Buscar([FromQuery] string cliente)
        {
            using var conn = new SqlConnection(ConnectionString);
            conn.Open();
            var cmd = new SqlCommand("SELECT * FROM Pedidos WHERE Cliente = '" + cliente + "'", conn);
            using var reader = cmd.ExecuteReader();
            var pedidos = new List<string>();
            while (reader.Read())
            {
                pedidos.Add(reader["Folio"].ToString());
            }
            return Ok(pedidos);
        }

        // Path traversal (CWE-22)
        [HttpGet("reporte")]
        public IActionResult Reporte([FromQuery] string archivo)
        {
            var contenido = System.IO.File.ReadAllText("/srv/reportes/" + archivo);
            return Content(contenido);
        }

        // Inyección de comandos (CWE-78)
        [HttpGet("ping")]
        public IActionResult Ping([FromQuery] string host)
        {
            Process.Start("/bin/sh", "-c \"ping -c 2 " + host + "\"");
            return Ok("Ping enviado");
        }

        // Cifrado roto: DES (CWE-327)
        [HttpGet("cifrar")]
        public IActionResult Cifrar([FromQuery] string dato)
        {
            using var des = DES.Create();
            des.Key = Encoding.ASCII.GetBytes("Sahuayo1");
            des.IV = Encoding.ASCII.GetBytes("12345678");
            var bytes = Encoding.UTF8.GetBytes(dato);
            var cifrado = des.CreateEncryptor().TransformFinalBlock(bytes, 0, bytes.Length);
            return Ok(Convert.ToBase64String(cifrado));
        }
    }
}
