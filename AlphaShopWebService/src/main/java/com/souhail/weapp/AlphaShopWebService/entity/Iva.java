package com.souhail.weapp.AlphaShopWebService.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "IVA")
@AllArgsConstructor
@NoArgsConstructor
public class Iva {
    @Id
    @Column(name = "IDIVA")
    private int idIva;

    @Column(name = "DESCRIZIONE")
    private String descrizione;

    @Column(name = "ALIQUOTA")
    private int aliquota;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "iva")
    @JsonBackReference
    private
    Set<Articoli> articoli = new HashSet<>();

}
