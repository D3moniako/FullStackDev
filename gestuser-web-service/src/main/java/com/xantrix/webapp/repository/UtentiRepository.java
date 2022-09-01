package com.xantrix.webapp.repository;

import com.xantrix.webapp.models.Utenti;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

// estendo MongoRepository come nel caso nel caso in cui estendevo jpaRepository o pagingsortingrepository
// TODO FATTO!
public interface UtentiRepository extends MongoRepository<Utenti, String> // con chiave l'utente oggetto e valore tipo utente in stringa
{
    public Optional<Utenti> findByUserId(String UserId);
}

