package com.souhail.weapp.AlphaShopWebService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="INGREDIENTI")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Ingredienti implements Serializable {


    public static final long serialVersionUID = -352499447670116376L;
    @Id
    @Column(name="CODART")
    private String codArt;

    @Column(name="INFO")
    private String info;

    @OneToOne
    @PrimaryKeyJoinColumn// il punto di intersezione si basa su primarykey senza doverlo specificare
    @JsonIgnore // SIMILE ALLA JSONBACKREFERENCE, riferimento inverso al json dall'altra parte, in mancanza di esse , avrei problemi di serializzazione
    private Articoli articolo;

}
