package edu.funix.common.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.common.IUserService;
import edu.funix.dao.IUserDAO;
import edu.funix.dao.imp.UserDAO;
import edu.funix.model.UserModel;

public class UserService implements IUserService {
	private IUserDAO userDAO;
	
	
	public UserService() {
		userDAO = new UserDAO();
	}

	@Override
	public List<UserModel> findAll() throws SQLException, Exception {
		return userDAO.findAll();
	}

	@Override
	public Long save(UserModel user) throws SQLException, Exception {
		return userDAO.save(user);
	}

	@Override
	public Long getTotalItems() throws SQLException, Exception {
		return userDAO.getTotalItems();
	}

	@Override
	public UserModel findUserById(long id) throws SQLException, Exception {
		return userDAO.findUserById(id);
	}

	@Override
	public void edit(UserModel user) throws SQLException, Exception {
		userDAO.edit(user);
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void permanentDelete(long id) throws SQLException, Exception {
		userDAO.permanentDelete(id);
	}
}
