/*
 * @(#) UserDAO.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.dao.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.dao.IUserDAO;
import edu.funix.model.PageModel;
import edu.funix.model.UserModel;
import edu.funix.model.mapper.UserMapper;

/**
 * The UserDAO class provides methods to interact with tblUSER in database.
 * 
 * @author Phoi Nguyen
 * @version 1.0 06 September 2021
 */
public class UserDAO extends AbstractDAO<UserModel> implements IUserDAO {
    /*
     * This class implements from IUserDAO. All javadoc has written in that
     * interface
     */

    @Override
    public List<UserModel> findAll() throws SQLException, Exception {
	String sql = "SELECT * FROM tblUSER";
	return query(sql, new UserMapper());
    }

    @Override
    public List<UserModel> findAll(PageModel page) throws SQLException, Exception {
	/* Build a query string */
	StringBuilder sql = new StringBuilder("SELECT * FROM ");
	sql.append("(SELECT *, ROW_NUMBER() OVER (ORDER BY Username ASC) AS RowNumber ");
	sql.append("FROM tblUSER) AS Pagable ");
	if (page.getTotalPage() == 1) {
	    /* When the total items fit on one page */
	    return query(sql.toString(), new UserMapper());
	} else {
	    /*
	     * Depending on the current page, the offset and limit will be set to query
	     * statement
	     */
	    sql.append("WHERE RowNumber BETWEEN ? AND ?");
	    return query(sql.toString(), new UserMapper(), page.getOffset() + 1, page.getLimit());
	}
    }

    @Override
    public List<UserModel> searchBy(PageModel page, String searchKey) throws SQLException, Exception {
	StringBuilder sql = new StringBuilder("SELECT * FROM ");
	String key = "%" + searchKey + "%";
	sql.append("(SELECT *, ROW_NUMBER() OVER (ORDER BY Username ASC) AS RowNumber "
		+ "FROM tblUSER WHERE Username LIKE ?) AS Pagable ");
	if (page.getTotalPage() == 1) {
	    return query(sql.toString(), new UserMapper(), key);
	} else {
	    sql.append("WHERE RowNumber BETWEEN ? AND ?");
	    return query(sql.toString(), new UserMapper(), key, page.getOffset() + 1, page.getLimit());
	}
    }

    @Override
    public UserModel findBy(long id) throws SQLException, Exception {
	String sql = "SELECT * FROM tblUSER WHERE UserID = ?";
	return query(sql, new UserMapper(), id).get(0);
    }

    @Override
    public UserModel findBy(String email) throws SQLException, Exception {
	StringBuilder sql = new StringBuilder("SELECT * FROM tblUSER ");
	String emailRegex = "[a-z0-9]+[_a-z0-9\\.-]*[a-z0-9]+@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})";
	if (email.matches(emailRegex)) {
	    sql.append("WHERE UserMail = ?");
	} else {
	    sql.append("WHERE Username = ?");
	}
	List<UserModel> users = query(sql.toString(), new UserMapper(), email);
	return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public Long save(UserModel user) throws SQLException, Exception {
	String sql = "INSERT INTO tblUSER(UserMail, Username, Pwd,"
		+ "DisplayName, UserRole) VALUES (?,?,HASHBYTES('SHA2_256', ?),?,?)";
	return insert(sql, user.getEmail(), user.getUsername(), user.getPassword(), user.getDisplayName(),
		user.isRole() ? 1 : 0);
    }

    @Override
    public void edit(UserModel user) throws SQLException, Exception {
	String sql = "UPDATE tblUSER SET UserMail = ?, Pwd = HASHBYTES('SHA2_256', ?), DisplayName = ?, "
		+ "UserRole = ? WHERE UserID = ?";
	update(sql, user.getEmail(), user.getPassword(), user.getDisplayName(), user.isRole() ? 1 : 0, user.getId());
    }

    @Override
    public Long getTotalItems() throws SQLException, Exception {
	String sql = "SELECT count(*) FROM tblUSER";
	return count(sql);
    }
    
    @Override
    public Long getTotalItems(String username) throws SQLException, Exception {
        String sql = "SELECT count(*) FROM tblUSER WHERE username LIKE ?";
        String key = "%" + username + "%";
        return count(sql, key);
    }

    @Override
    public void delete(long id) {
	// TODO Update status for soft delete
    }

    @Override
    public void permanentDelete(long id) throws SQLException, Exception {
	String sql = "DELETE FROM tblUSER WHERE UserID = ?";
	update(sql, id);
    }

    @Override
    public UserModel checkLogin(String username, String password) throws SQLException, Exception {
	String sql = "SELECT * FROM tblUSER WHERE Username = ? AND Pwd = HASHBYTES('SHA2_256', ?)";
	List<UserModel> validUser = query(sql, new UserMapper(), username, password);
	return validUser.isEmpty() ? null : validUser.get(0);
    }

    @Override
    public void changePassword(Long userID, String newPassword) throws SQLException, Exception {
	String sql = "UPDATE tblUSER SET Pwd = HASHBYTES('SHA2_256', ?) WHERE UserID = ?";
	update(sql, newPassword, userID);
    }
    
    @Override
    public void updateFailedAttempts(int failedAttempts, String username) throws SQLException, Exception {
        String sql = "UPDATE tblUSER SET Failed_attempts = ? WHERE Username = ?";
        update(sql, failedAttempts, username);
    }
    
    @Override
    public void updateLockUser(UserModel user) throws SQLException, Exception {
	String sql = "UPDATE tblUSER SET Acc_non_locked = ?, Lock_time = ?, Failed_attempts = ? WHERE Username = ?";
	update(sql, user.isAccountNonLocked() ? 1 : 0, user.getLockTime(), user.getFailedAttempts(), user.getUsername());
    }

}
