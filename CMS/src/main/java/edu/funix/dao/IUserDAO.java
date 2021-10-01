package edu.funix.dao;

import java.util.List;

import edu.funix.model.UserModel;

public interface IUserDAO extends GenericDAO<UserModel> {
	List<UserModel> findAll();
	UserModel findUserById(long id);
	Long save(UserModel user);
	void edit(UserModel user);
	Long getTotalItems();
}
