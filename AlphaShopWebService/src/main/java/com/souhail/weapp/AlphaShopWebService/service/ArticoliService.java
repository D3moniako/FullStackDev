package com.souhail.weapp.AlphaShopWebService.service;

import com.souhail.weapp.AlphaShopWebService.DTO.ArticoliDTO;
import com.souhail.weapp.AlphaShopWebService.entity.Articoli;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ArticoliService  {
// questo è l'elenco dei metodi che mi servono con ritorno parametri d'ingresso e e nome metodo


    public Iterable<Articoli> SelTutti();

    public List<ArticoliDTO> SelByDescrizione(String descrizione);

    public List<Articoli> SelByDescrizione(String descrizione, Pageable pageable);
    // nota ci sono più metodi di quelli dello stato di persistenza, perchè con la crudrepo ne ho di più

//    public Articoli SelByCodArt(String codArt);
//    ora uso i dto
  public ArticoliDTO SelByCodArt(String codArt);

    public Articoli SelByCodArt2(String codArt);

    public ArticoliDTO SelByBarcode(String barcode);

    public void DelArticolo(Articoli articolo);
    public void InsArticolo(Articoli articolo);

}

