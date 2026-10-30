package com.sahuayo.clientes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

// Cifrado de datos de tarjetas del sistema legado - DEMO INTENCIONALMENTE VULNERABLE
public class CifradoUtil {

    // Llave fija en el código y algoritmo roto (DES) (CWE-327)
    private static final byte[] LLAVE = "Sahuayo1".getBytes();

    public static String cifrar(String texto) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(LLAVE, "DES"));
        return Base64.getEncoder().encodeToString(cipher.doFinal(texto.getBytes()));
    }
}
