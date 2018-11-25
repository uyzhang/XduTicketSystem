package dao;

import bean.TradeBean;
import dao_interface.TradeDaoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TradeDao implements TradeDaoInterface {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;


    public TradeDao() {
        connection = DBConnection.getConnection();
    }




    @Override
    public boolean createTrade(TradeBean trade) {
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
