package com.commerxo.authserver.authserver.twillio;

import com.twilio.Twilio;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitializerConfiguration {

    private final TwilioConfiguration twilloConfiguration;

    public TwilioInitializerConfiguration(TwilioConfiguration twilloConfiguration) {
        this.twilloConfiguration = twilloConfiguration;
        init();
    }

    private void init(){
        Twilio.init(
                twilloConfiguration.getSid(),
                twilloConfiguration.getToken()
        );
    }
}
