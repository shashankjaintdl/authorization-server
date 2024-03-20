package com.commerxo.authserver.authserver.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class RestTemplateConfig {

    @Bean
    @RequestScope
    public RestTemplate keycloakRestTemplate(HttpServletRequest inReq) {
        // retrieve the auth header from incoming request
        final String authHeader = inReq.getHeader(HttpHeaders.AUTHORIZATION);
        final RestTemplate restTemplate = new RestTemplate();
        // add a token if an incoming auth header exists, only
        if (authHeader != null && !authHeader.isEmpty()) {
            // since the header should be added to each outgoing request,
            // add an interceptor that handles this.
            restTemplate.getInterceptors().add((outReq, bytes, clientHttpReqExec) -> {
                outReq.getHeaders().set(HttpHeaders.AUTHORIZATION, authHeader);
                outReq.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");
                return clientHttpReqExec.execute(outReq, bytes);
            });

        } else {
            restTemplate.getInterceptors().add((outReq, bytes, clientHttpReqExec) -> {
                outReq.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");
                return clientHttpReqExec.execute(outReq, bytes);
            });
        }
        return restTemplate;
    }


    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }


}
