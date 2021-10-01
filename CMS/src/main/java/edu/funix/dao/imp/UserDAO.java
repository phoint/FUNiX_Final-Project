package edu.funix.dao.imp;

import java.util.List;

import edu.funix.dao.IUserDAO;
import edu.funix.model.UserModel;
import edu.funix.model.mapper.UserMapper;

public class UserDAO extends AbstractDAO<UserModel> implements IUserDAO{

	@Override
	public List<UserModel> findAll() {
		String sql = "SELECT * FROM tblUSER";
		return query(sql, new UserMapper());
	}

	@Override
	public UserModel findUserById(long id) {
		String sql = "SELECT * FROM tblUSER WHERE UserID = ?";
		return query(sql, new UserMapper(), id).get(0);
	}

	@Override
	public Long save(UserModel user) {
		String sql = "INSERT INTO tblUSER(UserMail, Username, Pwd," + 
								 "DisplayName, UserRole) VALUES (?,?,?,?,?)";
		return insert(sql, user.getEmail(), user.getUsername(), user.getPassword(), 
									user.getDisplayName(), user.isRole() ? 1 : 0);
	}

	@Override
	public void edit(UserModel user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getTotalItems() {
		String sql = "SELECT count(*) FROM tblUSER";
		return count(sql);
	}

	

}
