package com.xantrix.webapp.REDWT;


import com.xantrix.webapp.models.Utenti;
import com.xantrix.webapp.repository.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityDetailUserServiceImpl implements UserDetailsService {

    @Autowired
    UtentiRepository utentiRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Utenti utenti = utentiRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userId));

        return SecurityUserDetail.build(utenti);

    }

}
