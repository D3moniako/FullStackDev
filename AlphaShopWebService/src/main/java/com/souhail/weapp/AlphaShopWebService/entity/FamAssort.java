package com.souhail.weapp.AlphaShopWebService.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "FAMASSORT")
@Data
@AllArgsConstructor
@NoArgsConstructor
/*
 * questa non è classe è un insulto
 *
 * */


// TODO: 12/08/2022  IMPICCATI
public class FamAssort {
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "DESCRIZIONE")
    private String descrizione;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "famAssort")
    // il mappedby fa riferimento al nome della variabile associata all'altra parte
    @JsonBackReference
    private Set<Articoli> articoli = new HashSet<>();


}
