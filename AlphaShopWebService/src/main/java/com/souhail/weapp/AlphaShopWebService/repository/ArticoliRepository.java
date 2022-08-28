package com.souhail.weapp.AlphaShopWebService.repository;

import com.souhail.weapp.AlphaShopWebService.entity.Articoli;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

// TRAMITE QUERY NATIVE
@Repository
@EnableJpaRepositories
public interface ArticoliRepository extends PagingAndSortingRepository<Articoli, String> { // questa interfaccia repository mi permette di eseguire le operazioni di pagging and sorting
    @Query(value = "SELECT * FROM ARTICOLI WHERE DESCRIZIONE LIKE CONCAT('%',:desArt,'%')", nativeQuery = true)
        // uso la flag concat per concatenare le stringhe in sql cosi posso cercare una parola nella descrizione
    List<Articoli> SelByDescrizioneLike(@Param("desArt") String descrizione);


    // query JPA facile facile non dovendo fare join
    List<Articoli> findByDescrizioneLike(String descrizione, Pageable pageable);

    // query JPA facile facile non dovendo fare join
    Articoli findByCodArt(String codArt);

// QUERY TRAMITE JPQl senza flag nativeQuery = true per fare selezioni classi entity
// SENZA QUINDI DOVER CREARE I DUE SERVICE CREATI IN PRECEDENZA MA POTENDO USARE DIRETTAMENTE LA QUERY

    // SPIEGAZIONE QUERY JPQL
//Seleziono tutti gli elementi A da classe entity Articoli (alias a elementi di Articoli)che sono IN join  grazie alla colonna  barcode
    //all'entità contente come chiave primaria barcode ,
// infine dove da Barcode entity alias b prendo elemento con barcode=ean'


    // specificando come FILTRO il parametro ean quindi tutti gli articoli tra due tabelle con uno specifico barcode ean
//@Query(value = "SELECT a FROM Articoli a JOIN a.barcode b WHERE b.barcode IN (:ean) ")
    @Query(value = "SELECT a FROM Articoli a JOIN a.barcode b WHERE b.barcode IN (:ean) ")
    Articoli SelByEan(@Param("ean") String ean);// cerco articolo tramite ean che fa riferimento al parametro del nostro metodo anch'esso chiamato ean
    // questo metodo ci restituirà una classe
}

