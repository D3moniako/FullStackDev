package com.souhail.weapp.AlphaShopWebService.security;

import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.util.Assert;

import javax.security.auth.message.AuthException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// questa classe ci restituisce uno specifico messaggio di errore nel caso password e/o nome siano errati
// avendo specificato il reame qui e su Security preserveremo i messaggi di errore standard nello spring security
@Log
public class AuthEntryPoint extends BasicAuthenticationEntryPoint {
    private static String REALM="REAME";
    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException  {
        String errMsg="Userid e/o Password non  corretti!";


        log.warning("Errore sicurezza:"+authException.getMessage());

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);// risposta che scatta nel caso di password o nome errato
        response.addHeader("WWW-Authenticate","Basic-realm"+getRealmName()+"");// risposta nel caso si cerchi di passare in url dove non si è autorizzati

        PrintWriter writer=response.getWriter();
        writer.println(errMsg);

    }
     @Override
     public void afterPropertiesSet() {
        setRealmName(REALM); // sto specifando proprietà quali nome reame che è nella stringa, deve essere identico a quello nella security nel configuration
        super.afterPropertiesSet();
     }

}
