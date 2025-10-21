package com.josephcsoftware.tsgstage2;

import org.springframework.security.oauth2.jwt.Jwt;

public class SimpleSession {

    public final String subject;
    public final String provider;

    public SimpleSession(Jwt jwt) {
        this.subject = jwt.getSubject();
        this.provider = jwt.getIssuer().toString();
    }
}
