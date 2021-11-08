package edu.funix.common.imp;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.beanutils.BeanUtils;

import edu.funix.common.IAccountService;
import edu.funix.common.IPageableService;
import edu.funix.dao.IAccountDAO;
import edu.funix.dao.imp.SubcriberDAO;
import edu.funix.domain.ChangePasswordForm;
import edu.funix.model.PageModel;
import edu.funix.model.SubcriberModel;
import edu.funix.model.SubcriberModel;
import edu.funix.model.SubcriberModel;

public class SubcriberService implements IAccountService<SubcriberModel> {
    /* The limited number of attempts */
    public static final int MAX_FAILED_ATTEMPTS = 3;
    /* The duration of locking user */
    private static final long LOCK_TIME_DURATION = 15 * 60 * 1000; // 15 minutes
    private IAccountDAO<SubcriberModel> subcriberDAO;
    private IPageableService paging;

    public SubcriberService() {
	subcriberDAO = new SubcriberDAO();
	paging = new PageableService();
    }

    @Override
    public List<SubcriberModel> findAll() throws SQLException, Exception {
	return subcriberDAO.findAll();
    }

    @Override
    public List<SubcriberModel> findAll(PageModel page) throws SQLException, Exception {
	page = paging.pageRequest(page, subcriberDAO.getTotalItems());
	return subcriberDAO.findAll(page);
    }

    @Override
    public List<SubcriberModel> search(PageModel page, String searchKey) throws SQLException, Exception {
	page = paging.pageRequest(page, subcriberDAO.getTotalItems(searchKey));
	return subcriberDAO.searchBy(page, searchKey);
    }

    @Override
    public SubcriberModel findBy(long id) throws SQLException, Exception {
	SubcriberModel subcriber = subcriberDAO.findBy(id);
	if (subcriber == null) {
	    throw new SQLException("Account is not found");
	}
	return subcriber;
    }

    @Override
    public SubcriberModel findBy(String email) throws SQLException, Exception {
	SubcriberModel subcriber = subcriberDAO.findBy(email);
	if (subcriber == null) {
	    throw new SQLException("Account is not found");
	}
	return subcriber;
    }

    @Override
    public Long save(SubcriberModel account) throws SQLException, Exception {
	Long newId = null;
	String pwdRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d_@$!%*#?&\\.]{8,}$";
	String usernameRegex = "[a-zA-Z][a-zA-Z0-9-_]{3,32}";
	String mailRegex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)"
		+ "*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

	/* MD5 hashing email for gravatar */
	MessageDigest md = MessageDigest.getInstance("MD5");
	md.update(account.getEmail().getBytes());
	byte[] digest = md.digest();
	String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();

	/* Checks the safe password satisfy with pattern or not */
	if (account.getSocialId() == null || account.getSocialId().isEmpty()) {
	    if (account.getUsername() == null || !account.getUsername().matches(usernameRegex)) {
		throw new Exception("Username must be at least 3 character start with an alphabetic. "
			+ "Can contain number, - and _, but no space");
	    } else if (account.getEmail() == null || !account.getEmail().matches(mailRegex)) {
		throw new Exception("Invalid email address");
	    } else if (account.getPassword() == null || !account.getPassword().matches(pwdRegex)) {
		throw new Exception("Password must be at least 8 character, one uppercase and one number");
	    } else {
		account.setPictureUrl("https://www.gravatar.com/avatar/" + myHash + "?d=mp");
		newId = subcriberDAO.save(account);
	    }
	} else {
	    newId = subcriberDAO.save(account);
	}
	return newId;
    }

    @Override
    public void edit(SubcriberModel account) throws SQLException, Exception {
	/* Regex pattern for nice username */
	String usernameRegex = "[a-zA-Z][a-zA-Z0-9-_]{3,32}";
	/* Regex Pattern for safe password */
	String pwdRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d_@$!%*#?&\\.]{8,}$";
	if (account.getSocialId() == null || account.getSocialId().isEmpty()) {
	    if (account.getUsername() == null || !account.getUsername().matches(usernameRegex)) {
		/* Checks the valid username */
		throw new Exception("Username must be at least 3 character start with an alphabetic. "
			+ "Can contain number, - and _, but no space");
	    } else if (account.getPassword() != null && !account.getPassword().matches(pwdRegex)) {
		/* Checks the safe password satisfy with pattern or not */
		throw new Exception("Password must be at least 8 character, one uppercase and one number");
	    } else {
		subcriberDAO.edit(account);
	    }
	} else {
	    subcriberDAO.edit(account);
	}
    }

    @Override
    public void delete(String[] ids) throws NumberFormatException, SQLException, Exception {
	for (String id : ids) {
	    subcriberDAO.delete(Long.parseLong(id));
	}
    }

    @Override
    public void permanentDelete(String[] ids) throws SQLException, Exception {
	for (String id : ids) {
	    subcriberDAO.permanentDelete(Long.parseLong(id));
	}
    }

    /**
     * Checks existed subcriber's account for logging in system
     * 
     * @param account A SubcriberModel containing subcriber's attribute for checking
     * @return A SubcriberModel representing the subcriber's attribute from database
     * @throws SQLException
     * @throws Exception
     * @see SubcriberModel
     */
    @Override
    public SubcriberModel checkLogin(SubcriberModel account) throws SQLException, Exception {
	SubcriberModel subcriber = null;
	    /* Regex pattern for nice username */
	    String usernameRegex = "[a-zA-Z][a-zA-Z0-9-_]{3,32}";
	    /* Regex Pattern for safe password */
	    String pwdRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d_@$!%*#?&\\.]{8,}$";
	    if (!account.getUsername().matches(usernameRegex)) {
		/* Checks the valid username */
		throw new Exception("Username must be at least 3 character start with an alphabetic. "
			+ "Can contain number, - and _, but no space");
	    } else if (!account.getPassword().matches(pwdRegex)) {
		/* Checks the safe password satisfy with pattern or not */
		throw new Exception("Password must be at least 8 character, one uppercase and one number");
	    } else {
		subcriber = subcriberDAO.checkLogin(account);
	    }
	return subcriber;
    }

    /**
     * Check login subcriber's account which registered by social account
     * 
     * @param account A SubcriberModel containing the subcriber's attribute for
     *                checking
     * @return A SubcriberModel representing the subcriber's attribute from database
     * @see SubcriberModel
     */
    @Override
    public SubcriberModel socialLogin(SubcriberModel account) throws SQLException, Exception {
	SubcriberModel validUser = subcriberDAO.socialLogin(account);
	if (validUser == null) {
	    throw new Exception("Account is not existed. Please Sign Up to continue.");
	}
	return validUser;
    }

    @Override
    public void changePassword(ChangePasswordForm newPwd) throws SQLException, Exception {
	SubcriberModel subcriber = null;
	/* Regex Pattern for safe password */
	String pwdRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d_@$!%*#?&\\.]{8,}$";
	/* Checks the safe password satisfy with pattern or not */
	if (!newPwd.getPassword().matches(pwdRegex)) {
	    throw new Exception("Password must be at least 8 character, one uppercase and one number");
	} else {
	    SubcriberModel account = new SubcriberModel();
	    BeanUtils.copyProperties(account, newPwd);
	    subcriber = subcriberDAO.checkLogin(account);
	    if (subcriber != null) {
		subcriberDAO.changePassword(subcriber.getId(), newPwd.getConfirmPassword());
	    } else {
		throw new Exception("Current password is incorrect");
	    }
	}

    }

    @Override
    public void increaseFailedAttempts(SubcriberModel account) throws SQLException, Exception {
	int newFailedAttempts = account.getFailedAttempts() + 1;
	subcriberDAO.updateFailedAttempts(newFailedAttempts, account.getUsername());
    }

    @Override
    public void resetFailedAttempts(String username) throws SQLException, Exception {
	subcriberDAO.updateFailedAttempts(0, username);

    }

    @Override
    public void lockUser(SubcriberModel account) throws SQLException, Exception {
	account.setAccountNonLocked(false);
	long currentTimeInMillis = System.currentTimeMillis();
	java.sql.Timestamp convertDate = new java.sql.Timestamp(currentTimeInMillis);
	account.setLockTime(convertDate);
	subcriberDAO.updateLockUser(account);
    }

    @Override
    public boolean unlockWhenTimeExpired(SubcriberModel account) throws SQLException, Exception {
	long lockTimeInMillis = account.getLockTime().getTime();
	long currentTimeInMillis = System.currentTimeMillis();

	if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
	    account.setAccountNonLocked(true);
	    account.setLockTime(null);
	    account.setFailedAttempts(0);
	    subcriberDAO.updateLockUser(account);
	    return true;
	}
	return false;
    }

    @Override
    public SubcriberModel LoginAttempt(SubcriberModel account) throws SQLException, Exception {
	SubcriberModel loginUser = null;
	/* Regex pattern for nice username */
	String usernameRegex = "[a-zA-Z][a-zA-Z0-9-_]{3,32}";
	/* Regex Pattern for safe password */
	String pwdRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d_@$!%*#?&\\.]{8,}$";
	if (!account.getUsername().matches(usernameRegex)) {
	    /* Checks the valid username */
	    throw new Exception(
		    "Username must be at least 3 character start with an alphabetic. Can contain number, - and _, but no space");
	} else if (!account.getPassword().matches(pwdRegex)) {
	    /* Checks the safe password satisfy with pattern or not */
	    throw new Exception("Password must be at least 8 character, one uppercase and one number");
	} else {
	    SubcriberModel userAttempt = findBy(account.getUsername());
	    if (userAttempt != null) {
		if (userAttempt.isAccountNonLocked() && userAttempt.isActive()) {
		    if (userAttempt.getFailedAttempts() < MAX_FAILED_ATTEMPTS - 1) {
			loginUser = checkLogin(account);
			if (loginUser == null) {
			    increaseFailedAttempts(userAttempt);
			    throw new Exception("Invalid Username or Password.");
			} else {
			    resetFailedAttempts(account.getUsername());
			}
		    } else {
			lockUser(userAttempt);
			throw new Exception(
				"Your account has been locked due to 3 failed attempts. It will be unlocked after 15 minutes.");
		    }
		} else if (!userAttempt.isAccountNonLocked() && userAttempt.isActive()) {
		    if (unlockWhenTimeExpired(userAttempt)) {
			loginUser = checkLogin(account);
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
