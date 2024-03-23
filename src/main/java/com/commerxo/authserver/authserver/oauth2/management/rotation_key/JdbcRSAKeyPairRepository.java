package com.commerxo.authserver.authserver.oauth2.management.rotation_key;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class JdbcRSAKeyPairRepository implements RSAKeyPairRepository{

    private final JdbcTemplate jdbcTemplate;
    private final RSAPrivateKeyConverter privateKeyConverter;
    private final RSAPublicKeyConverter publicKeyConverter;
    private final RsaKeyPairRowMapper rsaKeyPairRowMapper;

    public JdbcRSAKeyPairRepository(JdbcTemplate jdbcTemplate,
                                    RSAPrivateKeyConverter privateKeyConverter,
                                    RSAPublicKeyConverter publicKeyConverter,
                                    RsaKeyPairRowMapper rsaKeyPairRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.privateKeyConverter = privateKeyConverter;
        this.publicKeyConverter = publicKeyConverter;
        this.rsaKeyPairRowMapper = rsaKeyPairRowMapper;
    }

    @Override
    public List<RsaKeyPair> findKeyPairs() {
        String sql = "select * from rsa_key_pairs order by created desc";
        return jdbcTemplate.query(sql, this.rsaKeyPairRowMapper);
    }

    @Override
    public void delete(String id) {
        String sql = "delete from rsa_key_pairs where id = ?";
        this.jdbcTemplate.update(sql);
    }

    @Override
    public void save(RsaKeyPair rsaKeyPair) {
        String sql = "insert into rsa_key_pairs (id, created, public_key, private_key ) values (?, ?, ?, ?)";
        try (ByteArrayOutputStream privateBaos = new ByteArrayOutputStream(); var publicBaos = new ByteArrayOutputStream()) {
            this.privateKeyConverter.serialize(rsaKeyPair.privateKey(), privateBaos);
            this.publicKeyConverter.serialize(rsaKeyPair.publicKey(), publicBaos);
            this.jdbcTemplate.update(sql,
                    rsaKeyPair.id(),
                    new Date(rsaKeyPair.created().toEpochMilli()),
                    publicBaos.toString(),
                    privateBaos.toString());
        }
        catch (IOException e) {
            throw new IllegalArgumentException("there's been an exception", e);
        }


    }


}
