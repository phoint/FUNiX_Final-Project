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
public class UserModel extends AccountModel<UserModel> {
    private boolean role;
    private long totalPost;

    public UserModel() {
	super();
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
	return "UserModel [role=" + role + ", totalPost=" + totalPost + ", toString()=" + super.toString() + "]";
    }
    
    
}
