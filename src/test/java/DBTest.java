
import dao.DBConnection;

import org.junit.Test;





public class DBTest {


    @Test
    public void getDBConnectionTest() {
        DBConnection.getConnection();
    }

    @Test
    public void closeDB() {
        DBConnection.closeConnection();
    }
}
