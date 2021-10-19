package edu.funix.dao.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.dao.IUserDAO;
import edu.funix.model.PageModel;
import edu.funix.model.UserModel;
import edu.funix.model.mapper.UserMapper;

public class UserDAO extends AbstractDAO<UserModel> implements IUserDAO {

    @Override
    public List<UserModel> findAll() throws SQLException, Exception {
	String sql = "SELECT * FROM tblUSER";
	return query(sql, new UserMapper());
    }

    @Override
    public List<UserModel> findAll(PageModel page) throws SQLException, Exception {
	StringBuilder sql = new StringBuilder("SELECT * FROM ");
	sql.append("(SELECT *, ROW_NUMBER() OVER (ORDER BY Username ASC) AS RowNumber ");
	sql.append("FROM tblUSER) AS Pagable ");
	if (page.getTotalPage() == 1) {
	    return query(sql.toString(), new UserMapper());
	} else {
	    sql.append("WHERE RowNumber BETWEEN ? AND ?");
	    return query(sql.toString(), new UserMapper(), page.getOffset() + 1, page.getLimit());
	}
    }

    @Override
    public UserModel findUserById(long id) throws SQLException, Exception {
	String sql = "SELECT * FROM tblUSER WHERE UserID = ?";
	return query(sql, new UserMapper(), id).get(0);
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
	String sql = "{CALL VerifyAccount(?,?)}";
	List<UserModel> validUser = call(sql, new UserMapper(), username, password);
	return validUser.isEmpty() ? null : validUser.get(0);
    }

}
