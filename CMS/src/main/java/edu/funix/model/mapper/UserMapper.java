package edu.funix.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.funix.model.UserModel;

public class UserMapper implements RowMapper<UserModel> {

	@Override
	public UserModel mapRow(ResultSet rs) {
		try {
			UserModel user = new UserModel();
			user.setEmail(rs.getString("UserMail"));
			user.setUsername(rs.getString("Username"));
			user.setDisplayName(rs.getNString("DisplayName"));
			int role = rs.getInt("UserRole");
			user.setRole(role != 0);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
