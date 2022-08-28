package com.souhail.weapp.AlphaShopWebService.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.*;

@Configuration
@EnableWebSecurity// annotation per abilitare la sicurezza
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    // CREO UTENTI HARDCODED

    private static String REALM = "REAME";// una variabile che serve per configurare i messaggi di errore

    //NON INSERISCO PIù I DATI HARDCODE//
    /*@Bean
    @Override
    public UserDetailsService userDetailsService() {
        User.UserBuilder users= User.builder(); // COSTRUISCE UTENTI DIRETAAMENTE DALLA CLASSE USER
        InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();// GLI UTENTI NON SONO SALVATI NEL DB MA IN MEMORIA RAM BROWSER

        //1 UTENTE USER
        // InMemoryUserDetailsManager ha metodo create user in qui passo in catena users che ha metodo username per specificare tipo
        //poi a password passo una stringa ma criptata tramite classe  BCryptPasswordEncoder() che ha il suo metodo di criptaggio encode
         manager.createUser(users.username("Souhail")
                .password(new BCryptPasswordEncoder().encode("D3mondante.@"))
                 .roles("USER") // passo poi ruolo e infine faccio costruire il tutto
                 .build()
         );

         //2 UTENTE USER
        manager.createUser(users.username("Admin")
                .password(new BCryptPasswordEncoder().encode("toor"))
                .roles("USER","ADMIN")// roles accetta una lista di ruoli
                .build()
        );
        return manager;
    }*/
    @Autowired
    @Qualifier("customerUserDetailsService")
// è lo stesso nome inserito nella notazione service , permette di dare altri nomi e usare quelli come riferimento per le varie annotation
    private UserDetailsService userDetailsService;

    //CONFIGURO LA SICUREZZA

    // creo un array di costanti in cui ho gli url accessibili dai tipi di utenti
    private static final String[] USER_MATCHER = {"api/articoli/cerca/**"};
    private static final String[] ADMIN_MATCHER = {
            "api/articoli/inserisci/**",// ** indica ogni cosa se ne metto 3 non funziona
            "api/articoli/modifica/**",
            "api/articoli/elimina/**"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()        // csrf è un metodo che contrasta uno specifico attacco hacker ma che non serve nei service

                .authorizeRequests()// AUTORIZZA AD ACCEDERE A SOLO DEGLI PATH URL IN BASE AL TIPO USER
                .antMatchers(USER_MATCHER).hasAnyAuthority("USER")
                .antMatchers(ADMIN_MATCHER).hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()                    // gli dice che vale per ogni richiesta autenticata
                .and()
                .httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())// AuthEntryPoint è una classe che mi  creo per specificare gli entrypoint permessi
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//la sessione sarà stateless lavorando in REST
        ;
    }

    /**
     * @param auth riceve   userDetailsService
     *             che è globalmente visibile e
     *             in base ad esso gestisce l'autenticazione
     **/
    @Autowired
    // faccio l'autowired del metodo stesso per usarlo dove mi pare senza richiamare l'intero oggetto in cui è contenuto
    public void configurationGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        // passo al gestore di autenticazione  auth lo userDetails e cripto la password , lui quindi
        auth.
                userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean // SISTEMA DI AUTENTICAZIONE
    public AuthEntryPoint getBasicAuthEntryPoint() {
        return new AuthEntryPoint();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        // tutti i metodi che riguardano option e qualsiasi url dovranno essere
        // IGNORATI DALLA SICUREZZA, è FONDAMENTALE PER FUNZIONARE CON IL FRONTEND
    }
}
