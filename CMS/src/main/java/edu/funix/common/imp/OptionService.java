package edu.funix.common.imp;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import edu.funix.common.IOptionService;
import edu.funix.dao.IOptionDAO;
import edu.funix.dao.imp.OptionDAO;
import edu.funix.model.OptionModel;

public class OptionService implements IOptionService {
    private IOptionDAO optionDAO;

    
    
    public OptionService() {
	optionDAO = new OptionDAO();
    }

    @Override
    public void save(OptionModel option) throws SQLException, Exception {
	optionDAO.save(option);
    }

    @Override
    public OptionModel currentOpt() throws SQLException, Exception {
	return optionDAO.currentOpt();
    }
    
    @Override
    public void pushParam(HttpServletRequest request) throws SQLException, Exception {
        ServletContext context = request.getServletContext();
        context.setAttribute("webOpt", optionDAO.currentOpt());
        
    }
}
