package edu.funix.common.imp;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.BeanUtils;

import edu.funix.common.IAccountService;
import edu.funix.common.IPageableService;
import edu.funix.dao.IAccountDAO;
import edu.funix.dao.IPostDAO;
import edu.funix.dao.imp.PostDAO;
import edu.funix.dao.imp.UserDAO;
import edu.funix.domain.ChangePasswordForm;
import edu.funix.model.PageModel;
import edu.funix.model.UserModel;

public class UserService implements IAccountService<UserModel> {
    private IAccountDAO<UserModel> userDAO;
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
	Long newId = null;
	String pwdRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d_@$!%*#?&\\.]{8,}$";
	String usernameRegex = "[a-zA-Z][a-zA-Z0-9-_]{3,32}";
	String mailRegex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
	/* Checks the safe password satisfy with pattern or not */
	if (!user.getUsername().matches(usernameRegex)) {
	    throw new Exception("Username must be at least 3 character start with an alphabetic. Can contain number, - and _, but no space");
	} else if (user.getEmail().matches(mailRegex)) {
	    throw new Exception("Invalid email address");
	}else if (!user.getPassword().matches(pwdRegex)) {
	    throw new Exception("Password must be at least 8 character, one uppercase and one number");
	} else {
	    newId = userDAO.save(user);
	}
	return newId;
    }

    @Override
    public UserModel findBy(long id) throws SQLException, Exception {
	return userDAO.findBy(id);
    }

    @Override
    public void edit(UserModel user) throws SQLException, Exception {
	/* Regex pattern for nice username */
	String usernameRegex = "[a-zA-Z][a-zA-Z0-9-_]{3,32}";
	/* Regex Pattern for safe password */
	String pwdRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d_@$!%*#?&\\.]{8,}$";
	if (user.getUsername() == null || !user.getUsername().matches(usernameRegex)) {
	    /* Checks the valid username */
	    throw new Exception("Username must be at least 3 character start with an alphabetic. Can contain number, - and _, but no space");
	} else if (user.getPassword() != null && !user.getPassword().matches(pwdRegex)) {
	    /* Checks the safe password satisfy with pattern or not */
	    throw new Exception("Password must be at least 8 character, one uppercase and one number");
	} else {	    
	    userDAO.edit(user);
	}
    }

    @Override
    public void delete(String[] ids) throws NumberFormatException, SQLException, Exception {
	for (String id : ids) {
	    userDAO.delete(Long.parseLong(id));
	}
    }

    @Override
    public void permanentDelete(String[] ids) throws SQLException, Exception {
	for (String id : ids) {
	    userDAO.permanentDelete(Long.parseLong(id));
	}
    }

    @Override
    public UserModel checkLogin(UserModel account) throws SQLException, Exception {
	UserModel user = null;
	/* Regex pattern for nice username */
	String usernameRegex = "[a-zA-Z][a-zA-Z0-9-_]{3,32}";
	/* Regex Pattern for safe password */
	String pwdRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d_@$!%*#?&\\.]{8,}$";
	if (!account.getUsername().matches(usernameRegex)) {
	    /* Checks the valid username */
	    throw new Exception("Username must be at least 3 character start with an alphabetic. Can contain number, - and _, but no space");
	} else if (!account.getPassword().matches(pwdRegex)) {
	    /* Checks the safe password satisfy with pattern or not */
	    throw new Exception("Password must be at least 8 character, one uppercase and one number");
	} else {
	    user = userDAO.checkLogin(account);
	}
	return user;
    }
    
    @Override
    public UserModel socialLogin(UserModel account) throws SQLException, Exception {
	UserModel validUser = userDAO.socialLogin(account);
	if (validUser == null) {
	    throw new Exception("Account is not existed. Please Sign Up to continue.");
	}
	return validUser;
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
	    UserModel account = new UserModel();
	    BeanUtils.copyProperties(account, newPwd);
	    user = userDAO.checkLogin(account);
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
	UserModel loginUser = null;
	/* Regex pattern for nice username */
	String usernameRegex = "[a-zA-Z][a-zA-Z0-9-_]{3,32}";
	/* Regex Pattern for safe password */
	String pwdRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d_@$!%*#?&\\.]{8,}$";
	if (!user.getUsername().matches(usernameRegex)) {
	    /* Checks the valid username */
	    throw new Exception("Username must be at least 3 character start with an alphabetic. Can contain number, - and _, but no space");
	} else if (!user.getPassword().matches(pwdRegex)) {
	    /* Checks the safe password satisfy with pattern or not */
	    throw new Exception("Password must be at least 8 character, one uppercase and one number");
	} else {
	    UserModel userAttempt = findBy(user.getUsername());
	    if (userAttempt != null) {
		if (userAttempt.isAccountNonLocked() && userAttempt.isActive()) {
		    if (userAttempt.getFailedAttempts() < MAX_FAILED_ATTEMPTS - 1) {
			loginUser = checkLogin(user);
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
		} else if (!userAttempt.isAccountNonLocked() && userAttempt.isActive()) {
		    if (unlockWhenTimeExpired(userAttempt)) {
			loginUser = checkLogin(user);
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
		} else if (!userAttempt.isActive()) {
		    throw new Exception("This account is disabled. Please contact admin for more detail");
		}
	    } else {
		throw new Exception("Username is not existed");
	    }
	}
	return loginUser;
    }
}
