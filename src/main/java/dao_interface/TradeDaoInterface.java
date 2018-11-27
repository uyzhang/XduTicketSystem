package dao_interface;

import bean.TradeBean;

import java.util.ArrayList;

public interface TradeDaoInterface {
    /**
     * 创建交易,成功返回true，失败返回false
     * @param trade 需要创建的交易的信息
     * @return true 交易成功，false 交易失败
     */
    boolean createTrade(TradeBean trade);

    /**
     * 查询交易,若有错无或没有购票记录，则返回的列表长度为0
     * @param userId 需要查询所有交易信息的用户id
     * @return 对应用户的交易列表，
     *         若查询错误或用户没有购票信息，返回的列表长度为0
     */
    ArrayList<TradeBean> queryTrade(int userId);

    /**
     * 1.用户退票，取消订单,成功返回true，失败返回false
     * 2.注意退票前，一定要检查所选中的trade  的 cancel 位是否为 0 ，不为0 ，请提示已经退票，
     * 3.不满足2，不得调用此函数，否则会重复退票
     * @param trade 需要取消的交易信息
     * @return 是否退票成功 true false
     */
    boolean cancelTrade(TradeBean trade);

}
