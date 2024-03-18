package com.commerxo.authserver.authserver.security;

import com.commerxo.authserver.authserver.repository.JpaUserRegistrationRepository;
import com.commerxo.authserver.authserver.security.mfa.MFAHandler;
import com.commerxo.authserver.authserver.service.RoleService;
import com.commerxo.authserver.authserver.service.impl.UserRegistrationServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;

import static com.commerxo.authserver.authserver.common.WebConstants.Account.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final JwtDecoder jwtDecoder;
    private final BytesEncryptor bytesEncryptor;
    private final RoleService roleService;
    private final JpaUserRegistrationRepository userRegistrationRepository;

    public SecurityConfig(PasswordEncoder passwordEncoder, JwtDecoder jwtDecoder, BytesEncryptor bytesEncryptor, RoleService roleService, JpaUserRegistrationRepository userRegistrationRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtDecoder = jwtDecoder;
        this.bytesEncryptor = bytesEncryptor;
        this.roleService = roleService;
        this.userRegistrationRepository = userRegistrationRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
                    authorize.requestMatchers("/mfa/registration","/registration", "/authenticator").hasAuthority("ROLE_MFA_REQUIRED");
                    authorize.requestMatchers(ACCOUNT + USER_ACCOUNT_REGISTER, "/login**","/error").permitAll();
                    authorize.anyRequest().authenticated();
                })
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
                .oauth2ResourceServer(oauth2->
                        oauth2.jwt(jwtConfigurer ->
                                jwtConfigurer
                                        .authenticationManager(providerManager())
                        )
                ).exceptionHandling(ex ->
                        ex.authenticationEntryPoint(
                                (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                ).formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(new MFAHandler("/authenticator","ROLE_MFA_REQUIRED"))
                        .failureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"))
                );
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetails());
        return provider;
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(){
        return new DefaultAuthenticationEventPublisher();
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(){
        return new JwtAuthenticationProvider(jwtDecoder);
    }

    @Bean
    public ProviderManager providerManager(){
        ProviderManager providerManager = new ProviderManager(daoAuthenticationProvider(),jwtAuthenticationProvider());
        providerManager.setAuthenticationEventPublisher(authenticationEventPublisher());
        return providerManager;
    }



    private Converter<Jwt, Collection<GrantedAuthority>> getJwtGrantedAuthoritiesConverter() {
        return new DelegatingJwtGrantedAuthoritiesConverter(
                new JwtGrantedAuthoritiesConverter()
        );
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(false)
                .ignoring()
                .requestMatchers("/webjars/**", "/images/**", "/css/**", "/assets/**", "/favicon.ico");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Bean
    public UserDetailsService userDetails(){
        return new UserRegistrationServiceImpl(userRegistrationRepository, bytesEncryptor, roleService);
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }
}