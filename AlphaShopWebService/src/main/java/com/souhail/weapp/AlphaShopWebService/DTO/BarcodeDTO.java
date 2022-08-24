package com.souhail.weapp.AlphaShopWebService.DTO;

import lombok.Data;

@Data
public class BarcodeDTO {
    private String barcode;
    private String idTipoArt;// rispetto al model ci manca un campo ma con il mapper creato non ho problemi
}
