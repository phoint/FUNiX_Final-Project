package edu.funix.common;

import java.sql.SQLException;
import java.util.List;

import edu.funix.domain.ChangePasswordForm;
import edu.funix.model.PageModel;
import edu.funix.model.UserModel;

public interface IUserService {
    List<UserModel> findAll() throws SQLException, Exception;

    List<UserModel> findAll(PageModel page) throws SQLException, Exception;

    UserModel findUserById(long id) throws SQLException, Exception;

    Long save(UserModel user) throws SQLException, Exception;

    void edit(UserModel user) throws SQLException, Exception;

    void delete(long id);

    void permanentDelete(long id) throws SQLException, Exception;

    Long getTotalItems() throws SQLException, Exception;

    UserModel checkLogin(String username, String password) throws SQLException, Exception;
    
    void changePassword(ChangePasswordForm newPwd) throws SQLException, Exception;
}
