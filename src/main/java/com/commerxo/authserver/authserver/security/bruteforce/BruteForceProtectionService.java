package com.commerxo.authserver.authserver.security.bruteforce;

public interface BruteForceProtectionService {

    void registerLoginFailure(String username);

    void resetBruteForceCounter(String username);

    boolean isBruteForceAttack(String username);

}
