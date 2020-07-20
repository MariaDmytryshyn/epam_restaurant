package app.configuration;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.*;


//public class ConnectionPool {
//    private static volatile DataSource dataSource;
//
//    public static DataSource getDataSource() {
//        if (dataSource == null) {
//            synchronized (ConnectionPool.class) {
//                if (dataSource == null) {
//                    BasicDataSource ds = new BasicDataSource();
//                    ds.setDriverClassName("com.mysql.jdbc.driver");
//                    ds.setUrl("jdbc:mysql://localhost:3307/restaurant");
//                    ds.setUsername("root");
//                    ds.setPassword("root");
//                    ds.setMaxIdle(5);
//                    ds.setMaxOpenPreparedStatements(50);
//                    dataSource = ds;
//                }
//            }
//        }
//        return dataSource;
//    }
//}


public class DBCPDataSource {

    private DataSource dataSource;

    private static DBCPDataSource dbcpDataSource;

    private BasicDataSource ds;

    private DBCPDataSource() throws IOException, SQLException,
            PropertyVetoException {

        ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.driver");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setUrl("jdbc:mysql://localhost:3307/restaurant");

        ds.setMinIdle(5);
        ds.setMaxIdle(20);
        ds.setMaxOpenPreparedStatements(180);

        this.setDataSource(ds);

    }

    public static DBCPDataSource getInstance() throws IOException,
            SQLException, PropertyVetoException {
        if (dbcpDataSource == null) {
            dbcpDataSource = new DBCPDataSource();
            return dbcpDataSource;
        } else {
            return dbcpDataSource;
        }
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        return this.ds.getConnection();
    }

}
