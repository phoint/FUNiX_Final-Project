package edu.funix.common.imp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import edu.funix.common.IMediaService;
import edu.funix.dao.IMediaDAO;
import edu.funix.dao.imp.MediaDAO;
import edu.funix.model.MediaModel;

public class MediaService implements IMediaService {
    private IMediaDAO mediaDAO;
    ResourceBundle domainResource = ResourceBundle.getBundle("domain");

    public MediaService() {
	mediaDAO = new MediaDAO();
    }

    @Override
    public MediaModel getUploadPath(String uploadFolder, String contextPath , Part part) throws IOException {
	MediaModel media = new MediaModel();
	Path uploadPath = Paths.get(uploadFolder);
	String fileName = Path.of(part.getSubmittedFileName()).getFileName().toString();
	if (!Files.exists(uploadPath)) {
	    Files.createDirectories(uploadPath);
	}
	if (fileName != null && !fileName.equals("")) {
	    part.write(Paths.get(uploadPath.toString(), fileName).toString());
	    media.setName(fileName);
	    media.setUrl(domainResource.getString("domain") + contextPath + "/images/" + fileName);
	    return media;
	}
	return null;
    }

    @Override
    public Long save(MediaModel media) throws SQLException, Exception {
	return mediaDAO.save(media);
    }

}
