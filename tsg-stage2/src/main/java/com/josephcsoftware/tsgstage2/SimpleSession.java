package com.josephcsoftware.tsgstage2;

import org.springframework.security.oauth2.jwt.Jwt;

public class SimpleSession {

    public final String firstName;
    public final String lastName;
    public final String email;
    public final String subject;
    public final String provider;

    public SimpleSession(Jwt jwt) {
        this.firstName = extract(jwt, "given_name", "(no first name)");
        this.lastName = extract(jwt, "family_name", "(no last name)");
        this.email = jwt.getClaimAsString("email");
        this.subject = jwt.getSubject();
        this.provider = jwt.getIssuer().toString();
    }

    private static String extract(Jwt jwt, String key, String fallback) {
        try { // Just in case something goes wrong, do not stop the work
            String claim = jwt.getClaimAsString(key);
            if (claim == null) return fallback;
            if (claim.trim().length() == 0) return fallback;
            return claim;
        } catch (Exception ex) {
            return fallback;
        }
    }

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getSubject() {
		return subject;
	}

	public String getProvider() {
		return provider;
	}
}
