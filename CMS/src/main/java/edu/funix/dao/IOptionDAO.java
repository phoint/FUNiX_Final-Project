package edu.funix.dao;

import java.sql.SQLException;

import edu.funix.model.OptionModel;

public interface IOptionDAO {
    public void save(OptionModel options) throws SQLException, Exception;
    
    public OptionModel currentOpt() throws SQLException, Exception;
}
