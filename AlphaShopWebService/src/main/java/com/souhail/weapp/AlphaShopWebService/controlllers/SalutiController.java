package com.souhail.weapp.AlphaShopWebService.controlllers;

import org.springframework.web.bind.annotation.*;

@RestController // identifica come bean come rest che ci fornisce dATI JSON O XML
@RequestMapping("/api/saluti")// serve per specificare end poin app, convenzione api/versione/metodo es api/v1/saluti
@CrossOrigin("http://localhost:4200")
// qui viene indicato l'indirizzo del server client abilitato ad accedere a questi servizi

public class SalutiController {

    @GetMapping() // se non metto nulla come path mi gestisce richiesta del request mapping
    public String getSaluti() {

        return "\"Saluti, sono il primo servizio di merda che hai creato\"";// con l'escape restituisco la stringa dentro doppi apici cosi posso metterla nel json e non avere problemi
    }

    @GetMapping(value = "/{nome}") // se non metto nulla come path mi gestisce richiesta del request mapping
    public String getSaluti2(@PathVariable("nome") String Nome) {
        String message = String.format("Saluti, %s sono il tuo primo servizio di merda che hai creato", Nome);
        return message;
    }

}
