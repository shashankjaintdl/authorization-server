//package com.commerxo.authserver.authserver.config;
//
//import com.commerxo.authserver.authserver.security.SecurityConfig;
//import jakarta.servlet.ServletRegistration;
//import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//
//public class WebApp extends AbstractAnnotationConfigDispatcherServletInitializer {
//
//    public WebApp() {
//        super();
//    }
//
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return null;
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class<?>[] { ThymeleafConfig.class, SecurityConfig.class};
//    }
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[] { "/" };
//    }
//
//    @Override
//    protected void customizeRegistration(final ServletRegistration.Dynamic registration) {
//        super.customizeRegistration(registration);
//    }
//
//}
