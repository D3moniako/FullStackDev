package com.souhail.weapp.AlphaShopWebService.tests.ControllerTests;

import com.souhail.weapp.AlphaShopWebService.Application;
import com.souhail.weapp.AlphaShopWebService.entity.Articoli;
import com.souhail.weapp.AlphaShopWebService.repository.ArticoliRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = Application.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
// si occupa di tutti i metodi per l'inserimento di articoli
public class InsertArtTest {
    // NOTA SE IL METODO PERFORMER HA UN WARNING VUOL DIRE CHE IL MOCK USATO è INCONGRUENTE!!!
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac; // è un oggetto che rappresenta il conteato in cui viene generato il mock

    @Autowired
    private ArticoliRepository articoliRepository;// faccio autowired perchè lo userò per fare verifica dei dati che sono stati inseriti e modificati

    @BeforeEach// creo un mockmvc tramite la classe moclmvcbuilder con i metodi weAppCONTEXTsETUP E BUILD
    public void setup()/* throws JSONException, IOException*/ {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
    }

    // rappresenta il codice di test basta prendere un json da postman qualsiasi e aggiungerci gli escape di riga +/n+
    // NOTA ho bisogno solo degli elementi chiave
    private final String JsonData =
            "{\r\n"
                    + "    \"codArt\": \"123Test\",\r\n"
                    + "    \"descrizione\": \"ARTICOLO UNIT TEST INSERIMENTO\",\r\n"
                    + "    \"um\": \"PZ\",\r\n"
                    + "    \"codStat\": \"TESTART\",\r\n"
                    + "    \"pzCart\": 6,\r\n"
                    + "    \"pesoNetto\": 1.75,\r\n"
                    + "    \"idStatoArt\": \"1\",\r\n"
                    + "    \"dataCreaz\": \"2019-05-14\",\r\n"
                    + "    \"barcode\": [\r\n"
                    + "        {\r\n"
                    + "            \"barcode\": \"12345678\",\r\n"
                    + "            \"idTipoArt\": \"CP\"\r\n"
                    + "        }\r\n"
                    + "    ],\r\n"
                    + "    \"ingredienti\": {\r\n"
                    + "		\"codArt\" : \"123Test\",\r\n"
                    + "		\"info\" : \"TEST INGREDIENTI\"\r\n"
                    + "	},\r\n"
                    + "    \"iva\": {\r\n"
                    + "        \"idIva\": 22\r\n"
                    + "    },\r\n"
                    + "    \"famAssort\": {\r\n"
                    + "        \"id\": 1\r\n"
                    + "    }\r\n"
                    + "}";

    @Test
    @Order(1)
    public void testInsArticolo() throws Exception {
        //accetta post perchè inserisco roba, ci aspettiamo un json,passerò un jsonData. e verrà accettato sempre un apllication.json
        mockMvc.perform(MockMvcRequestBuilders.post("/api/articoli/inserisci")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonData)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        // ora che l'ho inserito verifico se è stato inserito come volevo
        assertThat(articoliRepository.findByCodArt("123Test")) // verifichiamo che la classe che otteniamo dal metodo findbycodart abbia prop descrizione=" articoli unit ...."
                .extracting(Articoli::getDescrizione)
                .isEqualTo("ARTICOLO UNIT TEST INSERIMENTO");
    }

    @Test
    @Order(2)
    public void testErrInsArticolo() throws Exception { // errore che parte nel caso si inserisca lo stesso articolo
        mockMvc.perform(MockMvcRequestBuilders.post("/api/articoli/inserisci")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonData)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable()) // devo ottenere uno stato di errore non accettabile
                .andExpect(jsonPath("$.codice").value(406)) // parte come al solito il codice di errore
                .andExpect(jsonPath("$.messaggio").value("Articolo 123Test presente in anagrafica! Impossibile utilizzare il metodo POST"))// e il messaggio che lo spieghi
                .andDo(print());
    }


    // IN questo JSON non c'è un elemento cioè la descrizione è vuota

    String ErrJsonData =
            "{\r\n"
                    + "    \"codArt\": \"123Test\",\r\n"
                    + "    \"descrizione\": \"\",\r\n" //<-- Descrizione Assente
                    + "    \"um\": \"PZ\",\r\n"
                    + "    \"codStat\": \"TESTART\",\r\n"
                    + "    \"pzCart\": 6,\r\n"
                    + "    \"pesoNetto\": 1.75,\r\n"
                    + "    \"idStatoArt\": \"1\",\r\n"
                    + "    \"dataCreaz\": \"2019-05-14\",\r\n"
                    + "    \"barcode\": [\r\n"
                    + "        {\r\n"
                    + "            \"barcode\": \"12345678\",\r\n"
                    + "            \"idTipoArt\": \"CP\"\r\n"
                    + "        }\r\n"
                    + "    ],\r\n"
                    + "    \"ingredienti\": {\r\n"
                    + "		\"codArt\" : \"123Test\",\r\n"
                    + "		\"info\" : \"TEST INGREDIENTI\"\r\n"
                    + "	},\r\n"
                    + "    \"iva\": {\r\n"
                    + "        \"idIva\": 22\r\n"
                    + "    },\r\n"
                    + "    \"famAssort\": {\r\n"
                    + "        \"id\": 1\r\n"
                    + "    }\r\n"
                    + "}";

    @Test
    @Order(3)     // Binding validation
    public void testErrInsArticolo2() throws Exception { // controlla errore nel caso un campo non sia riempito in questo caso specifico la descrizione, dovrei verificare tutto
        mockMvc.perform(MockMvcRequestBuilders.post("/api/articoli/inserisci")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ErrJsonData)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codice").value(400))
                .andExpect(jsonPath("$.messaggio").value("Il campo descrizione deve avere un numero di caratteri compreso tra 1 e 80"))
                .andDo(print());
    }

    private final String JsonDataMod = // JSON PER LA MODIFICA
            "{\r\n"
                    + "    \"codArt\": \"123Test\",\r\n"
                    + "    \"descrizione\": \"ARTICOLO UNIT TEST MODIFICA\",\r\n"
                    + "    \"um\": \"PZ\",\r\n"
                    + "    \"codStat\": \"TESTART\",\r\n"
                    + "    \"pzCart\": 6,\r\n"
                    + "    \"pesoNetto\": 1.75,\r\n"
                    + "    \"idStatoArt\": \"1\",\r\n"
                    + "    \"dataCreaz\": \"2019-05-14\",\r\n"
                    + "    \"barcode\": [\r\n"
                    + "        {\r\n"
                    + "            \"barcode\": \"12345678\",\r\n"
                    + "            \"idTipoArt\": \"CP\"\r\n"
                    + "        }\r\n"
                    + "    ],\r\n"
                    + "    \"ingredienti\": {\r\n"
                    + "		\"codArt\" : \"123Test\",\r\n"
                    + "		\"info\" : \"TEST INGREDIENTI\"\r\n"
                    + "	},\r\n"
                    + "    \"iva\": {\r\n"
                    + "        \"idIva\": 22\r\n"
                    + "    },\r\n"
                    + "    \"famAssort\": {\r\n"
                    + "        \"id\": 1\r\n"
                    + "    }\r\n"
                    + "}";

    @Test
    @Order(4)
    public void testUpdArticolo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/articoli/modifica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonDataMod)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()) // come al solito mi aspetto un codice http is created
                .andDo(print());

        assertThat(articoliRepository.findByCodArt("123Test"))
                .extracting(Articoli::getDescrizione)
                .isEqualTo("ARTICOLO UNIT TEST MODIFICA");
    }

    @Test
    @Order(5)
    public void testDelArticolo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/articoli/elimina/123Test").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codice").value("200 OK"))
                .andExpect(jsonPath("$.messaggio").value("Eliminazione Articolo 123Test Eseguita con successo"))
                .andDo(print());
    }

}
