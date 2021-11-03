/**
 * @(#) SubcriberDAO.java 1.0 2021/09/06
 * 
 * Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package edu.funix.dao.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.dao.IAccountDAO;
import edu.funix.model.PageModel;
import edu.funix.model.SubcriberModel;
import edu.funix.model.mapper.SubcriberMapper;

/**
 * The UserDAO class provides methods to interact with tblSUBCRIBER in database.
 * 
 * @author Phoi Nguyen
 * @version 1.0 06 September 2021
 */
public class SubcriberDAO extends AbstractDAO<SubcriberModel> implements IAccountDAO<SubcriberModel> {
    /*
     * This class implements from IUserDAO. All javadoc has written in that
     * interface
     */

    @Override
    public List<SubcriberModel> findAll() throws SQLException, Exception {
	String sql = "SELECT * FROM tblSUBCRIBER WHERE Active = 1";
	return query(sql, new SubcriberMapper());
    }

    @Override
    public List<SubcriberModel> findAll(PageModel page) throws SQLException, Exception {
	/* Build a query string */
	StringBuilder sql = new StringBuilder("SELECT * FROM ");
	sql.append("(SELECT *, ROW_NUMBER() OVER (ORDER BY Username ASC) AS RowNumber ");
	sql.append("FROM tblSUBCRIBER WHERE Active = 1) AS Pagable ");
	if (page.getTotalPage() == 1) {
	    /* When the total items fit on one page */
	    return query(sql.toString(), new SubcriberMapper());
	} else {
	    /*
	     * Depending on the current page, the offset and limit will be set to query
	     * statement
	     */
	    sql.append("WHERE RowNumber BETWEEN ? AND ?");
	    return query(sql.toString(), new SubcriberMapper(), page.getOffset() + 1, page.getLimit());
	}
    }

    @Override
    public List<SubcriberModel> searchBy(PageModel page, String searchKey) throws SQLException, Exception {
	StringBuilder sql = new StringBuilder("SELECT * FROM ");
	/* Set the search key for the statement with ... WHERE... LIKE... */
	String key = "%" + searchKey + "%";
	sql.append("(SELECT *, ROW_NUMBER() OVER (ORDER BY Username ASC) AS RowNumber "
		+ "FROM tblSUBCRIBER WHERE Username LIKE ?) AS Pagable ");
	if (page.getTotalPage() == 1) {
	    /* When the total items fit on one page */
	    return query(sql.toString(), new SubcriberMapper(), key);
	} else {
	    /*
	     * Depending on the current page, the offset and limit will be set to query
	     * statement
	     */
	    sql.append("WHERE RowNumber BETWEEN ? AND ?");
	    return query(sql.toString(), new SubcriberMapper(), key, page.getOffset() + 1, page.getLimit());
	}
    }

    @Override
    public SubcriberModel findBy(long id) throws SQLException, Exception {
	String sql = "SELECT * FROM tblSUBCRIBER WHERE UserID = ?";
	return query(sql, new SubcriberMapper(), id).get(0);
    }

    @Override
    public SubcriberModel findBy(String email) throws SQLException, Exception {
	StringBuilder sql = new StringBuilder("SELECT * FROM tblSUBCRIBER ");
	String emailRegex = "[a-z0-9]+[_a-z0-9\\.-]*[a-z0-9]+@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})";
	if (email.matches(emailRegex)) {
	    sql.append("WHERE UserMail = ?");
	} else {
	    sql.append("WHERE Username = ?");
	}
	List<SubcriberModel> subcribers = query(sql.toString(), new SubcriberMapper(), email);
	return subcribers.isEmpty() ? null : subcribers.get(0);
    }

    @Override
    public Long save(SubcriberModel subcriber) throws SQLException, Exception {
	String sql = "INSERT INTO tblSUBCRIBER(UserMail, Username, Pwd,"
		+ "DisplayName) VALUES (?,?,HASHBYTES('SHA2_256', ?),?)";
	return insert(sql, subcriber.getEmail(), subcriber.getUsername(), subcriber.getPassword(), subcriber.getDisplayName());
    }

    @Override
    public void edit(SubcriberModel subcriber) throws SQLException, Exception {
	StringBuilder sql = new StringBuilder("UPDATE tblSUBCRIBER SET UserMail = ?, ");
	if (subcriber.getPassword() != null) {	    
	    sql.append("Pwd = HASHBYTES('SHA2_256', ?), ");
	    sql.append("DisplayName = ? WHERE UserID = ?");
	} else {
	    sql.append("DisplayName = ? WHERE UserID = ?");
	}
	if (subcriber.getPassword() != null) {	    	    
	    update(sql.toString(), subcriber.getEmail(), subcriber.getPassword(), subcriber.getDisplayName(), subcriber.getId());
	} else {
	    update(sql.toString(), subcriber.getEmail(), subcriber.getDisplayName(), subcriber.getId());	    
	}
    }

    @Override
    public Long getTotalItems() throws SQLException, Exception {
	String sql = "SELECT count(*) FROM tblSUBCRIBER";
	return count(sql);
    }
    
    @Override
    public Long getTotalItems(String username) throws SQLException, Exception {
        String sql = "SELECT count(*) FROM tblSUBCRIBER WHERE username LIKE ?";
        String key = "%" + username + "%";
        return count(sql, key);
    }

    @Override
    public void delete(long id) throws SQLException, Exception {
	String sql = "UPDATE tblSUBCRIBER SET Active = 0 WHERE UserID = ?";
	update(sql, id);
    }

    @Override
    public void permanentDelete(long id) throws SQLException, Exception {
	String sql = "DELETE FROM tblSUBCRIBER WHERE UserID = ?";
	update(sql, id);
    }

    @Override
    public SubcriberModel checkLogin(String username, String password) throws SQLException, Exception {
	String sql = "SELECT * FROM tblSUBCRIBER WHERE Username = ? AND Pwd = HASHBYTES('SHA2_256', ?) AND Active = 1";
	List<SubcriberModel> validUser = query(sql, new SubcriberMapper(), username, password);
	return validUser.isEmpty() ? null : validUser.get(0);
    }

    @Override
    public void changePassword(Long userID, String newPassword) throws SQLException, Exception {
	String sql = "UPDATE tblSUBCRIBER SET Pwd = HASHBYTES('SHA2_256', ?) WHERE UserID = ?";
	update(sql, newPassword, userID);
    }
    
    @Override
    public void updateFailedAttempts(int failedAttempts, String username) throws SQLException, Exception {
        String sql = "UPDATE tblSUBCRIBER SET Failed_attempts = ? WHERE Username = ?";
        update(sql, failedAttempts, username);
    }
    
    @Override
    public void updateLockUser(SubcriberModel subcriber) throws SQLException, Exception {
	String sql = "UPDATE tblSUBCRIBER SET Acc_non_locked = ?, Lock_time = ?, Failed_attempts = ? WHERE Username = ?";
	update(sql, subcriber.isAccountNonLocked() ? 1 : 0, subcriber.getLockTime(), subcriber.getFailedAttempts(), subcriber.getUsername());
    }

}
