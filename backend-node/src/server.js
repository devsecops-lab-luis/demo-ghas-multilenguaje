// API de pedidos y catálogo - DEMO INTENCIONALMENTE VULNERABLE, NO USAR EN PRODUCCIÓN
const express = require('express');
const mysql = require('mysql');
const jwt = require('jsonwebtoken');
const { exec } = require('child_process');
const fs = require('fs');
const path = require('path');

const app = express();
app.use(express.json());

// Credenciales escritas en el código (CWE-798)
const JWT_SECRET = 'sahuayo-jwt-secreto-2024';
const db = mysql.createConnection({
  host: 'mysql-prod.sahuayo.local',
  user: 'api_pedidos',
  password: 'Sahuay0#Pedidos2024',
  database: 'pedidos',
});

// Login: firma un token con una llave fija
app.post('/api/login', (req, res) => {
  const token = jwt.sign({ usuario: req.body.usuario }, JWT_SECRET);
  res.json({ token });
});

// Inyección SQL (CWE-89)
app.get('/api/productos', (req, res) => {
  const sql = "SELECT * FROM productos WHERE categoria = '" + req.query.categoria + "'";
  db.query(sql, (err, rows) => res.json(rows));
});

// XSS reflejado (CWE-79)
app.get('/api/buscar', (req, res) => {
  res.send('<h2>Resultados para: ' + req.query.q + '</h2>');
});

// Path traversal (CWE-22)
app.get('/api/catalogo', (req, res) => {
  const archivo = path.join(__dirname, 'catalogos', req.query.archivo);
  fs.readFile(archivo, (err, data) => res.send(data));
});

// Inyección de comandos (CWE-78)
app.get('/api/estado-sucursal', (req, res) => {
  exec('ping -c 2 ' + req.query.host, (err, salida) => res.type('text').send(salida));
});

// Redirección abierta (CWE-601)
app.get('/api/salir', (req, res) => {
  res.redirect(req.query.regresar);
});

app.listen(3000, () => console.log('API Sahuayo en puerto 3000'));
