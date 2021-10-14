package edu.funix.dao.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.dao.IMediaDAO;
import edu.funix.model.MediaModel;
import edu.funix.model.mapper.MediaMapper;

public class MediaDAO extends AbstractDAO<MediaModel> implements IMediaDAO{

    @Override
    public Long save(MediaModel media) throws SQLException, Exception {
	StringBuilder sql = new StringBuilder("INSERT INTO tblMEDIA ");
	sql.append("(MediaTitle, MediaURL, UploadBy) ");
	sql.append("VALUES (?, ?, ?)");
	return insert(sql.toString(), media.getName(), media.getUrl(), media.getCreatedBy());
    }

    @Override
    public MediaModel findById(long id) throws SQLException, Exception {
	String sql = "SELECT * FROM tblMEDIA WHERE MediaID = ?";
	List<MediaModel> media = query(sql, new MediaMapper(), id);
	return (media.isEmpty() ? null : media.get(0));
    }

}
