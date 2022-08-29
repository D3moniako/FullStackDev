package com.xantrix.webapp.services;

import com.xantrix.webapp.models.Utenti;
import com.xantrix.webapp.repository.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)// nota non funziona se uso import diversi da springframework.annotation
public class UtentiServiceImpl implements UtentiService {

    @Autowired
    UtentiRepository utentiRepository;

    @Override
    public List<Utenti> selTutti() {
        return utentiRepository.findAll();
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public Utenti selUser(String userId) {
        return utentiRepository.findByUserId(userId);
    }

    /**
     * @param utente
     */
    @Override
    public void save(Utenti utente) {
        utentiRepository.save(utente);
    }

    /**
     * @param utente
     */
    @Override
    public void delete(Utenti utente) {
        utentiRepository.delete(utente);
    }
}
