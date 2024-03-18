package com.commerxo.authserver.authserver.service;

import java.security.GeneralSecurityException;

public interface AuthenticatorService {

    boolean check(String key, String code);

    String generateSecret();

    String generateQrImageUrl(String keyId, String base32Secret);

    String getCode(String base32Secret) throws GeneralSecurityException;

}
