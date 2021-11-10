package edu.funix.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.funix.model.OptionModel;

public class OptionMapper implements RowMapper<OptionModel> {

    @Override
    public OptionModel mapRow(ResultSet rs) {
	try {
	    OptionModel option = new OptionModel();
	    option.setId(rs.getLong("OptID"));
	    option.setCreatedDate(rs.getDate("CreateDate"));
	    option.setLogoPath(rs.getString("LogoPath"));
	    option.setBannerPath(rs.getString("BannerPath"));
	    return option;
	} catch (SQLException e) {
	    e.printStackTrace();
	    return null;
	}
    }

}
