package com.souhail.weapp.AlphaShopWebService.controlllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.souhail.weapp.AlphaShopWebService.DTO.ArticoliDTO;
/*import com.souhail.weapp.AlphaShopWebService.entity.Articoli;
import com.souhail.weapp.AlphaShopWebService.entity.Barcode;*/
import com.souhail.weapp.AlphaShopWebService.entity.Articoli;
import com.souhail.weapp.AlphaShopWebService.entity.InfoMsg;
import com.souhail.weapp.AlphaShopWebService.exception.BindingException;
import com.souhail.weapp.AlphaShopWebService.exception.DuplicateException;
import com.souhail.weapp.AlphaShopWebService.exception.NotFoundException;
import com.souhail.weapp.AlphaShopWebService.service.ArticoliService;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.catalina.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static org.hibernate.internal.CoreLogging.logger;

// classe creta sulla base degli junit
@RestController
@RequestMapping("api/articoli")
@Log // permette uso dei log senza instanziare classe
@CrossOrigin("http://localhost:4200")
public class ArticoliController {

    private static final Logger logger = LoggerFactory.getLogger(ArticoliController.class);
    @Autowired
    private ArticoliService articoliService;
  /*  @Autowired
    private BarcodeService barcodeService;*/

    @Autowired
    private ResourceBundleMessageSource errMessage; // mi serve per poter richiamare il file di messagi d'errore

    // la notazione getmapping identifica com metodo http di tipo get , con valore path  con dentro una variabile presa
    @GetMapping(value = "/cerca/barcode/{ean}", produces = "application/json")
    // il metodo produce dati in formato json
    public ResponseEntity<ArticoliDTO> listArtByEan(@PathVariable("ean") String Barcode)
            throws NotFoundException {

        log.info("****** Otteniamo l'articolo con barcode " + Barcode + " *******");
     /*   Articoli articoli;
         vecchio metodo con query native
        Barcode ean = barcodeService.SelByBarcode(barcode);
*/
        ArticoliDTO articoliDTO = articoliService.SelByBarcode(Barcode);

        /*if (ean == null) {
/// nota con jUnitTES NON FUNZIONA QUESTO METODO
            // poichè se vado nel Failur Tracee si aspettava il valore PZ senza spazzi bianchi
            String errMsg = String.format("Il barcode %s non è stato trovato!", barcode);
            logger.warn(errMsg);// devo creare una gestione specifica degli errori in un package
            //return new ResponseEntity<Articoli>(HttpStatus.NOT_FOUND);
            throw new NotFoundException(errMsg);

        }*/
        if (articoliDTO == null) {
            String errMsg = String.format("Il barcode %s non è stato trovato!", Barcode);
            log.warning(errMsg);// devo creare una gestione specifica degli errori in un package
            //return new ResponseEntity<Articoli>(HttpStatus.NOT_FOUND);
            throw new NotFoundException(errMsg);
        }
        return new ResponseEntity<ArticoliDTO>(articoliDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/cerca/codice/{codart}", produces = "application/json")
    public ResponseEntity<ArticoliDTO> listArtByCodArt(@PathVariable("codart") String codart)
            throws NotFoundException {

        log.info("****** Otteniamo l'articolo con codice " + codart + " *******");

        ArticoliDTO articoli = articoliService.SelByCodArt(codart);

        if (articoli == null) {
            String errMsg = String.format("L'articolo con codice %s non è stato trovato!", codart);
            log.warning(errMsg);
            throw new NotFoundException(errMsg);
        }
        return new ResponseEntity<>(articoli, HttpStatus.OK);

    }

    @GetMapping(value = "/cerca/descrizione/{filter}", produces = "application/json")
    public ResponseEntity<List<ArticoliDTO>> listArtByDesc(@PathVariable("filter") String Filter)
            throws NotFoundException {
        log.info("****** Otteniamo gli articoli con Descrizione" + Filter + " *******");
        List<ArticoliDTO> articoliList = articoliService.SelByDescrizione(Filter.toUpperCase() + "%");// si tratta di query di tipo LIKE perciò metto %

        if (articoliList.isEmpty()) {// trattandosi di una collezione non posso verificare che sia null ma che sia empity
            String errMsg = String.format("Non è stato trovato alcun articolo avente descrizione %s", Filter);
            log.warning(errMsg);
            throw new NotFoundException(errMsg);
        }
        return new ResponseEntity<List<ArticoliDTO>>(articoliList, HttpStatus.OK);// altrimenti ho articoli e status ok
    }


    /////////////////////////////////////////////////////////////////////////////

    // con RequestBody indico che accetta un json che verrà trasformato in un oggetto java
    // i dati passati non solo devono essere formato json ma devono essere anche validi(passare bind validation) con @ valid

    // come 2 parametro al metodo passo bindinResult in base ad esso visualizzo o no l'errore
    // quindi nel form di inserimento degli articoli dovrò passare un oggetto con i vari campi e il bindingresult che mi valida i campi
    @SneakyThrows // non ho bisogno di scrivere throws e  tutte le eccezioni da lanciare ma me le trova lui
    @PostMapping(value = "/inserisci")
    public ResponseEntity<InfoMsg> createArt(@Valid @RequestBody Articoli articolo, BindingResult bindingResult) {


        logger.info("Salviamo l'articolo con codice " + articolo.getCodArt());
        // 1 CONTROLLO  nel caso i dati passati non siano validi
        // parte l'exception
        if (bindingResult.hasErrors()) {
            String msgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
            logger.warn(msgErr);
            throw new BindingException(msgErr);
        }

        // 2 Controllo che il CodArt è già presente se DISABILITATO QUESTO METODO PER INSERT TRAMITE POST PERMETTEREBBE ANCHE DI FARE L'UPDATE
        ArticoliDTO checkArt = articoliService.SelByCodArt(articolo.getCodArt());
        if (checkArt != null) {
            String msgErr = String.format("Articolo %s presente in anagrafica!"
                    + "impossibile utilizzare il metodo POST", articolo.getCodArt());
            logger.warn(msgErr);

            throw new DuplicateException(msgErr);

        }

        articoliService.InsArticolo(articolo);

        return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), "Inserimento articolo eseguito con successo"), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/modifica", method = RequestMethod.PUT)
    // posso usare il requestmapping con qualsiasi chiamata l'importante che dopo specifico il metodo che voglio usare
    public ResponseEntity<InfoMsg> updateArt(@Valid @RequestBody Articoli articolo, BindingResult bindingResult) throws BindingException, NotFoundException, DuplicateException {
        // simile al metodo insert
        logger.info("Aggiorniamo l'articolo con codice " + articolo.getCodArt());

        if (bindingResult.hasErrors()) {
            String msgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
            logger.warn(msgErr);
            throw new BindingException(msgErr);
        }


        ArticoliDTO checkArt = articoliService.SelByCodArt(articolo.getCodArt());
        if (checkArt != null) {
            String MsgErr = String.format("Articolo %s presente in anagrafica! "
                    + "Impossibile utilizzare il metodo POST", articolo.getCodArt());

            log.warning(MsgErr);

            throw new DuplicateException(MsgErr);
        }

        articoliService.InsArticolo(articolo);

        return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), "Modifica articolo eseguita con successo"), HttpStatus.CREATED);
    }

    @RequestMapping(value = "elimina/{codart}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> deleteArt(@PathVariable("codart") String CodArt) throws NotFoundException { // il ? di domanda nella response enity non specifica che oggeto ha dentro
        logger.info("Eliminiamo l'articolo con codice " + CodArt);

        Articoli checkArt = articoliService.SelByCodArt2(CodArt);

        if (checkArt == null) {
            String msgErr = String.format("Articolo %s non presente in anagrafica!"
                    + "impossibile utilizzare il metodo DELETE", CodArt);
            logger.warn(msgErr);

            throw new NotFoundException(msgErr);// metodo uguale tranne per questa exception
        }
        articoliService.DelArticolo(checkArt);
        //  sistema per visualizzare il risultato
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();// è una mappa chiave valore in cui metto come chiave il code e il messagiio come valore lo statushttp in stringa

        responseNode.put("code", HttpStatus.OK.toString());// il to string mi serve perchè httpStatus è un json
        responseNode.put("messaggio", "Eliminazione Articoli" + CodArt + "Eseguita con successo");// il to string mi serve perchè httpStatus è un json
        // eliminazione articolo eseguita con successo è il valore

        return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.OK);
        // ho aggiunto il response node in modo da restituire un valore specifico
    }
}
