import bean.TradeBean;
import bean.UserBean;
import dao.TradeDao;
import dao.UserDao;
import org.junit.Test;

import java.util.ArrayList;

public class TradeDaoTest {

    @Test
    public void CreateTradeTest() {
        TradeBean bean = new TradeBean();
        TradeDao dao = new TradeDao();
        bean.setUserId(1);
        bean.setRunId(13);
        assert dao.createTrade(bean);
        bean.setUserId(1000);
        bean.setRunId(100);
        assert !dao.createTrade(bean);
        bean.setRunId(13);
        bean.setUserId(1000);
        assert !dao.createTrade(bean);
        bean.setRunId(1000);
        bean.setUserId(1000);
        assert !dao.createTrade(bean);
    }

    @Test
    public void queryTradeTest() {
        TradeDao dao = new TradeDao();
        ArrayList<TradeBean> beans = dao.queryTrade(1);
        assert beans.size() != 0;
        System.out.println(beans.size());
        beans = dao.queryTrade(1000);
        assert beans.size() == 0;
        System.out.println(beans.size());
        beans = dao.queryTrade(90);
        assert beans.size() != 0;
        System.out.println(beans.size());
    }

    @Test
    public void CancelTradeTest() {
        TradeDao dao = new TradeDao();
        TradeBean bean = new TradeBean();
        bean.setTradeId(5401);
        boolean res = dao.cancelTrade(bean);
        assert res;
        bean.setTradeId(10086);
        res = dao.cancelTrade(bean);
        assert !res;
    }


}
