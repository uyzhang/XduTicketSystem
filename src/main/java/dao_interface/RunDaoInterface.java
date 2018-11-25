package dao_interface;

import bean.RunBean;

import java.util.ArrayList;

public interface RunDaoInterface {
    /**
     * 返回从起点到终点的所有有效车次信息,若没有符合条件的，返回一个空的List
     * @param origin 起点
     * @param destination 终点
     * @return 符合条件的车次Bean列表
     */
    ArrayList<RunBean> getRuns(String origin, String destination);

    /**
     * 用于得到特定车次的所有信息
     * @param runId 车次
     * @return 对应车次的所有信息，若没有对应车次，返回空
     */
    RunBean getRun(int runId);


    /**
     * 得到特定唱车次的余票数量
     * @param id 车次
     * @return 所查询车次的余票数量，若出错，返回0，没有余票的时候也返回0
     */
    int getAvailableTicket(int id);
}
