package edu.funix.common;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import edu.funix.model.OptionModel;

public interface IOptionService {
    void save(OptionModel option) throws SQLException, Exception;
    
    OptionModel currentOpt() throws SQLException, Exception;
    
    void pushParam(HttpServletRequest request) throws SQLException, Exception;
}
