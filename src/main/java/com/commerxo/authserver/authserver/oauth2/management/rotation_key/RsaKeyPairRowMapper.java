package com.commerxo.authserver.authserver.oauth2.management.rotation_key;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;

import static com.commerxo.authserver.authserver.oauth2.management.rotation_key.RSAKeyPairRepository.RsaKeyPair;

@Component
public class RsaKeyPairRowMapper implements RowMapper<RsaKeyPair> {

    private final RSAPrivateKeyConverter rsaPrivateKeyConverter;
    private final RSAPublicKeyConverter rsaPublicKeyConverter;

    RsaKeyPairRowMapper(
            RSAPrivateKeyConverter rsaPrivateKeyConverter,
            RSAPublicKeyConverter rsaPublicKeyConverter) {
        this.rsaPrivateKeyConverter = rsaPrivateKeyConverter;
        this.rsaPublicKeyConverter = rsaPublicKeyConverter;
    }

    @Override
    public RsaKeyPair mapRow(ResultSet rs, int rowNum) throws SQLException {

        try {
            byte[] publicKeyBytes = rs.getString("public_key").getBytes();
            RSAPublicKey publicKey = this.rsaPublicKeyConverter.deserializeFromByteArray(publicKeyBytes);
            byte[] privateKeyBytes = rs.getString("private_key").getBytes();
            RSAPrivateKey privateKey = this.rsaPrivateKeyConverter.deserializeFromByteArray(privateKeyBytes);

            Instant created = new Date(rs.getDate("created").getTime()).toInstant();
            return new RsaKeyPair(
                    rs.getString("id"),
                    created,
                    publicKey,
                    privateKey);
        }
        catch (Exception  e) {
            throw new RuntimeException(e);
        }

    }

}
