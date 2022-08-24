package com.souhail.weapp.AlphaShopWebService.webapp;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MessageConfig {

    @Bean(name="validator") // questo bean che rinomino cosi per comodità, esiste giò in spring senza metterlo nel pom
                            //  come al solito creo una sua istanza e poi lo uso per settare l'origine del messaggio
    public LocalValidatorFactoryBean validator(){
        LocalValidatorFactoryBean bean=new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource()); // messageSource e un altro bean creato sotto a cui faccio riferimento
        return  bean;

    }
    @Bean
    public SessionLocaleResolver localResolver(){ // ci permette di specificare la lingua
        SessionLocaleResolver  sessionLocaleResolver= new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(new Locale("it")); // forzo il sistema a usare una lingua, oppure
       // sessionLocaleResolver.setDefaultLocale(LocaleContextHolder.getLocale());// GLI DICO DI PRENDERSI in automatico la  lingua in base alla lingua dove sta girando il web service

        return sessionLocaleResolver;
    }
    @Bean
    public MessageSource messageSource(){ // ci permette di specificare il basename del file
        ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
        resource.setBasename("messages");
        resource.setUseCodeAsDefaultMessage(true);
        return resource;

    }

    // dopo queste config devo creare il file con i messaggi , nel src/main/resource
    // lo chiami messages come richiesto da messageaSource e aggiungo _it.properties quindi messages_it.properties
    // gli _it _fr _us serve per sapere la nazione

}
