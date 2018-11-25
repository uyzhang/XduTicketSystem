package dao_interface;

import bean.TradeBean;

import java.util.ArrayList;

public interface TradeDaoInterface {
    //创建交易,成功返回true，失败返回false
    boolean createTrade(TradeBean trade);

    //查询交易,若有错无或没有购票记录，则返回的列表长度为0
    ArrayList<TradeBean> queryTrade(int userId);

    //用户退票，取消订单,成功返回true，失败返回false
    //注意退票前，一定要检查所选中的trade  的 cancel 位是否为 0 ，不为0 ，请提示已经退票，
    //不得调用下列函数，否则会重复退票
    boolean cancelTrade(TradeBean trade);

}
