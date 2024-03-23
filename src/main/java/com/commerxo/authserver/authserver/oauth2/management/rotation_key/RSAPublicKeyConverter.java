package com.commerxo.authserver.authserver.oauth2.management.rotation_key;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@SuppressWarnings("all")
public class RSAPublicKeyConverter implements Serializer<RSAPublicKey>, Deserializer<RSAPublicKey> {

    private final TextEncryptor textEncryptor;

    public RSAPublicKeyConverter(TextEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }

    @Override
    public RSAPublicKey deserialize(InputStream inputStream) throws IOException {
        try{
            String pem = this.textEncryptor.decrypt(FileCopyUtils.copyToString(new InputStreamReader(inputStream)));
            String publicKeyPEM = pem
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "");
            byte[] encoded = Base64.getMimeDecoder().decode(publicKeyPEM);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        }
        catch (Throwable throwable) {
            throw new IllegalArgumentException("There's been an exception", throwable);
      }
    }

    @Override
    public void serialize(RSAPublicKey key, OutputStream outputStream) throws IOException {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key.getEncoded());
        String encPublickKey = "-----BEGIN PUBLIC KEY-----\n" +
                Base64.getMimeEncoder().encodeToString(x509EncodedKeySpec.getEncoded()) +
                "\n-----END PUBLIC KEY-----";
        outputStream.write(this.textEncryptor.encrypt(encPublickKey).getBytes());
    }
}
