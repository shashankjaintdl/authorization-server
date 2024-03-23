package com.commerxo.authserver.authserver.oauth2.management.rotation_key;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.List;

public interface RSAKeyPairRepository {

    record RsaKeyPair(String id, Instant created, RSAPublicKey publicKey, RSAPrivateKey privateKey) { }

    List<RsaKeyPair> findKeyPairs();

    void delete(String id);

    void save(RsaKeyPair rsaKeyPair);
}

