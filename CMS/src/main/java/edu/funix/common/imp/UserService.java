package edu.funix.common.imp;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    /* The limited number of attempts */
    public static final int MAX_FAILED_ATTEMPTS = 3;
    /* The duration of locking user */
    private static final long LOCK_TIME_DURATION = 15 * 60 * 1000; // 15 minutes

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
    public UserModel findBy(String email) throws SQLException, Exception {
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
	UserModel user = null;
//	String emailRegex = "[a-z0-9]+[_a-z0-9\\.-]*[a-z0-9]+@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})";
	/* Regex Pattern for safe password */
	String pwdRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d_@$!%*#?&\\.]{8,}$";
	/* Checks the safe password satisfy with pattern or not */
	if (!password.matches(pwdRegex)) {
	    throw new Exception("Password must be at least 8 character, one uppercase and one number");
	} else {
	    user = userDAO.checkLogin(username, password);
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

    @Override
    public void increaseFailedAttempts(UserModel user) throws SQLException, Exception {
	int newFailedAttempts = user.getFailedAttempts() + 1;
	userDAO.updateFailedAttempts(newFailedAttempts, user.getUsername());
    }

    @Override
    public void resetFailedAttempts(String username) throws SQLException, Exception {
	userDAO.updateFailedAttempts(0, username);
    }

    @Override
    public void lockUser(UserModel user) throws SQLException, Exception {
	user.setAccountNonLocked(false);
	long currentTimeInMillis = System.currentTimeMillis();
	java.sql.Timestamp convertDate = new java.sql.Timestamp(currentTimeInMillis);
	user.setLockTime(convertDate);
	userDAO.updateLockUser(user);
    }

    @Override
    public boolean unlockWhenTimeExpired(UserModel user) throws SQLException, Exception {
	long lockTimeInMillis = user.getLockTime().getTime();
	long currentTimeInMillis = System.currentTimeMillis();

	if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
	    user.setAccountNonLocked(true);
	    user.setLockTime(null);
	    user.setFailedAttempts(0);
	    userDAO.updateLockUser(user);
	    return true;
	}
	return false;
    }

    @Override
    public UserModel LoginAttempt(UserModel user) throws SQLException, Exception {
	UserModel userAttempt = findBy(user.getUsername());
	UserModel loginUser = null;
	if (userAttempt != null) {
	    if (userAttempt.isAccountNonLocked()) {
		if (userAttempt.getFailedAttempts() < MAX_FAILED_ATTEMPTS - 1) {
		    loginUser = checkLogin(user.getUsername(), user.getPassword());
		    if (loginUser == null) {
			increaseFailedAttempts(userAttempt);
			throw new Exception("Invalid Username or Password.");
		    } else {
			resetFailedAttempts(user.getUsername());
		    }
		} else {
		    lockUser(userAttempt);
		    throw new Exception(
			    "Your account has been locked due to 3 failed attempts. It will be unlocked after 15 minutes.");
		}
	    } else if (!userAttempt.isAccountNonLocked()) {
		if (unlockWhenTimeExpired(userAttempt)) {
		    loginUser = checkLogin(user.getUsername(), user.getPassword());
		    if (loginUser == null) {
			increaseFailedAttempts(userAttempt);
			throw new Exception("Invalid Username or Password");
		    }
		} else {
		    long lockTimeInMillis = userAttempt.getLockTime().getTime();
		    long currentTimeInMillis = System.currentTimeMillis();
		    long remainHours = TimeUnit.MILLISECONDS
			    .toMinutes(lockTimeInMillis + LOCK_TIME_DURATION - currentTimeInMillis);
		    throw new Exception(
			    "Your account has been locked due to 3 failed attempts. It will be unlocked about "
				    + remainHours + " minutes.");
		}
	    }
	} else {
	    throw new Exception("Username is not existed");
	}
	return loginUser;
    }
}
