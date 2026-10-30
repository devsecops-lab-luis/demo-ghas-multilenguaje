package com.sahuayo.app

// App móvil Android - DEMO INTENCIONALMENTE VULNERABLE, NO USAR EN PRODUCCIÓN

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.webkit.WebView

class MainActivity : Activity() {

    // Llave de API escrita en el código (CWE-798)
    private val apiKey = "sahuayo-mobile-api-key-2024-prod"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // WebView con JavaScript y acceso a archivos que carga una URL recibida por intent (CWE-749)
        val webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowUniversalAccessFromFileURLs = true
        val url = intent.getStringExtra("url")
        if (url != null) {
            webView.loadUrl(url)
        }
        setContentView(webView)

        // Inyección SQL con datos del intent (CWE-89)
        val pedido = intent.getStringExtra("pedido")
        val db = openOrCreateDatabase("pedidos.db", MODE_PRIVATE, null)
        db.rawQuery("SELECT * FROM pedidos WHERE folio = '" + pedido + "'", null)

        // Datos sensibles en el log (CWE-532)
        val password = intent.getStringExtra("password")
        Log.d("Login", "Usuario autenticado con password: " + password)
    }
}
