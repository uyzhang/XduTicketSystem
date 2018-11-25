package gui;

import bean.RunBean;
import bean.TradeBean;
import bean.UserBean;
import dao.RunDao;
import dao.TradeDao;
import dao.UserDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class gui_refund extends JFrame implements ActionListener {
    private boolean refundonce = false;
    //主框体的label
    private JLabel name = new JLabel("  退票系统");
    //返回的label
    private JLabel l_quit = new JLabel("返回订票");
    //取消的label
    private JLabel l_refund = new JLabel("取消车票");

    private JLabel l_checkbox = new JLabel("订单号                  起始地                  目的地                  时间");

    //查询按钮
    JCheckBox checkbox[];
    //返回按钮
    private JButton quit = new JButton("返回");
    //退票按钮
    private JButton refund = new JButton("退票");

    JFrame refund_jf;
    gui_use use_jf;

    Container container;
    config opt = new config();
    String id;
    ArrayList<TradeBean> queryTrade = new ArrayList<>();//我我我
    ArrayList<RunBean> getRuns = new ArrayList<>();
    TradeBean tradeBean = new TradeBean();
    JPanel panel;
    RunDao rundao = new RunDao();
    TradeDao tradeDao = new TradeDao();
    int select_refund_ticket = 0;
    int bought_ticket = 0;//以上

    public gui_refund(String s) {
        refund_jf = new JFrame("退票");
        container = refund_jf.getContentPane();
        id = s;


        JPanel panel = new JPanel(new GridLayout(bought_ticket, 1, 10, 10));
        panel.setBackground(Color.white);
        //在文本框上添加滚动条
        JScrollPane jsp = new JScrollPane(panel);
        //设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
        jsp.setBounds(10, 50, 370, 200);
        //默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(l_checkbox);
        add_checkbox(panel);

        ImageIcon main_icon = new ImageIcon(opt.chat);
        main_icon.setImage(main_icon.getImage().getScaledInstance(30, 30, 10));

        ImageIcon quit_icon = new ImageIcon(opt.quit);
        quit_icon.setImage(quit_icon.getImage().getScaledInstance(25, 25, 10));

        ImageIcon refund_icon = new ImageIcon(opt.refund);
        refund_icon.setImage(refund_icon.getImage().getScaledInstance(25, 25, 10));

        name.setBounds(120, 5, 140, 30);
        name.setIcon(main_icon);

        l_quit.setBounds(210, 270, 85, 30);
        l_quit.setIcon(quit_icon);

        l_refund.setBounds(20, 270, 85, 30);
        l_refund.setIcon(refund_icon);


        quit.setBounds(300, 270, 60, 30);
        quit.addActionListener(this);
        refund.setBounds(110, 270, 60, 30);
        refund.addActionListener(this);


        //把滚动条添加到容器里面
//        container.add(panel1);
        container.add(name);
        container.add(l_quit);
        container.add(l_refund);
        container.add(refund);
        container.add(quit);
        container.add(jsp);
        container.setLayout(null);
        container.setBackground(Color.white);

        refund_jf.setLocation(500, 300);
        refund_jf.setSize(400, 350);
        refund_jf.setVisible(true);

        refund_jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quit) {
            refund_jf.setVisible(false);
            use_jf = new gui_use(id);
        }
        if (e.getSource() == refund) {

            select_refund_ticket = 0;
            boolean refund_Success = false;
            bought_ticket = queryTrade.size();

            int orders = 0;


            for (int i = 0; i < bought_ticket; i++) {//在这里遍历所有checkbox，要是有多个被选择，则弹出警告框
                if (checkbox[i].isSelected()) {
                    select_refund_ticket++;
                    orders = i;
                }


            }
            if (select_refund_ticket > 1) {
                JOptionPane.showMessageDialog(null, "请勿同时选择多张车票！", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if (select_refund_ticket < 1) {
                JOptionPane.showMessageDialog(null, "请选择您要退的车票", "提示", JOptionPane.ERROR_MESSAGE);
            }
            if (select_refund_ticket == 1) {//取得只有一个被选中的框的id，返回给数据库。

                tradeBean.setUserId(queryTrade.get(orders).getUserId());
                tradeBean.setTradeId(queryTrade.get(orders).getTradeId());
                tradeBean.setCanceled(queryTrade.get(orders).getCanceled());
                tradeBean.setRunId(queryTrade.get(orders).getRunId());

                if (!tradeBean.getCanceled() && !refundonce) {
                    double price = rundao.getRun(tradeBean.getRunId()).getPrice();
                    tradeDao.cancelTrade(tradeBean);
                    tradeBean.setCanceled(tradeDao.cancelTrade(tradeBean));
                    refundonce = true;
                    JOptionPane.showMessageDialog(null, "退票成功，请取走" + price + "元", "提示", JOptionPane.INFORMATION_MESSAGE);
                } else if (tradeBean.getCanceled() || refundonce) {
                    JOptionPane.showMessageDialog(null, "请不要重复退票", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void add_checkbox(JPanel panel) {
        int id_ = new UserDao().getUser(id).getUserId();
        queryTrade = tradeDao.queryTrade(id_);
        bought_ticket = queryTrade.size();
        RunDao run = new RunDao();
        checkbox = new JCheckBox[bought_ticket];
        for (int i = 0; i < bought_ticket; i++) {
            RunBean getRuns = run.getRun(queryTrade.get(i).getRunId());
            //在这里调用从数据库查询的函数，获得日期。。。。，
            //int userId=queryTrade.get(i).getUserId();
            int id = queryTrade.get(i).getTradeId();
            //String id = "id";
            String begin = getRuns.getOrigin();
            //String begin = "begin";
            String end = getRuns.getDestination();
            //String end = "end";
            String time = getRuns.getTime();
            //String time = "time";
            checkbox[i] = new JCheckBox(id + "                  " + begin + "                  " + end + "                  " + time);
            panel.add(checkbox[i]);
        }
        panel.validate();
        panel.repaint();
        System.out.println("您所购买的车票如下");
    }
}

