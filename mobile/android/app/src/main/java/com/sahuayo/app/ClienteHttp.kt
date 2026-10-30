package com.sahuayo.app

import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

// Cliente HTTPS que acepta cualquier certificado (CWE-295) - DEMO INTENCIONALMENTE VULNERABLE
object ClienteHttp {

    private val confiarEnTodo = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }

    fun desactivarValidacionTls() {
        val contexto = SSLContext.getInstance("TLS")
        contexto.init(null, arrayOf<TrustManager>(confiarEnTodo), java.security.SecureRandom())
        HttpsURLConnection.setDefaultSSLSocketFactory(contexto.socketFactory)
        HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
    }
}
