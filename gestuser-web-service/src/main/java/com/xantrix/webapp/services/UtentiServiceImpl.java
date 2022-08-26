package com.xantrix.webapp.services;

import com.xantrix.webapp.models.Utenti;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional(readOnly= true)// nota non funziona se uso import diversi da springframework.annotation
public class UtentiServiceImpl implements UtentiService{



    @Override
    public List<Utenti> selTutti() {
        return null;
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public Utenti selUser(String userId) {
        return null;
    }

    /**
     * @param utente
     */
    @Override
    public void save(Utenti utente) {

    }

    /**
     * @param utente
     */
    @Override
    public void delete(Utenti utente) {

    }
}
