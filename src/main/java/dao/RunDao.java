package dao;

import bean.RunBean;
import dao_interface.RunDaoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 这个类实现了RunDaoInterface接口
 * 每个函数的具体作用在RunDaoInterface中说明
 * 遵循以下原则：
 *     若返回的是列表，出错时，返回的列表长度为0
 *     若返回值一个单一对象，返回空
 */
public class RunDao implements RunDaoInterface {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;


    /**
     * 创建Dao的构造函数
     */
    public RunDao() {
        connection = DBConnection.getConnection();
    }


    /**
     * 返回从起点到终点的所有有效车次信息,若没有符合条件的，返回一个空的List
     * @param origin 起点
     * @param destination 终点
     * @return 符合条件的车次Bean列表
     */
    @Override
    public ArrayList<RunBean> getRuns(String origin, String destination) {
        ArrayList<RunBean> runBeans = new ArrayList<>();
        //数据库语句
        String statement = "select * from runs where origin = ? and dest = ? ";
        //数据库操作
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, origin);
            preparedStatement.setString(2, destination);
            resultSet = preparedStatement.executeQuery();
            //循环将查询到的结果放入到一个List中，用Bean存放每个结果
            while (resultSet.next()) {
                RunBean bean = new RunBean();
                bean.setRunId(resultSet.getInt("runid"));
                bean.setOrigin(origin);
                bean.setDestination(destination);
                bean.setTime(resultSet.getString("time"));
                bean.setPrice(resultSet.getDouble("price"));
                bean.setTotal(resultSet.getInt("total"));
                runBeans.add(bean);
            }
            return runBeans;
        } catch (Exception e) {
            //若中途出错，直接返回一个空的List
            return runBeans;
        }finally {
            closeAll();
        }
    }

    /**
     * 用于得到特定车次的所有信息
     * @param runId 车次
     * @return 对应车次的所有信息，若没有对应车次，返回空
     */
    @Override
    public RunBean getRun(int runId) {
        //数据库语句
        String statement = "select * from runs where  runid = ?";
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, runId);
            resultSet = preparedStatement.executeQuery();
            RunBean bean = new RunBean();
            //循环将查询到的结果放入到一个List中，用Bean存放每个结果
            if (resultSet.next()) {
                bean.setRunId(resultSet.getInt("runid"));
                bean.setOrigin(resultSet.getString("origin"));
                bean.setDestination(resultSet.getString("dest"));
                bean.setTime(resultSet.getString("time"));
                bean.setPrice(resultSet.getDouble("price"));
                bean.setTotal(resultSet.getInt("total"));
                return bean;
            } else {
                //若中途出错，直接返回空
                return null;
            }
        } catch (Exception e) {
            return null;
        }finally {
            closeAll();
        }
    }

    /**
     * 得到特定唱车次的余票数量
     * @param id 车次
     * @return 所查询车次的余票数量，若出错，返回0
     */
    @Override
    public int getAvailableTicket(int id) {
        String statement = "select ((select total from runs where runid = ?) - count(*) )as total from trade where runid = ? and canceled = 0";
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            resultSet = preparedStatement.executeQuery();
            int i =  0;
            if (resultSet.next()) {
                i = resultSet.getInt(1);
                return i;
            }
            return 0;
        } catch (SQLException e) {
            return 0;
        }finally {
            closeAll();
        }
    }

    /*
    用于关闭所有的数据库语句和结果集
     */
    private void closeAll() {
        try {
            if (!resultSet.isClosed()) {
                resultSet.close();
            }
            if (!preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        } catch (Exception ignored) {

        }
    }
}
