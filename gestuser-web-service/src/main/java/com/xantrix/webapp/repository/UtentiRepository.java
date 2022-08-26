package com.xantrix.webapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.xantrix.webapp.models.Utenti;
 // estendo MongoRepository come nel caso nel caso in cui estendevo jpaRepository o pagingsortingrepository
// TODO FATTO!
public interface UtentiRepository extends MongoRepository<Utenti, String> // con chiave l'utente oggetto e valore tipo utente in stringa
{
	public Utenti findByUserId(String UserId);
}

