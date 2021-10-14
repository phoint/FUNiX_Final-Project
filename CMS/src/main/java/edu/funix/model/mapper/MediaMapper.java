package edu.funix.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.funix.model.MediaModel;

public class MediaMapper implements RowMapper<MediaModel> {

	@Override
	public MediaModel mapRow(ResultSet rs) {
		try {
			MediaModel media = new MediaModel();
			media.setId(rs.getLong("MediaID"));
			media.setName(rs.getNString("MediaTitle"));
			media.setUrl(rs.getString("MediaURL"));
			media.setCreatedBy(rs.getLong("UploadBy"));
			media.setCreatedDate(rs.getDate("CreateDate"));
			return media;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	

}
