package com.souhail.weapp.AlphaShopWebService.exception;

public class NotFoundException extends  Exception {

    private static final long serialVersionUID = 461208358316620334L;// DEVO FARLO AUTOGENERARE MA NON SO  COME !!!
    private String messaggio ="Elemento Ricercato Non Trovato";

    public NotFoundException(String messaggio){
        super(messaggio);
        this.messaggio= messaggio;
    }

    public String getMessaggio() {
        return messaggio;
    }

   public void setMessaggio( String messaggio){
    this.messaggio= messaggio;
   }
}
