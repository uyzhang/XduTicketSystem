package gui;

import dao.UserDao;
import util.Md5;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class gui_login extends JFrame implements ActionListener
{
    //主框体的label
    private JLabel name = new JLabel("自动售票系统");
    //账号框
    private JTextField id = new JTextField();
    //密码框
    private JPasswordField pwd = new JPasswordField();
    //登录框的label
    private JLabel l1 = new JLabel("账号:");
    //密码框的label
    private JLabel l2 = new JLabel("密码:");
    //登陆按钮
    private JButton login = new JButton("登录");
    //注册按钮
    private JButton register = new JButton("注册");
//    private request_ r = new request_(2);
    JFrame login_jf;
//    client c;
    Container container;
    config opt = new config();
    String pswmd5 ;
//    response re;

    public gui_login()
    {
        /*
         * 开线程，启动一个dao
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                new UserDao();
            }
        }).start();

        login_jf = new JFrame("TicketSystem");
        container = login_jf.getContentPane();
        //主框体icon设置
        ImageIcon chat_icon = new ImageIcon(opt.chat);
        chat_icon.setImage(chat_icon.getImage().getScaledInstance(40, 40, 10));
        //整个程序的图标设置
        login_jf.setIconImage(chat_icon.getImage().getScaledInstance(40, 40, 10));
        //登录框icon设置
        ImageIcon login_icon = new ImageIcon(opt.login);
        login_icon.setImage(login_icon.getImage().getScaledInstance(25, 25, 10));
        //密码框icon设置
        ImageIcon pwd_icon = new ImageIcon(opt.pwd);
        pwd_icon.setImage(pwd_icon.getImage().getScaledInstance(25, 25, 10));

        name.setBounds(110,5,140,30);
        name.setIcon(chat_icon);

        l1.setBounds(30, 55, 80, 30);
        l1.setIcon(login_icon);

        l2.setBounds(30, 110, 80, 30);
        l2.setIcon(pwd_icon);

        id.setBounds(110, 55, 150, 30);
        pwd.setBounds(110, 110, 150, 30);

        login.setBounds(110, 155, 60, 30);
        login.addActionListener(this);
        register.setBounds(200, 155, 60, 30);
        register.addActionListener(this);

        //加入到主容器中
        container.add(name);
        container.add(l1);
        container.add(l2);
        container.add(login);
        container.add(register);
        container.add(id);
        container.add(pwd);

        vis();
    }
    public void vis()
    {
        //空布局
        container.setLayout(null);
        //背景色
        container.setBackground(Color.white);
        //主窗口是否可视化
        login_jf.setVisible(true);
        //设置在屏幕中间弹出
        login_jf.setLocation(500,300);
        //主窗口大小
        login_jf.setSize(380,240);
        System.out.println("登陆窗口构建成功!");
        //退出方式

        login_jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void actionPerformed ( ActionEvent e )
    {
        if(e.getSource() == login)
        {
            String idinput = id.getText();

            String pswinput = new String(pwd.getPassword());

            if(idinput.length() < 1 || pswinput.length() < 1)
            {
                JOptionPane.showMessageDialog(null, "账号或密码输入错误！", "提示",JOptionPane.ERROR_MESSAGE);
            }
            else {

                 //MD5加密
                try{
                    pswmd5 = Md5.EncoderByMd5(pswinput);
                }catch(Exception login_e){
                    login_e.printStackTrace();
                }
                 //获取psw
                UserDao user = new UserDao();
                String rightpsw = user.getPassWord(idinput);
                //密码正确->成功登陆
                if( pswmd5.equals(rightpsw))
                {
                    login_jf.setVisible(false);
                    gui_use chat_jf = new gui_use(idinput);
                }
                //密码错误
                else
                    JOptionPane.showMessageDialog(null, "账号或密码输入错误！", "提示",JOptionPane.ERROR_MESSAGE);
            }

        }

        if(e.getSource() == register)
        {
            login_jf.setVisible(false);
            gui_register register_jf = new gui_register();
            register_jf.vis();
        }
    }

}


