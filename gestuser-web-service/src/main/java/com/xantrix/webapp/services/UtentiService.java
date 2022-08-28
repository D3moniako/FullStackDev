package com.xantrix.webapp.services;

import com.xantrix.webapp.models.Utenti;

import java.util.List;

// TODO :FATTO!

public interface UtentiService {
    public List<Utenti> selTutti();

    public Utenti selUser(String userId);

    public void save(Utenti utente);


    public void delete(Utenti utente);
}
