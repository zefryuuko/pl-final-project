package zefryuuko.chat.lib;

import java.sql.*;

public class Database
{
    private Connection connection;
    private Logging logging;

    /**
     * Database <br/>
     * Bridges database server with Java application
     * @param host Database host address
     * @param username Database username
     * @param password Database password
     */
    public Database(String host, String username, String password)
    {
        this.logging = new Logging("DB " + username + "@" + host);

        try
        {
            connection = DriverManager.getConnection("jdbc:" + host, username, password);
            logging.log("Connected to database server");
        }
        catch (SQLException e)
        {
            logging.log("Failed to connect to database server");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Executes the passed query
     * @param query SQL query to be executed
     * @return ResultSet containing the query result
     */
    public ResultSet executeQuery(String query)
    {
        ResultSet resultSet = null;

        try
        {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        return resultSet;
    }

    /**
     * Executes the passed update query
     * @param query SQL update query to be executed
     * @return True if atleast one row is affected
     */
    public boolean executeUpdate(String query)
    {
        int rowsAffected = 0;

        try
        {
            Statement statement = connection.createStatement();
            rowsAffected = statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        return rowsAffected > 0;
    }
}

