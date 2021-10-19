package edu.funix.domain;

public class ChangePasswordForm {
    private String username;
    private String password;
    private String confirmPassword;
    private String currPassword;
    

    public ChangePasswordForm() {
    }

    public ChangePasswordForm(String username, String password, String confirmPassword, String currPassword) {
	this.username = username;
	this.password = password;
	this.confirmPassword = confirmPassword;
	this.currPassword = currPassword;
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

    public String getConfirmPassword() {
	return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
    }

    public String getCurrPassword() {
	return currPassword;
    }

    public void setCurrPassword(String currPassword) {
	this.currPassword = currPassword;
    }
}
