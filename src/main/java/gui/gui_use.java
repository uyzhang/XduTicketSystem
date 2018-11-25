package gui;

import bean.RunBean;
import bean.TradeBean;
import dao.RunDao;
import dao.TradeDao;
import dao.UserDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.util.List;

public class gui_use extends JFrame implements ActionListener
{
    //主框体的label
    private JLabel name = new JLabel("自动订票系统");
    //始发地
    private JTextField begin = new JTextField();
    //目的地
    private JTextField end = new JTextField();
    //钱
    private JTextField money = new JTextField();
    //始发地的label
    private JLabel l_begin = new JLabel("始发地");
    //目的地的label
    private JLabel l_end = new JLabel("目的地");
    //查询的label
    private JLabel l_query = new JLabel("查询车票");
    //取消的label
    private JLabel l_quit = new JLabel("取消购买");
    //购买的label
    private JLabel l_buy = new JLabel("购买车票");
    //交换的label
    private JLabel l_swap = new JLabel("");
    //交换的label
    private JLabel l_weather = new JLabel("");
    //清空的label
    private JLabel l_refund = new JLabel("取消车票");
    //消息区
    private JLabel l_message = new JLabel("");
    private JLabel l_money = new JLabel("投入现金");

    private JLabel l_checkbox = new JLabel("      车次                        发车时间                           价格                  余票");
    //查询按钮
    private JButton query = new JButton("查询");
    //取消按钮
    private JButton quit = new JButton("取消");
    //购票按钮
    private JButton buy = new JButton("购票");
    private JButton refund = new JButton("退票");

    JTextPane msgArea;
    JScrollPane textScrollPane;

    JFrame use_jf;
    JPanel panel;
    Container container;
    config opt = new config();
    gui_login login_jf;
    gui_refund quit_jf;
    String username;

    JCheckBox checkbox[] ;

    String origin = null;
    String dest = null;
    String origin2 = null;
    String dest2 = null;
    String moneyinput = null; // 获取输入的钱
    int totlemoney = 0;
    ArrayList<RunBean> queryRunBeans = new ArrayList<RunBean>();
    TradeDao tradeDao = new TradeDao();
    TradeBean trade =  new TradeBean();
    RunDao check_ticket = new RunDao();
    List<String> addresses=new ArrayList<>();
    String effective_addresses = ("上海、北京、西安、南京");
    String[] arr_address =  effective_addresses.split("、",4);
    int query_ticket_size = 6;
    int ticket_selected_count = 0;



    public gui_use(String s)
    {
        use_jf = new JFrame("TicketSystem " + s);
        container = use_jf.getContentPane();

        username = s;
        //主框体icon设置
        ImageIcon chat_icon = new ImageIcon(opt.chat);
        chat_icon.setImage(chat_icon.getImage().getScaledInstance(40, 40, 10));
        //整个程序的图标设置
        use_jf.setIconImage(chat_icon.getImage().getScaledInstance(40, 40, 10));

        ImageIcon begin_icon = new ImageIcon(opt.begin);
        begin_icon.setImage(begin_icon.getImage().getScaledInstance(25, 25, 10));

        ImageIcon end_icon = new ImageIcon(opt.end);
        end_icon.setImage(end_icon.getImage().getScaledInstance(25, 25, 10));

        ImageIcon query_icon = new ImageIcon(opt.query);
        query_icon.setImage(query_icon.getImage().getScaledInstance(25, 25, 10));

        ImageIcon buy_icon = new ImageIcon(opt.buy);
        buy_icon.setImage(buy_icon.getImage().getScaledInstance(25, 25, 10));

        ImageIcon quit_icon = new ImageIcon(opt.quit);
        quit_icon.setImage(quit_icon.getImage().getScaledInstance(25, 25, 10));

        ImageIcon time_icon = new ImageIcon(opt.time);
        time_icon.setImage(time_icon.getImage().getScaledInstance(25, 25, 10));

        ImageIcon swap_icon = new ImageIcon(opt.swap);
        swap_icon.setImage(swap_icon.getImage().getScaledInstance(25, 25, 10));

        ImageIcon refund_icon = new ImageIcon(opt.refund);
        refund_icon.setImage(refund_icon.getImage().getScaledInstance(25, 25, 10));

        ImageIcon message_icon = new ImageIcon(opt.message);
        message_icon.setImage(message_icon.getImage().getScaledInstance(25, 25, 10));

        ImageIcon moneyicon = new ImageIcon(opt.money);
        moneyicon.setImage(moneyicon.getImage().getScaledInstance(25, 25, 10));

        /////////////////
        //各种位置的设定
        name.setBounds(180,20,140,30);
        name.setIcon(chat_icon);

        l_begin.setBounds(100, 70, 80, 30);
        l_begin.setIcon(begin_icon);

        l_end.setBounds(310, 70, 80, 30);
        l_end.setIcon(end_icon);

        l_query.setBounds(30, 370, 80, 30);
        l_query.setIcon(query_icon);

        l_refund.setBounds(250, 370, 90, 30);
        l_refund.setIcon(refund_icon);

        l_buy.setBounds(30, 420, 80, 30);
        l_buy.setIcon(buy_icon);

        l_quit.setBounds(250, 420, 80, 30);
        l_quit.setIcon(quit_icon);

        l_swap.setBounds(230, 100, 80, 30);
        l_swap.setIcon(swap_icon);

        l_message.setBounds(50, 200, 80, 30);
        l_message.setIcon(message_icon);

        l_money.setBounds(150, 150, 100, 30);
        l_money.setIcon(moneyicon);



        //滚动选择栏
        panel = new JPanel(new GridLayout(query_ticket_size,1, 10, 10));
        panel.setBackground(Color.white);
        //在文本框上添加滚动条
        JScrollPane jsp = new JScrollPane(panel);
        //设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
        jsp.setBounds(50, 250, 400, 100);
        //默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        jsp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(l_checkbox);



        //文本框以及按钮的位置设定
        begin.setBounds(100, 100, 80, 30);
        end.setBounds(310, 100, 80, 30);
        money.setBounds(260,150,80,30);

        query.setBounds(125, 370, 80, 30);
        query.addActionListener(this);
        refund.setBounds(340, 370, 80, 30);
        refund.addActionListener(this);
        buy.setBounds(125, 420, 80, 30);
        buy.addActionListener(this);
        quit.setBounds(340, 420, 80, 30);
        quit.addActionListener(this);

        //信息显示区域
        msgArea = new JTextPane();

        textScrollPane = new JScrollPane(msgArea);
        textScrollPane.setBounds(100,190,300,50);
        textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //加入到主容器中
        container.add(name);
        container.add(l_begin);
        container.add(l_end);
        container.add(l_query);
        container.add(l_quit);
        container.add(l_buy);
        container.add(l_swap);
        container.add(l_refund);
        container.add(l_money);
        container.add(l_message);
        container.add(query);
        container.add(quit);
        container.add(buy);
        container.add(refund);
        container.add(begin);
        container.add(end);
        container.add(money);

        container.add(textScrollPane);
        container.add(jsp);
        vis();

        //有效地址的设定
        for(int i = 0 ;i < arr_address.length ;i++){
            addresses.add(arr_address[i]);
        }

    }
    
    public void vis()
    {
        //空布局
        container.setLayout(null);
        //背景色
        container.setBackground(Color.white);
        //主窗口是否可视化
        use_jf.setVisible(true);
        msgArea.setText("登陆成功，请购票！\n");
        //设置在屏幕中间弹出
        use_jf.setLocation(500,200);
        //主窗口大小
        use_jf.setSize(480,500);
        System.out.println("订票窗口构建成功!");
        //退出方式

        use_jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void actionPerformed ( ActionEvent e )
    {
        //查询  -》 add_checkbox
        if(e.getSource() == query)
        {

            if(query_ticket_size > 0 )
            {
                panel.removeAll();
                panel.add(l_checkbox);
                msgArea.setCaretPosition(msgArea.getText().length());
            }
            try {
                panel.validate();
                panel.repaint();
            }catch(Exception query_e){
                System.out.println(query_e.toString());
            }

            origin = begin.getText();
            dest = end.getText();

            if(origin.length() == 0 ){
                JOptionPane.showMessageDialog(null, "请输入始发地！", "提示",JOptionPane.ERROR_MESSAGE);
            }
            if(dest.length() == 0 )
            {
                JOptionPane.showMessageDialog(null, "请输入目的地！", "提示",JOptionPane.ERROR_MESSAGE);
            }
            if( origin.length() > 0 && dest.length() > 0 )
            {
                if(check_effective_address(origin) && check_effective_address(dest) && !origin.equals(dest))
                {  // 地址有效
                    StyledDocument querymsg = msgArea.getStyledDocument();
                    try {
                        querymsg.insertString(querymsg.getLength(), "查询 " + origin + " 到 " + dest + " 结果如下: \n", null);
                        querymsg.insertString(querymsg.getLength(),"请勾选您所要购买的车票！\n",null);

                        //将 滚动条 滚至底部
                        msgArea.setCaretPosition(msgArea.getText().length());

                    } catch (BadLocationException buy_msg_e) {
                        buy_msg_e.printStackTrace();
                    }
                    add_checkbox(panel);
                //天气控件
                    weather w = new weather(end.getText());
                    String path = "src\\main\\java\\icon\\" + w.get() + ".png";
                    System.out.println(path);
                    ImageIcon weather_icon = new ImageIcon(path);
                    weather_icon.setImage(weather_icon.getImage().getScaledInstance(50, 50, 10));
                    l_weather.setBounds(350, 20, 60, 30);
                    l_weather.setIcon(weather_icon);
                    container.add(l_weather);

                    //面板刷新
                    container.validate();
                    container.repaint();
                    panel.validate();
                    panel.repaint();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "请输入有效地址！", "提示",JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        //退出  finished
        if(e.getSource() == quit)
        {
            use_jf.setVisible(false);
            login_jf = new gui_login();
        }

        //买票  un
        if(e.getSource() == buy)
        {
            int ticket = 0;
            origin2 = begin.getText();
            dest2 = end.getText();
            //地址无效
            if (!check_effective_address(origin2) || !check_effective_address(dest2) || !(dest.equals(dest2)))
            {
                JOptionPane.showMessageDialog(null, "请输入有效地址！", "提示", JOptionPane.ERROR_MESSAGE);
            }

            //判断在购票时是否更改了始发地或目的地
            if (check_effective_address(origin2) && check_effective_address(dest2) && (!origin.equals(origin2) ))
            {
                JOptionPane.showMessageDialog(null, "请重新查询！", "提示", JOptionPane.ERROR_MESSAGE);
            }

            if (check_effective_address(origin2) && check_effective_address(dest2) && origin.equals(origin2) && dest.equals(dest2))
            {
                //检查勾选了几张车票
                ticket_selected_count = 0;
                for (int i = 0; i < query_ticket_size; i++) {
                    if (checkbox[i].isSelected()) {
                        ticket_selected_count++;
                        ticket = i;
                    }
                }
                //判断是否只购买了一张
                if (ticket_selected_count > 1) {
                    JOptionPane.showMessageDialog(null, "请勿同时购买多张车票！", "提示", JOptionPane.ERROR_MESSAGE);
                }

                if (ticket_selected_count < 1) {
                    JOptionPane.showMessageDialog(null, "请选择您要购买的车票！", "提示", JOptionPane.ERROR_MESSAGE);
                }
                if (ticket_selected_count == 1)
                {

                    //查询库中 该票 是否还有余票
                    if(check_ticket.getAvailableTicket(queryRunBeans.get(ticket).getRunId()) > 0)
                    {

                        //输入有效金额
                        moneyinput = money.getText();
                        if (moneyinput == null || moneyinput.length() <= 0) {
                            JOptionPane.showMessageDialog(null, "请输入有效金额！", "提示", JOptionPane.ERROR_MESSAGE);
                        }
                        //判断String 的 moneyinput 是否为合格的 int 类型
                        else if (moneyinput.matches("[1-9]\\d*")) {

                            totlemoney = Integer.parseInt(moneyinput);

                            //如果输入的钱够买票
                            if (totlemoney >= queryRunBeans.get(ticket).getPrice())
                            {
                                boolean createTradeSuccess = false;
                                trade.setRunId(queryRunBeans.get(ticket).getRunId());
                                trade.setUserId(new UserDao().getUser(username).getUserId());
                                try {
                                    createTradeSuccess = tradeDao.createTrade(trade);
                                } catch (Exception trade_e) {
                                    trade_e.printStackTrace();
                                }
                                //成功 createTrade 后 进行
                                StyledDocument document = msgArea.getStyledDocument();
                                if (createTradeSuccess) {

                                    try {
                                        document.insertString(document.getLength(), "成功购买 第 " + queryRunBeans.get(ticket).getRunId() + " 次 列车\n", null);

                                    } catch (BadLocationException buy_msg_e) {
                                        buy_msg_e.printStackTrace();
                                    }

                                        JOptionPane.showMessageDialog(null, "购票成功,请取出余额！", "提示", JOptionPane.INFORMATION_MESSAGE);
                                        money.setText(((int)(totlemoney - queryRunBeans.get(ticket).getPrice())) + "");
                                }
                                else{
                                    try {
                                        document.insertString(document.getLength(), "很抱歉，购票失败!\n", null);
                                    } catch (BadLocationException buy_faild_e) {
                                        buy_faild_e.printStackTrace();
                                    }

                                }
                            }
                            else {
                                JOptionPane.showMessageDialog(null, "金额不足，请取出！", "提示", JOptionPane.ERROR_MESSAGE);
                                money.setText(moneyinput);
                            }
                        }
                        else  // moneyinput.length() > 0  && !moneyinput.matches("[1-9]\\d*
                        {
                            JOptionPane.showMessageDialog(null, "请输入有效金额！", "提示", JOptionPane.ERROR_MESSAGE);
                        }
                    } //是否还有余票
                    else
                    {
                        JOptionPane.showMessageDialog(null, "该车次尚无余票！", "提示", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }

        //退票
        if(e.getSource() == refund)
        {
            use_jf.setVisible(false);
            quit_jf = new gui_refund(username);

        }
    }


    private void add_checkbox(JPanel panel)
    {
        queryRunBeans = new RunDao().getRuns(origin,dest);
        query_ticket_size = queryRunBeans.size();
        checkbox = new JCheckBox[query_ticket_size];
        if(query_ticket_size <= 0 )
        {
            StyledDocument querymsg = msgArea.getStyledDocument();
            try {
                querymsg.insertString(querymsg.getLength(), "抱歉，暂无该路线车票... \n", null);
            } catch (BadLocationException buy_msg_e) {
                buy_msg_e.printStackTrace();
            }
        }
        else
        {
            for (int i = 0; i < query_ticket_size; i++) {
                //在这里调用从数据库查询的函数，获得日期。。。。，然后new出来checkbox对象，加进去。
                int runid = queryRunBeans.get(i).getRunId();
                String time = queryRunBeans.get(i).getTime();

                //余票
                int remain = new RunDao().getAvailableTicket(runid);

                double money = queryRunBeans.get(i).getPrice();
                if (runid < 10) {
                    checkbox[i] = new JCheckBox(runid + "                         " + time + "                      " + money + "                    " + remain);
                }
                else{
                    checkbox[i] = new JCheckBox(runid + "                       " + time + "                      " + money + "                    " + remain);
                }

                panel.add(checkbox[i]);
            }

            //刷新panel 窗口
            panel.validate();
            panel.repaint();
            System.out.println("查询库中该路线信息");
        }

    }

    private boolean check_effective_address(String address)
    {
        return addresses.contains(address);
    }


}
