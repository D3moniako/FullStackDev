package com.souhail.weapp.AlphaShopWebService.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/*diooooo
 * perchè non vuoi funzionare
 *
 */
@Entity
@Table(name = "BARCODE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Barcode implements Serializable {
    public static final long serialVersionUID = -6290032043939163105L;
    @Id
    @Column(name = "BARCODE")
    @Size(min = 8, max = 13, message = "{Size.Articoli.pzCart.Validation}")
    private String barcode;


    @Column(name = "IDTIPOART")
    private String idTipoArt;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    // fa parte di loombok , serve per non far andare a loop loombok per via dei metodi ,hashmapping
    @JoinColumn(name = "CODART", referencedColumnName = "codArt")
    @JsonBackReference
    // riferimento inverso al json dall'altra parte, in mancanza di esse , avrei problemi di serializzazione
    private Articoli articoli;

}

