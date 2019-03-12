package dao;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool
{
    private static BasicDataSource dataSource;
    private static Connection connection;

    private static BasicDataSource getDataSource()
    {
        if (dataSource == null)
        {
            try
            {
                Class.forName("org.postgresql.Driver");
            }
            catch (ClassNotFoundException ex)
            {
                ex.printStackTrace();
                return null;
            }

            BasicDataSource ds = new BasicDataSource();
            ds.setUrl("jdbc:postgresql://localhost/workers");
            ds.setUsername("postgres");
            ds.setPassword("postgres");

            dataSource = ds;
        }

        return dataSource;
    }

    public static Connection getConnection()
    {
        if (connection ==null)
        {
            try
            {
                connection = getDataSource().getConnection();
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                return null;
            }
        }

        return connection;
    }
}
