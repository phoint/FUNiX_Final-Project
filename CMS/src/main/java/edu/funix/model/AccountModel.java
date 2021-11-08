package edu.funix.model;

import java.sql.Timestamp;

public class AccountModel<T> extends AbstractModel<T> {
    private String email;
    private String username;
    private String password;
    private String displayName;
    private boolean active;
    private String pictureUrl;
    /* Attributes for limiting login failed attempts */
    private boolean accountNonLocked;
    private int failedAttempts;
    private Timestamp lockTime;
    /* Social Login's Attribute */
    private String registeredFrom;
    private String socialId;

    
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getRegisteredFrom() {
        return registeredFrom;
    }

    public void setRegisteredFrom(String registeredFrom) {
        this.registeredFrom = registeredFrom;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public AccountModel() {
	super();
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

    @Override
    public String toString() {
	return "AccountModel [email=" + email + ", username=" + username + ", password=" + password + ", displayName="
		+ displayName + super.toString() + "]";
    }
}
