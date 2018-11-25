import bean.TradeBean;
import bean.UserBean;
import dao.TradeDao;
import dao.UserDao;
import org.junit.Test;

public class CreateData {
    @Test
    public void madeUser() {
        UserDao dao = new UserDao();
        UserBean bean = new UserBean();
        for (int i = 1; i <= 90; i++) {
            bean.setUserName("user" + i);
            bean.setUserPassword("user" + i);
            dao.createUser(bean);
        }
    }

    @Test
    public void madeData(){
        TradeBean bean = new TradeBean();
        TradeDao dao = new TradeDao();
        for (int i = 1; i <= 60; i++) {
            for (int j = 10; j <= 99; j++) {
                bean.setUserId(j);
                bean.setRunId(i);
                dao.createTrade(bean);
            }
        }
    }

}
