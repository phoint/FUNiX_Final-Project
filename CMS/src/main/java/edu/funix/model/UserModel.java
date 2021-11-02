/*
 * @(#) UserModel.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.model;

import java.sql.Timestamp;

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
    private long totalPost;
    private boolean active;
    /* Attributes for limiting login failed attempts */
    private boolean accountNonLocked;
    private int failedAttempts;
    private Timestamp lockTime;

    public UserModel() {
	super();
    }

    public UserModel(String email, String username, String password, String displayName, boolean role) {
	super();
	this.email = email;
	this.username = username;
	this.password = password;
	this.displayName = displayName;
	this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAccountNonLocked() {
	return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
	this.accountNonLocked = accountNonLocked;
    }

    public int getFailedAttempts() {
	return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
	this.failedAttempts = failedAttempts;
    }

    public Timestamp getLockTime() {
	return lockTime;
    }

    public void setLockTime(Timestamp lockTime) {
	this.lockTime = lockTime;
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

    public long getTotalPost() {
	return totalPost;
    }

    public void setTotalPost(long totalPost) {
	this.totalPost = totalPost;
    }

    @Override
    public String toString() {
	return String.format(
		"UserModel [email=%s, username=%s, password=%s, displayName=%s, role=%s, totalPost=%s, accountNonLocked=%s, failedAttempts=%s, lockTime=%s]",
		email, username, password, displayName, role, totalPost, accountNonLocked, failedAttempts, lockTime);
    }
}
