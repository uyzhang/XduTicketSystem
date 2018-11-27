package dao;

import bean.TradeBean;
import dao_interface.TradeDaoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * 这个类实现了TradeDaoInterface接口
 * 每个函数的具体作用在TradeDaoInterface中说明
 * 遵循以下原则：
 *     若返回的是列表，出错时，返回的列表长度为0
 *     若返回值一个单一对象，返回空
 */
public class TradeDao implements TradeDaoInterface {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public TradeDao() {
        connection = DBConnection.getConnection();
    }

    /**
     * 创建交易,成功返回true，失败返回false
     * @param trade 需要创建的交易的信息
     * @return true 交易成功，false 交易失败
     */
    @Override
    public boolean createTrade(TradeBean trade) {

        //首先检测用户是否存在
        String statement = "select * from users where  userid = ?";
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, trade.getUserId());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        //其次检测是否有对应车次
        statement = "select * from runs where runid = ?";
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, trade.getRunId());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        //检测都通过后，数据库中添加交易信息
        statement = "insert into trade (userid, runid, canceled) values (?, ?, 0)";
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, trade.getUserId());
            preparedStatement.setInt(2, trade.getRunId());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }finally {
            try {
                if (!preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (Exception ignore) {

            }
        }
    }


    /**
     * 查询交易,若有错无或没有购票记录，则返回的列表长度为0
     * @param userId 需要查询所有交易信息的用户id
     * @return 对应用户的交易列表，
     *         若查询错误或用户没有购票信息，返回的列表长度为0
     */
    @Override
    public ArrayList<TradeBean> queryTrade(int userId) {
        ArrayList<TradeBean> tradeBeans = new ArrayList<>();
        //先通过用户名查询到id,然后根据id 查询 订单
        String statement = "select * from trade where userid = ?";
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TradeBean bean = new TradeBean();
                bean.setTradeId(resultSet.getInt(1));
                bean.setUserId(userId);
                bean.setRunId(resultSet.getInt("runid"));
                bean.setCanceled(resultSet.getInt("canceled") == 1);
                tradeBeans.add(bean);
            }
        } catch (Exception e) {
            //如果SQL查询出错，就返回一个长度为0的ArrayList
            return tradeBeans;
        }finally {
            //关闭Sql连接
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tradeBeans;
    }

    /**
     * 1.用户退票，取消订单,成功返回true，失败返回false
     * 2.注意退票前，一定要检查所选中的trade  的 cancel 位是否为 0 ，不为0 ，请提示已经退票，
     * 3.不满足2，不得调用此函数，否则会重复退票
     * @param trade 需要取消的交易信息
     * @return 是否退票成功 true false
     */
    @Override
    public boolean cancelTrade(TradeBean trade) {
        String statement = "update trade set canceled = 1 where tradeid = ?";
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, trade.getTradeId());
            if (preparedStatement.executeUpdate() == 1) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }finally {
            try {
                if (!preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (Exception ignore) {
            }
        }
        return false;
    }
}
