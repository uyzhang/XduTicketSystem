package dao_interface;

import bean.RunBean;

import java.util.ArrayList;

public interface RunDaoInterface {
    //返回从起点到终点的所有有效车次信息,若没有符合条件的
    ArrayList<RunBean> getRuns(String origin, String destination);

    //得到所给id的车次信息，若没有这个班次，则返回null
    RunBean getRun(int runId);


    //根据id返回该班次剩余的车票数量,若不存在这个班次，则返回0
    int getAvailableTicket(int id);
}
