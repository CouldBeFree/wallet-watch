package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.service.WebAuth;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/webauth")
public class Webauth {

    private final WebAuth webauth;

    public Webauth(WebAuth webauth) {
        this.webauth = webauth;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PublicKeyCredentialCreationOptions> registerViaWebAuth() {
        return new ResponseEntity<>(webauth.register(), HttpStatus.CREATED);
    }
}
