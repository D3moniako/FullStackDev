package com.souhail.weapp.AlphaShopWebService.security;

import com.xantrix.webapp.models.Utenti;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Log
@Service("customUserDetailsService")// il parametro name serve per identificare il servizio

public class CustomUserDetailsService implements UserDetailsService {// l'interfaccia implementata è di spring

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Autowired
    UserConfig config; // classe creata in security

    @Override
    public UserDetails loadUserByUsername(String userId)
            throws UsernameNotFoundException {
        String errMsg="";
        //prima di tutto verifichiamo che l'username non sia null e che si sia inserito qualcosa, potrei anche imporre che user id sia 5 cartteri o altro
        if(userId==null|| userId.length()<2)
        {
            errMsg="Nome utente assente o non valido";
            log.warning(errMsg);
            throw new  UsernameNotFoundException(errMsg);
        }
        // ORA DEVO OTTENERE I DATI QUEI DATI DAL MICROSERVIZIO
        // DATO CHE I DATI ARRIVANO IN FORMATO JSON DEVO EFFETTUARE òA DESERIALIZZAZIONE
        // CREO UNA CLASSE SIMILE ALLA CLASSE UTENTI
    // dal secondo metodo mi prendo utenti

        Utenti utente=this.GetHttpValue(userId);
        // se mi passa una variabile null allora faccio partire un errore
        if (utente== null){
            errMsg=String.format("Utente %s non Trovato!!",userId);
            log.warning(errMsg);
            throw new UsernameNotFoundException(errMsg);
        }
        //altrimenti devo costruire dei dati da quel utente

        User.UserBuilder builder=null;// istanzio un oggetto builder

        builder=org.springframework.security.core.userdetails.User.withUsername(utente.getUserId());

        builder.d

    }


    // metodo che richiede userId E ci resituisce un utente che viene dal secondo microservizio e e lo devo aggiungere come dipoendenza nel pom
    private Utenti GetHttpValue(String userId){
        // dobbiamo ottenere informazioni da microservizio getuser
        // per farlo modifico l'accesso del secondo modulo dall'application.yml dal modulo da cui accedo cioè questo in cui sono
        String errMsg="";
        URI url = null;// USO LA CLASSE URI PER CREARE URL COMPLETO IN MANIERA DINAMAICA
        try{
            String srvUrl=config.getSrvUrl();// IN CONFIG PRESO DA USER CONFIG ho URL
            url= new URI(srvUrl+userId);
        }catch (URISyntaxException e){
            e.printStackTrace();
        }

        /** INTERAZIONE CON SECONDO SERVIZIO GRAZIE AL REST TEMPLATE**/

        RestTemplate restTemplate=new RestTemplate();
        // a cui passo le specifiche di comunicazione
        // uso il metodo getInterceptor a cui aggiungo un autenticazione di base  a cui passo password e UserId, ricavata da classe UserConfig
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(config.getUserId(), config.getPassword()))


                //
        Utenti utente= null;
        try{
            utente = restTemplate.getForObject(url,Utenti.class);
        }catch (Exception e){
            String ErrMsg =String.format("Connessione al servizio di autenticazione non riuscita!!");
            log.warning(errMsg);
        }
        return utente;
    }
}
