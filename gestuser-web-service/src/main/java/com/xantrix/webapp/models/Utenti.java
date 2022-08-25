package com.xantrix.webapp.models;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
//NOTA ORA CHE USO DB NON RELAZIONALI NON CHIAMO PIù ENTITA' MA MODELlI ,
@Document(collection = "utenti") // l'annotation Document è simile a entity ma per i documenti
@Data
public class Utenti
{
	@Id
	private String id; // id è la primary key gestita da mongo
	
	@Indexed(unique = true) // indice univoco non valore
	@Size(min = 5, max = 80, message = "{Size.Utenti.userId.Validation}")
	@NotNull(message = "{NotNull.Articoli.userId.Validation}")
	private String userId;
	
	@Size(min = 8, max = 80, message = "{Size.Utenti.password.Validation}")
	private String password;
	
	private String attivo = "Si";
	
	@NotNull(message = "{NotNull.Utenti.ruoli.Validation}")
	private List<String> ruoli; // i ruoli sono contenuti in una lista ma potevo usare un enumerate
	
}
