package com.commerxo.authserver.authserver.service.impl;

import com.commerxo.authserver.authserver.service.AuthenticatorService;
import com.j256.twofactorauth.TimeBasedOneTimePasswordUtil;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

@Service
public class AuthenticatorServiceImpl implements AuthenticatorService {

    private final BytesEncryptor bytesEncryptor;

    public AuthenticatorServiceImpl(BytesEncryptor bytesEncryptor) {
        this.bytesEncryptor = bytesEncryptor;
    }

    @Override
    public boolean check(String key, String code) {
        try {
            String secret = new String(this.bytesEncryptor.decrypt(Hex.decode(key)), StandardCharsets.UTF_8);
            return TimeBasedOneTimePasswordUtil.validateCurrentNumber(secret, Integer.parseInt(code), 1000);
        }
        catch (IllegalArgumentException ex){
            return false;
        }
        catch (GeneralSecurityException ex){
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public String generateSecret() {
        return TimeBasedOneTimePasswordUtil.generateBase32Secret();
    }

    @Override
    public String generateQrImageUrl(String keyId, String base32Secret) {
        return TimeBasedOneTimePasswordUtil.qrImageUrl(keyId, base32Secret);
    }

    @Override
    public String getCode(String base32Secret) throws GeneralSecurityException {
        return TimeBasedOneTimePasswordUtil.generateCurrentNumberString(base32Secret);
    }
}
