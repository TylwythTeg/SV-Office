import java.sql.*;

public class DAO
{
    protected static Connection connection = null;

   public DAO() throws Exception
    {
        if(connection == null)
            connectToDatabase();
    }

    public void connectToDatabase() throws Exception
    {
        String url = "jdbc:mysql://localhost:3306/sacramento_vending";
        String user = "root";
        String password = "rootpurse";


        try
        {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection to " + url + " successful");
        } catch (Exception exc)
        {
            System.out.println("Database connection to " + url + " unsuccessful");
        }

    }

    protected static void close(Connection connection, Statement stmt, ResultSet resultSet) throws SQLException
    {
        if(resultSet != null)
            resultSet.close();

        if(stmt != null)
            stmt.close();

        if(connection != null)
            connection.close();
    }

    protected void close(Statement stmt, ResultSet resultSet) throws SQLException
    {
        close(null,stmt,resultSet);
    }
    protected void close(Statement stmt) throws SQLException
    {
        close(null,stmt,null);
    }
    protected void close(ResultSet resultSet) throws SQLException
    {
        close(null,null,resultSet);
    }


}