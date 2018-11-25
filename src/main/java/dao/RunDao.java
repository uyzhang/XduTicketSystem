package dao;

import bean.RunBean;
import dao_interface.RunDaoInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RunDao implements RunDaoInterface {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;


    public RunDao() {
        connection = DBConnection.getConnection();
    }


    @Override
    public ArrayList<RunBean> getRuns(String origin, String destination) {
        ArrayList<RunBean> runBeans = new ArrayList<>();
        String statement = "select * from runs where origin = ? and dest = ? ";
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, origin);
            preparedStatement.setString(2, destination);
            resultSet = preparedStatement.executeQuery();
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
            return runBeans;
        }finally {
            closeAll();
        }
    }
    @Override
    public RunBean getRun(int runId) {
        String statement = "select * from runs where  runid = ?";
        try {
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, runId);
            resultSet = preparedStatement.executeQuery();
            RunBean bean = new RunBean();
            if (resultSet.next()) {
                bean.setRunId(resultSet.getInt("runid"));
                bean.setOrigin(resultSet.getString("origin"));
                bean.setDestination(resultSet.getString("dest"));
                bean.setTime(resultSet.getString("time"));
                bean.setPrice(resultSet.getDouble("price"));
                bean.setTotal(resultSet.getInt("total"));
                return bean;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }finally {
            closeAll();
        }
    }



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
