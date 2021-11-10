package edu.funix.utils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import edu.funix.model.UserModel;

public class GoogleUtil {
    public static UserModel decode(String idTokenString) throws GeneralSecurityException, IOException {
	GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
		.setIssuer("https://accounts.google.com")
		.setAudience(Collections.singletonList(GoogleConstants.GOOGLE_CLIENT_ID)).build();

	// (Receive idTokenString by HTTPS POST)
	UserModel account = new UserModel();
	GoogleIdToken idToken = verifier.verify(idTokenString);
	if (idToken != null) {
	    Payload payload = idToken.getPayload();
	    account.setSocialId(payload.getSubject());
	    account.setEmail(payload.getEmail());
	    account.setDisplayName((String) payload.get("name"));
	    account.setPictureUrl((String) payload.get("picture"));
	    account.setRegisteredFrom("google");
	    // Print user identifier
//	    String userId = payload.getSubject();
//	    System.out.println("User ID: " + userId);

	    // Get profile information from payload
//	    String email = payload.getEmail();
//	    boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//	    String name = (String) payload.get("name");
//	    String pictureUrl = (String) payload.get("picture");
//	    String locale = (String) payload.get("locale");
//	    String familyName = (String) payload.get("family_name");
//	    String givenName = (String) payload.get("given_name");
	} else {
	    throw new IOException("Invalid ID Token");
	}
	return account;
    }
}
