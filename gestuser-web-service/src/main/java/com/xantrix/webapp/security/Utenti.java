package com.xantrix.webapp.security;

import lombok.Data;

import java.util.List;

// questa classe è la copia del model utenti nell'altro microservizio ma non ci sono notazioni hibernate per creare le tabelle
@Data
public class Utenti {
    private String id;
    private String userId;
    private String password;
    private String attivo;

    private List<String> ruoli;
}
