package edu.funix.common;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import edu.funix.model.MediaModel;

public interface IMediaService {
    Long save(MediaModel media) throws SQLException, Exception;
    MediaModel getUploadPath(HttpServletRequest request, Part part) throws IOException;
}
