package edu.funix.dao.imp;

import java.sql.SQLException;
import java.util.List;

import edu.funix.dao.IOptionDAO;
import edu.funix.model.OptionModel;
import edu.funix.model.mapper.OptionMapper;

public class OptionDAO extends AbstractDAO<OptionModel> implements IOptionDAO  {

    @Override
    public void save(OptionModel options) throws SQLException, Exception {
	String sql = "INSERT INTO tblOPTION (LogoPath, BannerPath) VALUES (?,?)";
	insert(sql, options.getLogoPath(), options.getBannerPath());
    }

    @Override
    public OptionModel currentOpt() throws SQLException, Exception {
	String sql = "SELECT TOP(1) * FROM tblOPTION ORDER BY OptID DESC";
	List<OptionModel> option = query(sql, new OptionMapper());
	return option.isEmpty() ? null : option.get(0);
    }

}
