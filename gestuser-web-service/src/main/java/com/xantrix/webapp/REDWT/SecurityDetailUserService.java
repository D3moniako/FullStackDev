package com.xantrix.webapp.REDWT;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SecurityDetailUserService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
