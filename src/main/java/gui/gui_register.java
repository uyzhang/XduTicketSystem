package gui;

import bean.UserBean;
import dao.UserDao;
import util.Md5;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui_register extends JFrame implements ActionListener {
    // 主框体的label
    private JLabel name = new JLabel("   注册账号");
    // 账号框
    private JTextField id = new JTextField();
    // 密码框
    private JPasswordField pwd1 = new JPasswordField();
    private JPasswordField pwd2 = new JPasswordField();
    // 电话框和验证码框
    private JTextField phone = new JTextField();
    private JTextField confirm = new JTextField();

    // label
    private JLabel l1 = new JLabel("账号:");
    private JLabel l2 = new JLabel("输入密码:");
    private JLabel l3 = new JLabel("再次输入密码:");
    private JLabel l4 = new JLabel("手机号:");
    private JLabel l5 = new JLabel("验证码");

    // 按钮
    private JButton register = new JButton("注册");
    private JButton back = new JButton("返回");
    private JButton confirm_ = new JButton("获取");
    //输入的用户名
    private String username = null;
    //输入的第一次密码
    private String psw1 = null;
    //输入的第二次密码
    private String psw2 = null;
    //输入的验证码
    private String confirm_num = null;
    //输入的手机号
    private String phone_num = null;
    //新注册的用户 用于向数据库插入
    private UserBean newuser = new UserBean();
    //MD5加密后的密码
    private String newpswmd5 = null;
    //检查用户名是否存在
    private UserDao check_user_exist = new UserDao();

    JFrame register_jf;
    Container container;
    config opt = new config();
    gui_login login_jf;
    //生成随机验证码
    String confirm_data;
    //验证标记
    boolean flag = false;
    //注册标记
    boolean register_msg = false;

    public gui_register() {
        register_jf = new JFrame("TicketSystem");
        container = register_jf.getContentPane();

        // 框体和组件icon及位置的设置
        ImageIcon chat_icon = new ImageIcon(opt.register);
        chat_icon.setImage(chat_icon.getImage().getScaledInstance(40, 40, 10));

        ImageIcon chat1_icon = new ImageIcon(opt.chat);
        register_jf.setIconImage(chat1_icon.getImage().getScaledInstance(40, 40, 10));

        ImageIcon login_icon = new ImageIcon(opt.login);
        login_icon.setImage(login_icon.getImage().getScaledInstance(25, 25, 10));

        ImageIcon pwd_icon = new ImageIcon(opt.pwd);
        pwd_icon.setImage(pwd_icon.getImage().getScaledInstance(25, 25, 10));

        ImageIcon phone_icon = new ImageIcon(opt.phone);
        phone_icon.setImage(phone_icon.getImage().getScaledInstance(25, 25, 10));

        ImageIcon confirm_icon = new ImageIcon(opt.confirm);
        confirm_icon.setImage(confirm_icon.getImage().getScaledInstance(25, 25, 10));

        name.setBounds(130, 10, 140, 30);
        name.setIcon(chat_icon);

        l1.setBounds(30, 55, 120, 30);
        l2.setBounds(30, 110, 120, 30);
        l3.setBounds(30, 165, 120, 30);
        l4.setBounds(30, 210, 120, 30);
        l5.setBounds(30, 255, 120, 30);
        l1.setIcon(login_icon);
        l2.setIcon(pwd_icon);
        l3.setIcon(pwd_icon);
        l4.setIcon(phone_icon);
        l5.setIcon(confirm_icon);

        id.setBounds(150, 55, 160, 30);
        pwd1.setBounds(150, 110, 160, 30);
        pwd2.setBounds(150, 165, 160, 30);
        phone.setBounds(150, 210, 160, 30);
        confirm.setBounds(220, 255, 90, 30);

        // 添加监听
        register.setBounds(150, 300, 60, 30);
        register.addActionListener(this);
        back.setBounds(250, 300, 60, 30);
        back.addActionListener(this);
        confirm_.setBounds(150, 255, 65, 30);
        confirm_.addActionListener(this);

        // 加入到主容器中
        container.add(name);
        container.add(l1);
        container.add(l2);
        container.add(l3);
        container.add(l4);
        container.add(l5);
        container.add(register);
        container.add(back);
        container.add(confirm_);
        container.add(id);
        container.add(pwd1);
        container.add(pwd2);
        container.add(phone);
        container.add(confirm);

    }
    public void actionPerformed (ActionEvent e )
    {
        //用户点击注册按钮
        if(e.getSource() == register)
        {
            //获取输入的用户名
            username = id.getText();
            //获取第一次输入的密码
            psw1 = String.valueOf(pwd1.getPassword());
            //获取第二次输入的密码
            psw2 = String.valueOf(pwd2.getPassword());
            //获取输入的验证码
            confirm_num = confirm.getText();
            //获取输入的手机号
            phone_num = phone.getText();

            /**
             * flag为判断是否进行手机验证的标志
             * 当没有进行手机验证时进入if
             */
            if(!flag)  //
            {
                JOptionPane.showMessageDialog(null, "请输入验证码！", "提示", JOptionPane.ERROR_MESSAGE);
            }

            /**
             * 判断用户名username的长度
             * 当用户名长度小于1时进入if
             */
            if(username.length() < 1 && flag)
            {
                JOptionPane.showMessageDialog(null, "用户名不能少于1位！", "提示",JOptionPane.ERROR_MESSAGE);

            }

            /**
             * 判断两次输入的密码psw1 psw2 是否一致
             * 当两次密码不一致时进入if
             */
            if(!psw1.equals(psw2) && username.length() >= 1 && flag)
            {
                JOptionPane.showMessageDialog(null, " 两次密码不一致！", "提示",JOptionPane.ERROR_MESSAGE);
            }
            /**
             * 判断密码的长度是否符合
             * 当密码的长度小于1时进入if
             */
            if(psw1.length() < 1 && psw1.equals(psw2) && flag)
            {
                JOptionPane.showMessageDialog(null, "密码不能少于1位！", "提示",JOptionPane.ERROR_MESSAGE);
            }

            /**
             * 判断验证码 confirm_num 的长度是否为6位
             * 当验证码的长度非6位时进入if
             */
            if(confirm_num.length() != 6 && flag && psw1.equals(psw2) && username.length() >= 1 && psw1.length() >= 1)
            {
                JOptionPane.showMessageDialog(null, "请输入正确的验证码！", "提示",JOptionPane.ERROR_MESSAGE);
            }
            /**
             * 判断用户输入的验证码是否正确
             * confirm_num 为用户输入的验证码
             * confirm_data为正确的验证码
             * 当验证码不一致时进入if
             */
            if(confirm_num.length() == 6 && flag && psw1.equals(psw2) && username.length() >= 1 && psw2.length() >= 1 && !confirm_num.equals(confirm_data))
            {
                JOptionPane.showMessageDialog(null, "验证码错误", "提示",JOptionPane.ERROR_MESSAGE);

            /**
             * 判断该用户名是否已经存在
             * 使用UserDao 的 isExist(username)函数
             * return = true ； 该用户名已被占用
             * return = false ； 该用户名可以使用
             * //当用户名已经被占用时进入if
             */

            if (confirm_num.length() == 6 && flag && psw1.equals(psw2) && username.length() >= 1 && psw2.length() >= 1
                    && confirm_num.equals(confirm_data) && check_user_exist.isExist(username)) {
                JOptionPane.showMessageDialog(null, "该用户名已被注册", "提示", JOptionPane.ERROR_MESSAGE);
            }


            /**
             * 符合注册条件
             * 1：验证码的长度等于6位
             * 2：已进行验证
             * 3：两次密码输入一致
             * 4：用户名的长度 >= 1
             * 5：密码的长度 >= 1
             * 6：验证码输入正确
             * 7：该用户名没有被占用
             */
            if(confirm_num.length() == 6 && flag && psw1.equals(psw2) && username.length() >= 1 && psw2.length() >= 1 && confirm_num.equals(confirm_data) && !check_user_exist.isExist(username))
            {
                //将密码进行md5加密
                try{
                    newpswmd5 = Md5.EncoderByMd5(psw1);

                } catch (Exception registeconfiem_e) {
                    registeconfiem_e.printStackTrace();
                }
                //设置newuser的用户名
                newuser.setUserName(username);
                //设置newuser的密码
                newuser.setUserPassword(newpswmd5);

                /**
                 * 向数据库中插入 newuser信息
                 * 对插入数据的结果进行判断并处理
                 */
                try {
                    //向数据库中插入一条新的用户数据，register_msg接收返回值
                    register_msg = check_user_exist.createUser(newuser);

                    //register_msg = true; 向数据库中插入用户成功
                    if(register_msg)
                    {
                        //弹窗提示注册成功
                        JOptionPane.showMessageDialog(null, "注册成功！", "提示",JOptionPane.INFORMATION_MESSAGE);
                        //注册窗口设置为不可见
                        register_jf.setVisible(false);
                        //新建购票窗口，传入该用户名
                        gui_use chat_jf = new gui_use(username);

                    }
                    //register_msg = false; 向数据库中插入用户失败
                    else
                    {
                        //弹窗提示注册失败
                        JOptionPane.showMessageDialog(null, "注册失败", "提示",JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ee) {
                    System.out.print("Register error :");
                    ee.printStackTrace();
                }
            }

        }

        //用户点击返回按钮
        if(e.getSource() == back)
        {
            //注册窗口设为不可见
            register_jf.setVisible(false);
            //新建登陆窗口
            login_jf = new gui_login();
        }

        //用户点击验证按钮
        if(e.getSource() == confirm_)
        {
            /**
             * flag为判断是否进行手机验证的标志
             * 当已进行手机验证时进入if
             * 提醒不要重复验证
             */
            if(flag)
            {
                JOptionPane.showMessageDialog(null, "请不要重复验证！", "提示",JOptionPane.ERROR_MESSAGE);
            }
            //获取输入的手机号
            phone_num = phone.getText();
            /**
             * 验证手机号长度是否正确
             * 当手机号的长度为非11位时进入if
             */
            if(phone_num.length() != 11 && !flag)
            {
                JOptionPane.showMessageDialog(null, "请输入正确的11位手机号！", "提示",JOptionPane.ERROR_MESSAGE);
            }
            /**
             * 符合验证条件
             * 1：手机号长度为11位
             * 2：没有进行验证
             */
            if(phone_num.length() == 11 && !flag)
            {
                //创建Random对象，用于调用Random的函数
                Random random = new Random();
                //生成一个 100000-999999 之间的随机数，并转换为String类型
                //confirm_data为正确的验证码
                confirm_data = String.valueOf(random.nextInt(899999) + 100000);
                System.out.println(confirm_data);
                try{
                    /**
                     * 调用验证类confirm，向用户手机发送手机验证
                     * @param phone_num 用于接受验证短信的手机号
                     * @param confirm_data 向用户发送的验证码
                     */
                    confirm confirm_r = new confirm(phone_num,confirm_data);
                    //手机验证失败，抛出异常，flag仍为false
                    //手机验证成功，将flag设为true
                    flag = true;
                } catch (Exception confiem_e) {
                    confiem_e.printStackTrace();
                }
            }
        }
    }

    public void vis() {
        // 绝对布局
        container.setLayout(null);
        // 背景色
        container.setBackground(Color.white);
        // 主窗口是否可视化
        register_jf.setVisible(true);
        // 设置在屏幕中间弹出
        register_jf.setLocation(500, 300);
        // 主窗口大小
        register_jf.setSize(420, 400);
        System.out.println("注册窗口构建成功!");
        // 退出方式
        register_jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
