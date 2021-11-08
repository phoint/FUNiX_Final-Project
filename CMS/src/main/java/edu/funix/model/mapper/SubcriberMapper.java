package edu.funix.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.funix.model.SubcriberModel;

public class SubcriberMapper implements RowMapper<SubcriberModel> {

    @Override
    public SubcriberModel mapRow(ResultSet rs) {
	try {
	    SubcriberModel subcriber = new SubcriberModel();
	    subcriber.setId(rs.getLong("UserID"));
	    subcriber.setEmail(rs.getString("UserMail"));
	    subcriber.setUsername(rs.getString("Username"));
	    subcriber.setDisplayName(rs.getNString("DisplayName"));
	    subcriber.setRegisteredFrom(rs.getString("CreatedFrom"));
	    subcriber.setSocialId(rs.getString("SocialID"));
	    subcriber.setPictureUrl(rs.getString("Avatar"));
	    subcriber.setActive(rs.getInt("Active") == 1 ? true : false);
	    subcriber.setFailedAttempts(rs.getInt("Failed_attempts"));
	    subcriber.setAccountNonLocked(rs.getInt("Acc_non_locked") == 1 ? true : false);
	    subcriber.setLockTime(rs.getTimestamp("Lock_time"));
	    return subcriber;
	} catch (SQLException e) {
	    return null;
	}
    }
}
