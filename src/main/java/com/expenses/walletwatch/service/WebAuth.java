package com.expenses.walletwatch.service;

import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartRegistrationOptions;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import com.yubico.webauthn.data.RelyingPartyIdentity;
import com.yubico.webauthn.data.UserIdentity;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class WebAuth {

    private final RelyingParty rp;

    public WebAuth() {
        RelyingPartyIdentity rpIdentity = RelyingPartyIdentity.builder()
                .id("https://walletwatch-app-frontend-6c4c5def6018.herokuapp.com")  // Set this to a parent domain that covers all subdomains
                // where users' credentials should be valid
                .name("Walletwatch")
                .build();

        this.rp = RelyingParty.builder()
                .identity(rpIdentity)
                .credentialRepository(new com.expenses.walletwatch.dao.WebAuth())
//                .appId("https://acme.example.com")
                .build();
    }

    public PublicKeyCredentialCreationOptions register() {
        Random random = new Random();
        byte[] userHandle = new byte[64];
        random.nextBytes(userHandle);
        PublicKeyCredentialCreationOptions request = rp.startRegistration(
                StartRegistrationOptions.builder()
                        .user(
                                UserIdentity.builder()
                                        .name("Poler")
                                        .displayName("Poler Hypothetical")
                                        .id(new ByteArray(userHandle))
                                        .build()
                        ).build()
        );
        return request;
    }
}
