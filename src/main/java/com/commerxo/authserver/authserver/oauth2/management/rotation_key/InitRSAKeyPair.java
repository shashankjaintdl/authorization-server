package com.commerxo.authserver.authserver.oauth2.management.rotation_key;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class InitRSAKeyPair  implements ApplicationRunner {


    private final RSAKeyPairRepository repository;
    private final Keys keys;

    public InitRSAKeyPair(RSAKeyPairRepository repository, Keys keys) {
        this.repository = repository;
        this.keys = keys;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (this.repository.findKeyPairs().isEmpty()) {
            RSAKeyPairRepository.RsaKeyPair keypair = keys.generateKeyPair(Instant.now());
            this.repository.save(keypair);
        }
    }
}
