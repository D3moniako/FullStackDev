package com.souhail.weapp.AlphaShopWebService.exception;
// classe da gestire nell'handler
public class DuplicateException extends  Exception{

     static  final  long serialVersionUID= -6989938346468200836L;


    // metodi uguali alle altre exception cambia solo nome e il seriale
    private  String messaggio;

    public DuplicateException( ){
        super();
    }

    public DuplicateException(String Messaggio) {
        super(Messaggio);
        this.messaggio= Messaggio;

    }
    public String getMessaggio () {

        return  messaggio;
    }

    public  void setMessaggio(String messaggio){
        this.messaggio=messaggio;
    }



}
