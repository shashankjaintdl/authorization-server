//package com.commerxo.authserver.authserver.config;
//
//import com.nimbusds.jose.util.StandardCharset;
//import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
//import nz.net.ultraq.thymeleaf.layoutdialect.decorators.strategies.GroupingStrategy;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Description;
//import org.springframework.context.support.ResourceBundleMessageSource;
//import org.springframework.format.FormatterRegistry;
//import org.springframework.http.MediaType;
//import org.springframework.web.servlet.LocaleResolver;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
//import org.springframework.web.servlet.i18n.SessionLocaleResolver;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
//import org.thymeleaf.spring6.ISpringTemplateEngine;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring6.view.ThymeleafViewResolver;
//import org.thymeleaf.templatemode.TemplateMode;
//import org.thymeleaf.templateresolver.ITemplateResolver;
//
//import java.util.Locale;
//
//@Configuration
//@ComponentScan
//public class ThymeleafConfig implements WebMvcConfigurer, ApplicationContextAware {
//
//
//    private ApplicationContext applicationContext;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
//
//
//    @Bean
//    public ViewResolver viewResolver() {
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//        resolver.setTemplateEngine(templateEngine());
//        resolver.setCharacterEncoding("UTF-8");
//        return resolver;
//    }
//
//    @Bean
//    public ISpringTemplateEngine templateEngine() {
//        SpringTemplateEngine engine = new SpringTemplateEngine();
//        engine.setEnableSpringELCompiler(true);
//        engine.setTemplateResolver(templateResolver());
//        return engine;
//    }
//
//    private ITemplateResolver templateResolver() {
//        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
//        resolver.setApplicationContext(applicationContext);
//        resolver.setPrefix("/WEB-INF/views/");
//        resolver.setTemplateMode(TemplateMode.HTML);
//        return resolver;
//    }
//
////    @Bean
////    public ViewResolver htmlViewResolver(){
////        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
////        resolver.setTemplateEngine(templateEngine(htmlTemplateResolver()));
////        resolver.setContentType(MediaType.TEXT_HTML_VALUE);
////        resolver.setCharacterEncoding(StandardCharset.UTF_8.name());
////        resolver.setViewNames(new String[]{"*.html"});
////        return resolver;
////    }
////
////    private ITemplateResolver htmlTemplateResolver(){
////        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
////        templateResolver.setApplicationContext(applicationContext);
////        templateResolver.setPrefix("/WEB-INF/views/");
////        templateResolver.setCacheable(false);
////        templateResolver.setTemplateMode(TemplateMode.HTML);
////        return templateResolver;
////    }
////
////    @Bean
////    public ViewResolver javaScriptViewResolver(){
////        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
////        resolver.setTemplateEngine(templateEngine(javaScriptTemplateResolver()));
////        resolver.setContentType("application/javascript");
////        resolver.setCharacterEncoding(StandardCharset.UTF_8.name());
////        resolver.setViewNames(new String[]{"*.js"});
////        return resolver;
////    }
////
////    private ITemplateResolver javaScriptTemplateResolver(){
////        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
////        templateResolver.setApplicationContext(applicationContext);
////        templateResolver.setPrefix("/WEB-INF/js/");
////        templateResolver.setCacheable(false);
////        templateResolver.setTemplateMode(TemplateMode.JAVASCRIPT);
////        return templateResolver;
////    }
////
////    @Bean
////    public ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver){
////        SpringTemplateEngine engine = new SpringTemplateEngine();
////        engine.addDialect(new LayoutDialect(new GroupingStrategy()));
////        engine.addDialect(new Java8TimeDialect());
////        engine.setEnableSpringELCompiler(true);
////        engine.setTemplateResolver(htmlTemplateResolver());
//////        engine.setTemplateEngineMessageSource(messageSource());
////        return engine;
////    }
////
////    @Bean
////    public ResourceBundleMessageSource messageSource(){
////        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
////        resourceBundleMessageSource.setBasename("messages");
////        return resourceBundleMessageSource;
////    }
////
////
////    @Override
////    public void addResourceHandlers(ResourceHandlerRegistry registry) {
////        registry.addResourceHandler("/resources/**", "/css/**")
////                .addResourceLocations("/WEB-INF/resources/", "/WEB-INF/css/");
////    }
////
////    @Override
////    @Description("Custom Conversion Service")
////    public void addFormatters(FormatterRegistry registry) {
////        registry.addFormatter(new NameFormatter());
////    }
//
//}