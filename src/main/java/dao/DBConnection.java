package dao;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    //使用单例模式，用于得到一个数据库连接
    //直到这个程序结束，这个连接才会关闭，否则会一直保持连接

    //远程数据库地址
    private static String IP_ADDRESS = "159.65.109.78";
    //远程数据库端口
    private static String PORT_NUM = "3306";
    //数据库用户名
    private static String USER_NAME = "ticketsys";
    //数据库密码
    private static String PASSWORD = "970819";
    //使用的数据库的名称
    private static String DB_NAME = "ticketsys";
    //连接数据库的url
    private static String url = "jdbc:mysql://localhost:33102/ticketsys?useUnicode=true&characterEncoding=utf8";
    //共享的全局变量
    private static Connection connection = null;

    /**
     * 得到数据库连接的函数，使用单例模式
     * @return 建立的数据库连接
     */
    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        //数据库使用ssl加密，此处配置ssl
        connectSession();
        //加载jdbc驱动并得到一个数据库连接
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, USER_NAME, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection(){
        try {
            connection.close();
            connection = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * 建立ssl连接，映射远程3306端口到本地33102端口
     */
    private static void connectSession() {
        String user = "root";//SSH连接用户名
        String password = "z19970819y!";//SSH连接密码
        String host = "159.65.109.78";//SSH服务器
        int port = 22;//SSH访问端口
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            System.out.println(session.getServerVersion());//这里打印SSH服务器版本信息
            int assinged_port = session.setPortForwardingL(33102, IP_ADDRESS, 3306);
            System.out.println("localhost:" + assinged_port + " -> " + IP_ADDRESS + ":" + PORT_NUM);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
