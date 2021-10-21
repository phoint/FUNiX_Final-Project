/*
 * @(#) UserModel.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.model;

/**
 * The User model representing all attribute of a user's account created
 * 
 * @author Phoi Nguyen
 * @version 1.0 06 September 2021
 */
public class UserModel extends AbstractModel<UserModel> {
    private String email;
    private String username;
    private String password;
    private String displayName;
    private boolean role;
    private String loginMessage;
    private String hashPwd;

    public UserModel() {
    }
    
    public UserModel(String email, String username, String password, String displayName, boolean role) {
	super();
	this.email = email;
	this.username = username;
	this.password = password;
	this.displayName = displayName;
	this.role = role;
    }

    public String getHashPwd() {
	return hashPwd;
    }

    public void setHashPwd(String hashPwd) {
	this.hashPwd = hashPwd;
    }

    public String getLoginMessage() {
	return loginMessage;
    }

    public void setLoginMessage(String loginMessage) {
	this.loginMessage = loginMessage;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getDisplayName() {
	return displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }

    public boolean isRole() {
	return role;
    }

    public void setRole(boolean role) {
	this.role = role;
    }
}
