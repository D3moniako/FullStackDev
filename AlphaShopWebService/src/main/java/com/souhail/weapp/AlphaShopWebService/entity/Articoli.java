package com.souhail.weapp.AlphaShopWebService.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * sta roba fa veramente schifo
 *
 * @author Souhail Lamzouri
 **/
@Entity
@Table(name = "ARTICOLI")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Articoli implements Serializable {
    public static final long serialVersionUID = -3044756712209141022L;

    @Id
    // Validation è la bind presente nel controller tramite annotation @Validation,
    // se il campo scelto nel caso SIZE contiente .Validattion vuol dire che i dati in ingresso devono rispecchiare quel campo
    // quindi avere quel campo iìcon quella size specifica
    // se il campo inserito non soddisfa la specifica parte un messaggio facendo riferimento a delle variabili, in un file con i messaggi
    @Column(name = "CODART")

    @Size(min = 5, max = 20, message = "{Size.Articoli.codArt.Validation}")
//    @NotNull(message="{NotNull.Articoli.codArt.Validation}")
    private String codArt;


    @Column(name = "DESCRIZIONE")
    @Size(min = 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
//    @NotNull(message="{NotNull.Articoli.codArt.Validation}")
    private String descrizione;


    @Column(name = "UM")
    private String um;


    @Column(name = "CODSTAT")
    private String codStat;


    @Column(name = "PZCART")
    @Max(value = 99, message = "{Size.Articoli.pzCart.Validation}")
    private String pzCart;


    @Column(name = "PESONETTO")
    @Min(value = (long) 0.01, message = "{Size.Articoli.pzCart.Validation}")
    private double pesoNetto;


    @Column(name = "IDSTATOART")
    private String idStatoArt;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATACREAZIONE")
    private Date dataCreaz;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "articoli", orphanRemoval = true)
    // Molto importante il mappedby
    @JsonManagedReference
    // quando leggo i dati da db vengono convertiti in formato json, questa annotation serve per cio
    private Set<Barcode> barcode = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "articolo", orphanRemoval = true)
    // il mappedby fa riferimento al nome della variabile associata all'altra parte
    private Ingredienti ingredienti;
    @ManyToOne
    @JoinColumn(name = "IDIVA", referencedColumnName = "idIva")
    private Iva iva;

    @ManyToOne
    @JoinColumn(name = "IDFAMASS", referencedColumnName = "ID")
    private FamAssort famAssort;
}