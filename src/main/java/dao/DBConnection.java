package dao;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    //使用单例模式

    private static String IP_ADDRESS = "159.65.109.78";
    private static String PORT_NUM = "3306";
    private static String USER_NAME = "ticketsys";
    private static String PASSWORD = "970819";
    private static String DB_NAME = "ticketsys";
    private static String url = "jdbc:mysql://localhost:33102/ticketsys?useUnicode=true&characterEncoding=utf8";
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        connectSession();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, USER_NAME, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void closeConnection(){
        try {
            connection.close();
            connection = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
