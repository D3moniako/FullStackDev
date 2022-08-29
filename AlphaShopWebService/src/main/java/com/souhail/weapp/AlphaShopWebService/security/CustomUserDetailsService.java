package com.souhail.weapp.AlphaShopWebService.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Log
@Service("customerUserDetailsService")// il parametro name serve per identificare il servizio

public class CustomUserDetailsService implements UserDetailsService {// l'interfaccia implementata è di spring


    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Autowired
    UserConfig config; // classe creata in security per immagazinare srvurl userid e password

    @Override
    public UserDetails loadUserByUsername(String userId) // classe da usare nella clasee secutity config , ha un oggetto userdetails con i ruoli dei vari profili passworde se sono abilitati
            throws UsernameNotFoundException {
        String errMsg = "";
        //prima di tutto verifichiamo che l'username non sia null e che si sia inserito qualcosa, potrei anche imporre che user id sia 5 caratteri o altro
        if (userId == null || userId.length() < 2) {
            errMsg = "Nome utente assente o non valido";
            log.warning(errMsg);
            throw new UsernameNotFoundException(errMsg);
        }
        // ORA DEVO OTTENERE I DATI QUEI DATI DAL MICROSERVIZIO
        // DATO CHE I DATI ARRIVANO IN FORMATO JSON DEVO EFFETTUARE òA DESERIALIZZAZIONE
        // CREO UNA CLASSE SIMILE ALLA CLASSE UTENTI
        // dal secondo metodo mi prendo utenti
        // utenti è una classe public nello stesso pacchetto quindi la posso usare senza istanziarla
        Utenti utente = this.GetHttpValue(userId);// variabile a cui passo metodo GetHTTPvalue che mi torna un oggetto utente
        // se mi passa una variabile null allora faccio partire un errore
        if (utente == null) {
            errMsg = String.format("Utente %s non Trovato!!", userId);
            log.warning(errMsg);
            throw new UsernameNotFoundException(errMsg);
        }
        //altrimenti devo costruire dei dati da quel utente trasformandoli in userdetails che poi viene usato dal sistema di sicurezza
        // COME LO FACCIO?
        UserBuilder builder = null;// istanzio un oggetto builder

        builder = org.springframework.security.core.userdetails.User.withUsername(utente.getUserId());
        builder.disabled((utente.getAttivo().equals("Si") ? false : true));
        // passo al builder dall'utente lo stato utente che è un booleano,se lo stato è si allora è falso altrimenti true
        builder.password(utente.getPassword()); // passo anche la password ricavata da db mongo e salvata in utenti

        // creo un array di profili ottenendo i ruoli a cui applico una stream map che trasforma ogni
        // elemento come stringa "ROLE"+ruolo e poi con toArray() salvo come array di stringhe , esempio
        // esempio diventa ROLE_Admin ROLE_user etc perchè devono essere di questo formato per essere usati nella security
        String[] profili = utente.getRuoli().stream().map(a -> "ROLE_" + a).toArray(String[]::new);

        builder.authorities(profili);// passo al builder array dei ruoli autorizzati
        return builder.build();// passo l'oggetto costruito NOTA il build è un metodo del builder che ritorna un oggetto di tipo UserDetails
    }


    /**
     * metodo per ricavare gli dati utenti da classe utente passandogli userId
     *
     * @param userId
     * @return Utenti
     **/
    // metodo che richiede userId E ci resituisce un utente che viene dal secondo microservizio e e lo devo aggiungere come dipoendenza nel pom
    private Utenti GetHttpValue(String userId) {
        // dobbiamo ottenere informazioni da microservizio getuser
        // per farlo modifico l'accesso del secondo modulo dall'application.yml dal modulo da cui accedo cioè questo in cui sono
        String errMsg = "";
        URI url = null;// USO LA CLASSE URI PER CREARE URL COMPLETO IN MANIERA DINAMAICA
        try {
            String srvUrl = config.getSrvUrl();// IN CONFIG PRESO DA USER CONFIG ho URL
            url = new URI(srvUrl + userId);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        /** INTERAZIONE CON SECONDO SERVIZIO GRAZIE AL REST TEMPLATE**/

        RestTemplate restTemplate = new RestTemplate();
        // a cui passo le specifiche di comunicazione
        // uso il metodo getInterceptor a cui aggiungo un autenticazione di base  a cui passo password e UserId, ricavata da classe UserConfig
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(config.getUserId(), config.getPassword()));


        Utenti utente = null;
        try {
            utente = restTemplate.getForObject(url, Utenti.class);
        } catch (Exception e) {
            String ErrMsg = String.format("Connessione al servizio di autenticazione non riuscita!!");
            log.warning(errMsg);
        }
        return utente;
    }
}
