package dao_interface;

import bean.UserBean;

public interface UserDaoInterface {
    /**
     * 向数据库中插入一个新的用户，插入成功返回true ,失败返回false,传入的userId 为空，系统自动分配
     * @param user 需要注册的用户名
     * @return 是否创建成功
     */
    boolean createUser(UserBean user);

    /**
     * 用户登陆时，传入用户名，将返回数据库中存储的密码（真正密码的md5值）
     * @param userName 用户id
     * @return 所对应id的用户密码的md5值
     */
    String getPassWord(String userName);

    /**
     * 得到对应用户名用户的全部信息
     * @param userName 用户名
     * @return 存放用户全部信息的Bean
     */
    UserBean getUser(String userName);

    /**
     * 检查存不存在名字相同的用户
     * @param name 用户名
     * @return true 存在用户 false 不存在用户
     */
    boolean isExist(String name);
}
