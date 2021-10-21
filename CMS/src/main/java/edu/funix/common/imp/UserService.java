package edu.funix.common.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.common.IPageableService;
import edu.funix.common.IUserService;
import edu.funix.dao.IPostDAO;
import edu.funix.dao.IUserDAO;
import edu.funix.dao.imp.PostDAO;
import edu.funix.dao.imp.UserDAO;
import edu.funix.domain.ChangePasswordForm;
import edu.funix.model.PageModel;
import edu.funix.model.UserModel;

public class UserService implements IUserService {
    private IUserDAO userDAO;
    private IPostDAO postDAO;
    private IPageableService paging;

    public UserService() {
	userDAO = new UserDAO();
	paging = new PageableService();
	postDAO = new PostDAO();
    }

    @Override
    public List<UserModel> findAll() throws SQLException, Exception {
	List<UserModel> users = userDAO.findAll();
	for (UserModel user : users) {
	    user.setTotalPost(postDAO.getTotalItems(user.getId()));
	}
	return users;
    }

    @Override
    public List<UserModel> findAll(PageModel page) throws SQLException, Exception {
	page = paging.pageRequest(page, userDAO.getTotalItems());
	List<UserModel> users = userDAO.findAll(page);
	for (UserModel user : users) {
	    user.setTotalPost(postDAO.getTotalItems(user.getId()));
	}
	return users;
    }

    @Override
    public List<UserModel> search(PageModel page, String searchKey) throws SQLException, Exception {
	page = paging.pageRequest(page, userDAO.getTotalItems(searchKey));
	List<UserModel> users = userDAO.searchBy(page, searchKey);
	for (UserModel user : users) {
	    user.setTotalPost(postDAO.getTotalItems(user.getId()));
	}
	return users;
    }

    @Override
    public UserModel findByEmail(String email) throws SQLException, Exception {
	return userDAO.findBy(email);
    }

    @Override
    public Long save(UserModel user) throws SQLException, Exception {
	return userDAO.save(user);
    }

    @Override
    public UserModel findUserById(long id) throws SQLException, Exception {
	return userDAO.findBy(id);
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
    public UserModel checkLogin(String username, String password) throws SQLException, Exception {
	UserModel user = new UserModel();
//	String emailRegex = "[a-z0-9]+[_a-z0-9\\.-]*[a-z0-9]+@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})";
	/* Regex Pattern for safe password */
	String pwdRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d_@$!%*#?&\\.]{8,}$";
	/* Checks the safe password satisfy with pattern or not */
	if (!password.matches(pwdRegex)) {
	    throw new Exception("Password must be at least 8 character, one uppercase and one number");
	} else {
	    user = userDAO.checkLogin(username, password);
	    if (user == null) {
		throw new Exception("Invalid Username or Password");
	    }
	}
	return user;
    }

    @Override
    public void changePassword(ChangePasswordForm newPwd) throws SQLException, Exception {
	UserModel user = null;
	/* Regex Pattern for safe password */
	String pwdRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d_@$!%*#?&\\.]{8,}$";
	/* Checks the safe password satisfy with pattern or not */
	if (!newPwd.getPassword().matches(pwdRegex)) {
	    throw new Exception("Password must be at least 8 character, one uppercase and one number");
	} else {
	    user = userDAO.checkLogin(newPwd.getUsername(), newPwd.getCurrPassword());
	    if (user != null) {
		userDAO.changePassword(user.getId(), newPwd.getConfirmPassword());
	    } else {
		throw new Exception("Current password is incorrect");
	    }
	}
    }
}
