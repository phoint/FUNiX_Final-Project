package edu.funix.common.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.common.IUserService;
import edu.funix.dao.IUserDAO;
import edu.funix.dao.imp.UserDAO;
import edu.funix.model.CategoryModel;
import edu.funix.model.PageModel;
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
	public List<UserModel> findAll(long offset, long limit) throws SQLException, Exception {
		return userDAO.findAll(offset,limit);
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
	
	@Override
	public List<UserModel> pageRequest(PageModel page) throws SQLException, Exception {
		if (page.getMaxItem() == null) {
			page.setMaxItem(10);
		}
		page.setTotalPage((long)Math.ceil((double)getTotalItems() / page.getMaxItem()));
		if (page.getTotalPage() > 1 && page.getCurrentPage() == null) {
			page.setCurrentPage(1);
		} 
		if (page.getCurrentPage() != null) {
			long offset = (page.getCurrentPage() - 1) * page.getMaxItem();
			long limit = page.getCurrentPage() * page.getMaxItem();
			return findAll(offset, limit);
		}
		return findAll();
	}

	@Override
	public UserModel checkLogin(String username, String password) throws SQLException, Exception {
		UserModel user = new UserModel();
//		String emailRegex = "[a-z0-9]+[_a-z0-9\\.-]*[a-z0-9]+@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})";
		String pwdRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d_@$!%*#?&\\.]{8,}$";
		if (!password.matches(pwdRegex)) {
			user.setLoginMessage("Password must be at least 8 character, one uppercase and one number");
		} else {
			user = userDAO.checkLogin(username, password);
			if (user == null) {
				user = new UserModel();
				user.setLoginMessage("Invalid Username or Password");
			}
		}
		return user;
	}
}
