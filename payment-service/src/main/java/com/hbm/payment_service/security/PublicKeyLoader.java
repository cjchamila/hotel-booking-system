package com.hbm.payment_service.security;


import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class PublicKeyLoader {

    public PublicKey  loadPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        ClassPathResource classPathResource = new ClassPathResource("/keys/public.pem");
        InputStream inputStream = classPathResource.getInputStream();
        byte[] bytes = inputStream.readAllBytes();
        String publicKey = new String(bytes);

        publicKey=publicKey.replace("-----BEGIN PUBLIC KEY-----","")
                .replace("-----END PUBLIC KEY-----","")
                .replaceAll("\\s","");

        byte[] decoded = Base64.getDecoder().decode(publicKey);

        X509EncodedKeySpec spec  = new X509EncodedKeySpec(decoded);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePublic(spec);
    }
}
