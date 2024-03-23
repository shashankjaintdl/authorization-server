package com.commerxo.authserver.authserver.oauth2.management.rotation_key;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
public class KeyConfig {

    @Bean
    public TextEncryptor textEncryptor(@Value("${app.jwt.encryptor.password}") String password,
                                       @Value("${app.jwt.encryptor.salt}") String salt){
        return Encryptors.text(password, salt);
    }

    @Bean
    public NimbusJwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }


}
