package com.souhail.weapp.AlphaShopWebService.service;

import com.souhail.weapp.AlphaShopWebService.DTO.ArticoliDTO;
import com.souhail.weapp.AlphaShopWebService.entity.Articoli;
import com.souhail.weapp.AlphaShopWebService.repository.ArticoliRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional

public class ArticoliServiceImpl implements ArticoliService {
    @Autowired
    ArticoliRepository articoliRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Iterable<Articoli> SelTutti() {
        return articoliRepository.findAll();
    }

    @Override
    public List<ArticoliDTO> SelByDescrizione(String descrizione) {
        String filter = "" + descrizione.toUpperCase() + "";
        List<Articoli> articoliList = articoliRepository.SelByDescrizioneLike(filter);
        //ora dovendo trattare una lista di oggetti con gli spazi in statoart e unità misura
        // che non combacia con il mock
        // devo gestire tutto con un iterazione, nel caso forEach e lambda exprection
        // PROBLEMA SE FACCIO COSI DOVREI GESTIRE ELEMENTO PER ELEMENTO MANUALMENTE E NON è FATTIBILE
        /*articoliList.forEach(e->e.setIdStatArt(e.getIdStatArt().trim()));

        //per ogni elemento articolo nella lista di articoli , setto id prendendoòp da idStatArt a cui levo spazi
        articoliList.forEach(e->e.setIdStatArt(e.getIdStatArt().trim()));
        // idem per unità misura
        articoliList.forEach(e->e.setDescrizione(e.getDescrizione().trim()));*/

        // a questo punto metto i valori nuovi in un nuovo dto con LE STREAM

        // faccio stream di lista articoli(chiamato articoliList) a cui applico una map, una funzione lambda ad ogni elemento contenuto in articoli,
        // la funzione in se non è altro che un modelMapper che prende in ingresso class di partenza e tipo cllasse di arrivo
        // in modo da convertire elemento per elemento
        List<ArticoliDTO> retVal = articoliList.stream().map(source -> modelMapper.map(source, ArticoliDTO.class))
                .collect(Collectors.toList());
        return retVal;
    }

    @Override
    public List<Articoli> SelByDescrizione(String descrizione, Pageable pageable) {
        return articoliRepository.findByDescrizioneLike(descrizione, pageable);
    }

//    @Override
//    public Articoli SelByCodArt(String codArt) {
//
//        return  articoliRepository.findByCodart(codArt);
//    }


    /// metodo per convertire direttamente da entity a modell
    private ArticoliDTO ConvertToDTO(Articoli articoli) {

        ArticoliDTO articoliDTO = null;
        if (articoli != null) {

            articoliDTO = modelMapper.map(articoli, ArticoliDTO.class);
            articoliDTO.setUm(articoliDTO.getUm().trim());// metto nel service la conversione del Um senza spazi tramite metodo trim()
            articoliDTO.setIdStatoArt(articoliDTO.getIdStatoArt().trim());// metto nel service la conversione del IdStatoArt senza spazi tramite metodo trim()
            articoliDTO.setDescrizione(articoliDTO.getDescrizione().trim());// idem per IdStatoArt
        }
        return articoliDTO;
    }

    @Override
    public ArticoliDTO SelByBarcode(String barcode) {
        Articoli articoli = articoliRepository.SelByEan(barcode);

        return this.ConvertToDTO(articoli);
    }


    @Override// ora uso i dto quindi faccio restituire il dto
    public ArticoliDTO SelByCodArt(String codArt) {
       /* Articoli articoli=articoliRepository.findByCodart(codArt);// i dati li ricevo dal dao come model poi converto in DTO
        ArticoliDTO articoliDTO = modelMapper.map(articoli, ArticoliDTO.class);



        articoliDTO.setUm(articoliDTO.getUm().trim());// metto nel service la conversione del Um senza spazi tramite metodo trim()
        articoliDTO.setIdStatoArt(articoliDTO.getIdStatoArt().trim());// idem per IdStatoArt

        return  articoliDTO;*/
        Articoli articoli = articoliRepository.findByCodArt(codArt);

        return this.ConvertToDTO(articoli);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    @Transactional// i metodi che servono per cancellazione o inserimento sono di transazione
    public void DelArticolo(Articoli articolo) {
        articoliRepository.delete(articolo);
    }

    @Override
    @Transactional
    public void InsArticolo(Articoli articolo) {
        articolo.setDescrizione(articolo.getDescrizione().toUpperCase());
        articoliRepository.save(articolo);
    }


    @Override
    public Articoli SelByCodArt2(String codArt) {

        return articoliRepository.findByCodArt(codArt);
    }

}
