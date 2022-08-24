package com.souhail.weapp.AlphaShopWebService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()  // identifica la classe come classe di test di aspring boot
@ContextConfiguration(classes = Application.class) // inserisco la classe dell'entry point dell'app, cioè dove sta il main
public class TestSalutiController {

    // mockmvc è una classe delle servlet vedi import
    private MockMvc mvc;//  creo un mock del model view controller creo questa variabile che simula le specifiche mvc dell'app

    @Autowired
    private WebApplicationContext wac; // creo un wac che mi rappresenta l'ambiente di testing

    @BeforeEach // prima di ciascun junit test verrà eseguito questo metodo setup che mi crea l'ambiente per avviare il test
    public void setup(){

             mvc = MockMvcBuilders // ora chiamo la classe costruttore di mock che ha un metodo che mi setta il
            .webAppContextSetup(wac) // che si prende l'ambiente poi mi costruisce un mock in base all'ambiente ,
            .build(); //questo mock lo metto nel mock vuoto che mi ero creato precedentemente
    }

@Test
public void testGetSaluti () throws Exception{ // creo un metodo per gestire

     mvc.perform(get("/api/saluti") // ora prendo i metodi dell'web application context tramite il suo oggetto
                    .contentType(MediaType.APPLICATION_JSON))// questi metodi effettuano delle verifiche sul mvc creato
                    .andExpect(status().isOk())  // uno ci aspettiamo che il tipo di contenuto sia un  application json(una stringa),
                    .andExpect( jsonPath("$")// per il client è un json ovviamente, dopo che lo stato del codice è ok
                    .value("Saluti, sono il primo servizio di merda che hai creato"))// ci aspettiamo che come valore ci ritotni questa strinag qui
                    .andDo(print());// ed infine visualizzeremo il risultato nellla console

    }

    @Test
    public void testGetSaluti2 () throws Exception{

        mvc.perform(get("/api/saluti/Souhail")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$")
                        .value("Saluti, Souhail sono il tuo primo servizio di merda che hai creato"))
                .andDo(print());

    }
}


