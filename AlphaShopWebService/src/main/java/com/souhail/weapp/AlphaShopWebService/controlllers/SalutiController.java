package com.souhail.weapp.controlllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // identifica come bean come rest che ci fornisce dATI JSON O XML
@RequestMapping ("/api/saluti")// serve per specificare end poin app, convenzione api/versione/metodo es api/v1/saluti
public class SalutiController {

    @GetMapping("/test") // se non metto nulla come path mi gestisce richiesta del request mapping
    public String getSaluti(){

        return "Saluti, solo il primo servizio di merda che hai creato";
    }

}
