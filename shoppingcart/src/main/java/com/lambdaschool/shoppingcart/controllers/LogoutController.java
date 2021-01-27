package com.lambdaschool.shoppingcart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RestController
public class LogoutController {
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ConsumerTokenServices tokenServices;

    static final String CLIENT_ID = System.getenv("OAUTHCLIENTID");


    @GetMapping(value = "/logout")
    public ResponseEntity<?> userLogout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(CLIENT_ID, userName);

        if(tokens!=null) {
            for (OAuth2AccessToken token : tokens){
              tokenServices.revokeToken(token.getValue());
            }
        } else {
            System.out.println("Tokens are null");
        }


       return new ResponseEntity<>(HttpStatus.OK);

    }

}
