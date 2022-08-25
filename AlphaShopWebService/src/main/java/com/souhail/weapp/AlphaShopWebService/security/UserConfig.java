package com.souhail.weapp.AlphaShopWebService.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component  // component è un componente di spring generico
@ConfigurationProperties("getuser") // ha come parametro il nome della matrice delle proprietà
@Data
public class UserConfig
{
private String srvUrl;
private String userId;
private String password;
}
