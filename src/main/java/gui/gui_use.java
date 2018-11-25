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
    //获取始发地
    String origin = null;
    //获取目的地
    String dest = null;
    //二次获取始发地
    String origin2 = null;
    //二次获取目的地
    String dest2 = null;
    // 获取输入的钱
    String moneyinput = null;
    //存放总钱数 用于计算余额
    int totlemoney = 0;
    //存放查询到的所有车次信息
    ArrayList<RunBean> queryRunBeans = new ArrayList<RunBean>();
    //用于查询trade信息
    TradeDao tradeDao = new TradeDao();
    //用于新建trade信息
    TradeBean trade =  new TradeBean();
    //用于查询run是否有余票
    RunDao check_ticket = new RunDao();
    //存储有效地址
    List<String> addresses=new ArrayList<>();
    //全部有效地址
    String effective_addresses = ("上海、北京、西安、南京");
    //分割有效地址并进行存储
    String[] arr_address =  effective_addresses.split("、",4);
    //查询到的车次数
    int query_ticket_size = 6;
    //统计勾选的车票数
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
        //用户点击查询按钮
        if(e.getSource() == query)
        {
            /**
             * 进行多次查询时
             * 需要重新绘制panel 查询窗口显示信息
             */
            if(query_ticket_size > 0 )
            {
                //删除panel中的全部内容
                panel.removeAll();
                //向panel中加入查询窗口表头
                panel.add(l_checkbox);
                //msgArea区域滚动条调至最后一条消息处
                msgArea.setCaretPosition(msgArea.getText().length());
            }
            try {
                //确保panel组件具有有效的布局
                panel.validate();
                //重绘panel
                panel.repaint();
            }catch(Exception query_e){
                System.out.println(query_e.toString());
            }

            //获取用户输入的始发地
            origin = begin.getText();
            //获取用户输入的目的地
            dest = end.getText();
            /**
             * 判断始发地origin 输入是否为空
             * 当始发地的长度为0时进入if
             * 并进行弹窗警告
             */
            if(origin.length() == 0 ){
                JOptionPane.showMessageDialog(null, "请输入始发地！", "提示",JOptionPane.ERROR_MESSAGE);
            }
            /**
             * 判断目的地dest 输入是否为空
             * 当目的地的长度为0时进入if
             * 并进行弹窗提示
             */
            if(dest.length() == 0 )
            {
                JOptionPane.showMessageDialog(null, "请输入目的地！", "提示",JOptionPane.ERROR_MESSAGE);
            }

            /**
             * 判断 始发地和目的地的值
             * 1：始发地origin长度 大于0
             * 2：目的地dest长度 大于0
             */
            if( origin.length() > 0 && dest.length() > 0 )
            {
                /**
                 * 判断 始发地和目的地 是否为有效地址
                 * 判断 始发地和目的地 是否为相同地址
                 * 1：始发地origin 为有效地址
                 * 2：目的地dest 为有效地址
                 * 3:origin dest  不相同
                 */
                if(check_effective_address(origin) && check_effective_address(dest) && !origin.equals(dest))
                {
                    //获取与编辑器窗格msgArea相关的文档
                    StyledDocument querymsg = msgArea.getStyledDocument();
                    try {
                        //向msgArea中插入内容
                        querymsg.insertString(querymsg.getLength(), "查询 " + origin + " 到 " + dest + " 结果如下: \n", null);
                        querymsg.insertString(querymsg.getLength(),"请勾选您所要购买的车票！\n",null);

                        //将 滚动条 滚至msgArea消息底部
                        msgArea.setCaretPosition(msgArea.getText().length());

                    } catch (BadLocationException buy_msg_e) {
                        buy_msg_e.printStackTrace();
                    }
                    //向查询窗口中加入车票信息
                    add_checkbox(panel);
                    //天气控件，查询目的地的天气
                    weather w = new weather(end.getText());
                    //获取目的地天气的图标地址
                    String path = "src\\main\\java\\icon\\" + w.get() + ".png";
                    System.out.println(path);
                    //创建Icon组件
                    ImageIcon weather_icon = new ImageIcon(path);
                    //设置天气icon的相关属性并向窗口中加入天气图标
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
                /**
                 * 1：始发地或目的地不是有效地址
                 * 2：始发地和目的地为相同地址
                 */
                else
                {
                    JOptionPane.showMessageDialog(null, "请输入有效地址！", "提示",JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        //用户点击退出窗口
        if(e.getSource() == quit)
        {
            //购票窗口设为不可见
            use_jf.setVisible(false);
            //新建登陆窗口
            login_jf = new gui_login();
        }

        //用户点击购票按钮
        if(e.getSource() == buy)
        {
            //将要购买的车票在查询结果中的位置
            int ticket = 0;
            //二次获取始发地
            origin2 = begin.getText();
            //二次获取目的地
            dest2 = end.getText();
            /**
             * 二次判断输入的地址是否为有效地址
             * 判断地址是否相同
             */
            if (!check_effective_address(origin2) || !check_effective_address(dest2) || (origin2.equals(dest2)))
            {
                JOptionPane.showMessageDialog(null, "请输入有效地址！", "提示", JOptionPane.ERROR_MESSAGE);
            }
            /**
             * 判断在购票时是否更改了始发地或目的地
             * origin 和 origin1 是否相同
             * dest 和 dest2 是否相同
             */
            if ((!origin.equals(origin2) || !(dest.equals(dest2) )))
            {
                JOptionPane.showMessageDialog(null, "请重新查询！", "提示", JOptionPane.ERROR_MESSAGE);
            }
            /**
             * 符合购票条件
             * 始发地和目的地为有效地址
             * 始发地和目的地与查询时到的信息一致
             */
            if (check_effective_address(origin2) && check_effective_address(dest2) && origin.equals(origin2) && dest.equals(dest2))
            {
                //检查勾选了几张车票
                ticket_selected_count = 0;
                //遍历所有的checkbox，查看哪张票被勾选了
                for (int i = 0; i < query_ticket_size; i++) {
                    if (checkbox[i].isSelected()) {
                        //勾选车票数量++
                        ticket_selected_count++;
                        //只记录勾选的最后一张车票
                        ticket = i;
                    }
                }
                //判断勾选车票数量是否多于1
                if (ticket_selected_count > 1) {
                    JOptionPane.showMessageDialog(null, "请勿同时购买多张车票！", "提示", JOptionPane.ERROR_MESSAGE);
                }
                //判断勾选车票数量是否少于1
                if (ticket_selected_count < 1) {
                    JOptionPane.showMessageDialog(null, "请选择您要购买的车票！", "提示", JOptionPane.ERROR_MESSAGE);
                }
                //勾选车票数量恰好等于1
                if (ticket_selected_count == 1)
                {
                    /**查询库中 勾选的该票 是否还有余票
                     * 余票大于1时进入if
                     */
                    if(check_ticket.getAvailableTicket(queryRunBeans.get(ticket).getRunId()) > 0)
                    {
                        //获取用户投入的money
                        moneyinput = money.getText();
                        //money值为空或者长度小于0时弹窗警告
                        if (moneyinput == null || moneyinput.length() <= 0) {
                            JOptionPane.showMessageDialog(null, "请输入有效金额！", "提示", JOptionPane.ERROR_MESSAGE);
                        }
                        //判断String 的 moneyinput 是否为合格的 int 类型
                        else if (moneyinput.matches("[1-9]\\d*")) {
                            //存储总金额
                            totlemoney = Integer.parseInt(moneyinput);

                            /**
                             * 判断金额是否足够
                             * 如果输入的钱够买票进入if
                             */
                            if (totlemoney >= queryRunBeans.get(ticket).getPrice())
                            {
                                //用于判断新建trade是否成功
                                boolean createTradeSuccess = false;
                                //交易信息设置车票id
                                trade.setRunId(queryRunBeans.get(ticket).getRunId());
                                //交易信息设置用户id
                                trade.setUserId(new UserDao().getUser(username).getUserId());
                                try {
                                    //向数据库中插入该条trade信息
                                    createTradeSuccess = tradeDao.createTrade(trade);
                                } catch (Exception trade_e) {
                                    trade_e.printStackTrace();
                                }
                                //向msgArea中加入消息
                                StyledDocument document = msgArea.getStyledDocument();
                                //成功 createTrade 后 进行
                                if (createTradeSuccess) {

                                    try {
                                        document.insertString(document.getLength(), "成功购买 第 " + queryRunBeans.get(ticket).getRunId() + " 次 列车\n", null);

                                    } catch (BadLocationException buy_msg_e) {
                                        buy_msg_e.printStackTrace();
                                    }
                                    //弹窗提示取出余额
                                    JOptionPane.showMessageDialog(null, "购票成功,请取出余额！", "提示", JOptionPane.INFORMATION_MESSAGE);
                                    //将money区域的值设置为余额
                                    money.setText(((int)(totlemoney - queryRunBeans.get(ticket).getPrice())) + "");
                                }
                                // createTrade 失败
                                else{
                                    try {
                                        document.insertString(document.getLength(), "很抱歉，购票失败!\n", null);
                                    } catch (BadLocationException buy_faild_e) {
                                        buy_faild_e.printStackTrace();
                                    }

                                }
                            }
                            //提示输入的金额不足以购票
                            else {
                                JOptionPane.showMessageDialog(null, "金额不足，请取出！", "提示", JOptionPane.ERROR_MESSAGE);
                                money.setText(moneyinput);
                            }
                        }
                        //如果用户输入的金额不是正确的整数
                        else  // moneyinput.length() > 0  && !moneyinput.matches("[1-9]\\d*
                        {
                            JOptionPane.showMessageDialog(null, "请输入有效金额！", "提示", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    //如果该车次没有余票
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
            //购票窗口设为不可见
            use_jf.setVisible(false);
            //新建退票窗口
            quit_jf = new gui_refund(username);

        }
    }


    private void add_checkbox(JPanel panel)
    {
        //查询符合origin和dest的所有车次信息
        queryRunBeans = new RunDao().getRuns(origin,dest);
        //符合信息的车次数
        query_ticket_size = queryRunBeans.size();
        //新建相应数量的checkbox，用于勾选车票
        checkbox = new JCheckBox[query_ticket_size];
        /**
         * 查询到的车次数<=0
         */
        if(query_ticket_size <= 0 )
        {
            //向msgArea 加入消息
            StyledDocument querymsg = msgArea.getStyledDocument();
            try {
                querymsg.insertString(querymsg.getLength(), "抱歉，暂无该路线车票... \n", null);
            } catch (BadLocationException buy_msg_e) {
                buy_msg_e.printStackTrace();
            }
        }
        /**
         * 查询到的车次数>0
         */
        else
        {
            //遍历 向panel中加车次信息
            for (int i = 0; i < query_ticket_size; i++) {
                //调用从数据库查询的函数
                //获取车次号
                int runid = queryRunBeans.get(i).getRunId();
                //获取车次时间
                String time = queryRunBeans.get(i).getTime();

                //获取余票
                int remain = new RunDao().getAvailableTicket(runid);
                //获取车票价格
                double money = queryRunBeans.get(i).getPrice();
                //在显示时更加整齐，二者相差一个空格
                //车次id 为个位数的
                if (runid < 10) {
                    checkbox[i] = new JCheckBox(runid + "                         " + time + "                      " + money + "                    " + remain);
                }
                //车次id 为二位数的
                else{
                    checkbox[i] = new JCheckBox(runid + "                       " + time + "                      " + money + "                    " + remain);
                }
                //向panel中加入该车次
                panel.add(checkbox[i]);
            }

            //刷新panel 窗口
            panel.validate();
            panel.repaint();
            System.out.println("查询库中该路线信息");
        }

    }
    //判断传入的address是否在有效地址addresses中
    private boolean check_effective_address(String address)
    {
        return addresses.contains(address);
    }


}
