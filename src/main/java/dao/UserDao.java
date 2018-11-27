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


    /**
     * 向数据库中插入一个新的用户，插入成功返回true ,失败返回false,传入的userId 为空，系统自动分配
     * @param user 需要注册的用户名
     * @return 是否创建成功
     */
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
    /**
     * 用户登陆时，传入用户名，将返回数据库中存储的密码（真正密码的md5值）
     * @param userName 用户名
     * @return 所对应id的用户密码的md5值
     */
    @Override
    public String getPassWord(String userName) {
        String statement = "select userpassword from users where username = ?";

        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            //若出错返回空值
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

    /**
     * 得到对应用户名用户的全部信息
     * @param userName 用户名
     * @return 存放用户全部信息的Bean
     */
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
        //若出错返回空值
        return user;
    }

    /**
     * 检查存不存在名字相同的用户
     * @param name 用户名
     * @return true 存在用户 false 不存在用户
     */
    @Override
    public boolean isExist(String name) {
        String result = getPassWord(name);
        return result != null && !result.equals("");
    }
}
