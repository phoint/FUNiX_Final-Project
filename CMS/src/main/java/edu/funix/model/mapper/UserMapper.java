package edu.funix.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.funix.model.UserModel;

public class UserMapper implements RowMapper<UserModel> {

    @Override
    public UserModel mapRow(ResultSet rs) {
	try {
	    UserModel user = new UserModel();
	    user.setId(rs.getLong("UserID"));
	    user.setEmail(rs.getString("UserMail"));
	    user.setUsername(rs.getString("Username"));
	    user.setDisplayName(rs.getNString("DisplayName"));
	    user.setRegisteredFrom(rs.getString("CreatedFrom"));
	    user.setSocialId(rs.getString("SocialID"));
	    user.setPictureUrl(rs.getString("Avatar"));
	    user.setFailedAttempts(rs.getInt("Failed_attempts"));
	    user.setActive(rs.getInt("Active") == 1 ? true : false);
	    user.setAccountNonLocked(rs.getInt("Acc_non_locked") == 1 ? true : false);
	    user.setLockTime(rs.getTimestamp("Lock_time"));
	    int role = rs.getInt("UserRole");
	    user.setRole(role != 0);
	    
	    return user;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
    }

}
