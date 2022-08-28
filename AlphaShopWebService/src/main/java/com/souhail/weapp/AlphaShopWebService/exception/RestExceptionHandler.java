package com.souhail.weapp.AlphaShopWebService.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


// ControllerAdvice specializzazione del Component  e del controller per la gestione delle exeption centralizzata
@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ErrorResponse> exceptionNotFoundHandler(Exception ex) {

        // creo nuova istanza di risposta dell'errore dalla classe ErrorResponse contenitore
        ErrorResponse errore = new ErrorResponse();

        // in cui setto(la riempo ) uno il codice due il messaggio
        errore.setCodice(HttpStatus.NOT_FOUND.value());
        //messaggio restituito dal metodo della classe del notfoundException
        errore.setMessaggio(ex.getMessage());
        // come ritorno avrò l'oggetto ResponseEntity che accetta l'oggetto errore contente messaggio errore
        // e valore del codice , più httpstatus contente stato errore e http headers
        return new ResponseEntity<>(errore, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    //GESTIONE ERRORI BINDING NELL'INSERIMENTO
    @ExceptionHandler(BindingException.class)
    public final ResponseEntity<ErrorResponse> exceptionBindingHandler(Exception ex) {

        ErrorResponse errore = new ErrorResponse();


        errore.setCodice(HttpStatus.BAD_REQUEST.value());
        errore.setMessaggio(ex.getMessage());
        return new ResponseEntity<>(errore, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /// GESTIONE ERRORE NEL CASO DI ARTICOLI INSERITI CON CODART DUPLICATO
    @ExceptionHandler(DuplicateException.class)
    public final ResponseEntity<ErrorResponse> exceptionDuplicateHandler(Exception ex) {

        ErrorResponse errore = new ErrorResponse();


        errore.setCodice(HttpStatus.NOT_ACCEPTABLE.value());
        errore.setMessaggio(ex.getMessage());
        return new ResponseEntity<>(errore, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

}
