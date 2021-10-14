package edu.funix.dao;

import java.sql.SQLException;

import edu.funix.model.MediaModel;

public interface IMediaDAO {
    Long save(MediaModel media) throws SQLException, Exception;
    MediaModel findById(long id) throws SQLException, Exception;
}
