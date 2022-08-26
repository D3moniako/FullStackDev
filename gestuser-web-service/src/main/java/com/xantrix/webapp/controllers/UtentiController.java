package com.xantrix.webapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xantrix.webapp.exception.BindingException;
import com.xantrix.webapp.exception.NotFoundException;
import com.xantrix.webapp.models.Utenti;
import com.xantrix.webapp.services.UtentiService;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping(value="/api/utenti")
@Log
public class UtentiController {
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private  ResourceBundleMessageSource errMessage=new ResourceBundleMessageSource();

    @GetMapping(value="/cerca/tutti")
        public List<Utenti> getAllUser(){
        log.info("Otteniamo tutti gli utenti");
        return utentiService.selTutti();
    }
    @SneakyThrows
    @GetMapping(value="/cerca/userId/{userId}")
    public Utenti getUtente(@PathVariable("userId") String userId){

        log.info("Otteniamo l'utente"+userId);
        Utenti utente=utentiService.selUser(userId);
        if(utente==null){
        String errMsg=String.format("L'utente %s non è autenticato", userId);
        log.warning(errMsg);
        throw new NotFoundException(errMsg);
        }

        return utente;
    }
    ///////////////INSERIMENTO/MODIFICA UTENTE
    @GetMapping(value="cerca")
    //questo metodo non  solo inserisce un nuovo utente ma lo modifica anche la modifica
    @PostMapping(value="/inserisci")
    public ResponseEntity<InfoMSg> addNewUser
    (@Valid @RequestBody Utenti utente,BindingResult bindingResult) throws BindingException{

        Utenti checkUtente = utentiService.selUser(utente.getUserId());

        if (checkUtente != null)
        {
            utente.setId(checkUtente.getId());
            log.info("Modifica Utente");
        }
        else
        {
            log.info("Inserimento Nuovo Utente");
        }

        if (bindingResult.hasErrors())
        {
            String msgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());

            log.warning(msgErr);


            throw new BindingException(msgErr);
        }

        String encodedPassword = passwordEncoder.encode(utente.getPassword());
        utente.setPassword(encodedPassword);
        utentiService.save(utente);

        return new ResponseEntity<InfoMSg>(new InfoMSg(LocalDate.now(),
                String.format("Inserimento Utente %s Eseguita Con Successo", utente.getUserId())), HttpStatus.CREATED);
    }

    // ------------------- ELIMINAZIONE UTENTE ------------------------------------
    @DeleteMapping(value = "/elimina/{id}")
    @SneakyThrows
    public ResponseEntity<?> deleteUser(@PathVariable("id") String userId)
    {
        log.info("Eliminiamo l'utente con id " + userId);

        Utenti utente = utentiService.selUser(userId);

        if (utente == null)
        {
            String MsgErr = String.format("Utente %s non presente in anagrafica! ",userId);

            log.warning(MsgErr);

            throw new NotFoundException(MsgErr);
        }

        utentiService.delete(utente);

        HttpHeaders headers = new HttpHeaders();
        ObjectMapper mapper = new ObjectMapper();

        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectNode responseNode = mapper.createObjectNode();

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Eliminazione Utente " + userId + " Eseguita Con Successo");

        return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
    }
}
