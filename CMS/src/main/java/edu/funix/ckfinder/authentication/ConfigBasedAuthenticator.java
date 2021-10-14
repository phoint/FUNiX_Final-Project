package edu.funix.ckfinder.authentication;

import javax.inject.Named;

import com.cksource.ckfinder.authentication.Authenticator;

@Named
public class ConfigBasedAuthenticator implements Authenticator {
    @Override
    public boolean authenticate() {
	return true;
    }
}
