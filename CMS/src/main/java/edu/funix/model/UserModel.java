package edu.funix.model;

public class UserModel extends AbstractModel<UserModel> {
	private String email;
	private String username;
	private String password;
	private String displayName;
	private boolean role;
	private String loginMessage;
	private String hashPwd;

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

	public UserModel() {
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

	public boolean isRole() {
		return role;
	}

	public void setRole(boolean role) {
		this.role = role;
	}
}
