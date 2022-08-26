package com.xantrix.webapp.controllers;

import com.xantrix.webapp.models.Utenti;
import com.xantrix.webapp.services.UtentiService;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/api/utenti")
@Log
public class UtentiController {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ResourceBundleMessageSource errMessage;

    @GetMapping(value="/cerca/tutti")
    public List<Utenti> getAllUser(){
        log.info("Otteniamo tutti gli utenti");
        return utentiService.SelTutti;
    }
    @SneakyThrows
    @GetMapping(value="/cerca/userId/{userId}")
    public Utenti getUtente(){
        if(){


        }

        return utente;
    }
    ///////////////INSERIMENTO/MODIFICA UTENTE
    @GetMapping(value="cerca")
    //questo metodo non  solo inserisce un nuovo utente ma lo modifica anche la modifica
    @PostMapping(value="/inserisci")
    public ResponseEntity<InfoMSg> addNewUser
    (@Valid @RequestBody Utenti utente,BindingResult bindingResult){
    if(){

    }
    else(){

        }
    else if(){

        }


    }
//////////////////ELIMINAZIONE UTENTE /////////////////////////////
@DeleteMapping(value="/elimina/{id}")
@SneakyThrows
public ResponseEntity<?> deleteUser(@PathVariable("id") String userId){

        if(){

        }
}


}
