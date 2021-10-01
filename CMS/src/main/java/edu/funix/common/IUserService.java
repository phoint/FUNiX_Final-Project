package edu.funix.common;

import java.util.List;

import edu.funix.model.UserModel;

public interface IUserService {
	List<UserModel> findAll();
	Long save(UserModel user);
	Long getTotalItems();
}
