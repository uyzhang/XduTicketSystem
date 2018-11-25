package dao;

import bean.UserBean;
import dao_interface.UserDaoInterface;

import java.sql.*;

public class UserDao implements UserDaoInterface {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public UserDao() {
        connection = DBConnection.getConnection();
    }



    @Override
    public boolean createUser(UserBean user) {
        String statement = "insert into users(username, userpassword) values(?,?)";
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getUserPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException ignored) {

            }
        }
        return true;
    }

    @Override
    public String getPassWord(String userName) {
        String statement = "select userpassword from users where username = ?";

        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                //TODO 标签从0还是1
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            return "";
        }finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException ignored) {
            }
        }
        return null;
    }

    @Override
    public UserBean getUser(String userName) {
        UserBean user = new UserBean();
        String statement = "select * from users where username = ?";
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setUserId(resultSet.getInt("userid"));
                user.setUserName(resultSet.getString("username"));
                user.setUserPassword(resultSet.getString("userpassword"));
            }
            else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (Exception ignored) {

            }
        }
        return user;
    }

    @Override
    public boolean isExist(String name) {
        String result = getPassWord(name);
        return result != null && !result.equals("");
    }
}
