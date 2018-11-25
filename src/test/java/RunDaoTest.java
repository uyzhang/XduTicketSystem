import bean.RunBean;
import dao.RunDao;
import org.junit.Test;

import java.util.ArrayList;

public class RunDaoTest {

    @Test
    public void getRunsTest() {
        ArrayList<RunBean> beans = new RunDao().getRuns("西安", "上海");
        assert beans.size() == 5;
        for (RunBean bean : beans) {
            System.out.println(bean.getRunId() + " " + bean.getOrigin() + " " + bean.getDestination() + " " + bean.getTime() + " " + bean.getPrice());
        }
        beans = new RunDao().getRuns("南京", "南京");
        assert beans.size() == 0;
    }

    @Test
    public void getRunTest() {
        RunBean runBean = new RunDao().getRun(1);
        assert runBean != null;
        System.out.println(runBean.getRunId() + " " + runBean.getOrigin()
                + " " + runBean.getDestination() + " " + runBean.getPrice() +
                " " + runBean.getTime() + " " + runBean.getTotal());
        runBean = new RunDao().getRun(100);
        assert runBean == null;
    }


    @Test
    public void getAvailableTicketTest() {
        RunDao dao = new RunDao();
        for (int i = 1; i <= 60; i++) {
            System.out.println("id = " + i + ", remain =  " + dao.getAvailableTicket(i));
        }
    }

}
