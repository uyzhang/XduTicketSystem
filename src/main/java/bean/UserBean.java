package bean;


/**
 * 用于记录用户的bean
 */
public class UserBean {
    private int userId;//用户id
    private String userName;//用户名
    private String userPassword;//密码md5值

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
