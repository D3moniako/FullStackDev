package com.souhail.weapp.AlphaShopWebService.DTO;

import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class ArticoliDTO {
    private String codArt;
    private String descrizione;
    private String um;
    private String codStat;
    private Integer pzCart;
    private double pesoNetto;
    private String idStatoArt;
    private Date dataCreazione;// se modifico il dto rispetto al model es da data creaz a data creazione ho problemi con modell mapper

    /*
        private Date dateCreaz;// se modifico il dto rispetto al model es da data creaz a data creazione ho problemi con modell mapper
    */
    private double prezzo = 0;

    /*private Set<Barcode> barcode = new HashSet<>();// DTO FA RIFERIMENTO A UN MODE NON VA BENE
    private Ingredienti ingredienti;//DTO rif model
    private FamAssort famAssort;//DETO rif model
    private Iva iva;*/
    private Set<BarcodeDTO> barcode = new HashSet<>();
    private IngredientiDTO ingredienti;//DTO rif DTO
    private CategoriaDTO famAssort;//DTO rif DTO
    private IvaDTO iva;

}
