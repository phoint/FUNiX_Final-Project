package edu.funix.common.imp;

import java.util.List;

import edu.funix.common.IUserService;
import edu.funix.dao.IUserDAO;
import edu.funix.dao.imp.UserDAO;
import edu.funix.model.UserModel;

public class UserService implements IUserService {
	private IUserDAO user;
	
	
	public UserService() {
		user = new UserDAO();
	}

	@Override
	public List<UserModel> findAll() {
		return user.findAll();
	}

	@Override
	public Long save(UserModel user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getTotalItems() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
