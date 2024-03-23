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
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class RSAPrivateKeyConverter implements Serializer<RSAPrivateKey>, Deserializer<RSAPrivateKey> {

    private final TextEncryptor textEncryptor;

    public RSAPrivateKeyConverter(TextEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }

    @Override
    @SuppressWarnings("all")
    public RSAPrivateKey deserialize(InputStream inputStream) throws IOException {
        try{
            String pem = this.textEncryptor.decrypt(FileCopyUtils.copyToString(new InputStreamReader(inputStream)));
            String privateKeyPem = pem
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "");
            byte[] encoded = Base64.getMimeDecoder().decode(privateKeyPem);
            KeyFactory factory  = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            return (RSAPrivateKey) factory.generatePrivate(keySpec);
        }
        catch (Throwable throwable){
            throw new IllegalArgumentException(throwable);
        }
    }

    @Override
    @SuppressWarnings("all")
    public void serialize(RSAPrivateKey key, OutputStream outputStream) throws IOException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key.getEncoded());
        String encryptedKey = "-----BEGIN PRIVATE KEY-----\n" + Base64.getMimeEncoder().encodeToString(keySpec.getEncoded())
                + "\n-----END PRIVATE KEY-----";
        outputStream.write(this.textEncryptor.encrypt(encryptedKey).getBytes());
    }
}
