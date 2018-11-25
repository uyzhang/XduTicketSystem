package dao_interface;

import bean.UserBean;

public interface UserDaoInterface {
    //向数据库中插入一个新的用户，插入成功返回true ,失败返回false,传入的userId 为空，系统自动分配
    boolean createUser(UserBean user);
    //用户登陆时，传入用户名，将返回数据库中存储的密码（真正密码的md5值）
    String getPassWord(String userName);

    //得到用户的全部信息
    UserBean getUser(String userName);

    //检查存不存在名字相同的用户
    boolean isExist(String name);
}
