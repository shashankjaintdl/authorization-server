package com.commerxo.authserver.authserver.security.mfa;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class CodeStore {

    private String generatedCode;
    private String base32Secret;

    public void saveAll(String generatedCode, String base32Secret){
        this.generatedCode = generatedCode;
        this.base32Secret = base32Secret;
    }

    public String getGeneratedCode() {
        return generatedCode;
    }

    public void saveGeneratedCode(String generatedCode){
        this.generatedCode =  generatedCode;
    }

    public String getBase32Secret(){
        return base32Secret;
    }

    public void saveBase32Secret(String base32Secret){
        this.base32Secret = base32Secret;
    }
}
