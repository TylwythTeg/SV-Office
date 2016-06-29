import java.util.*;
import java.sql.*;
//import java.io.*;

public class AccountDAO
{
    private Connection connection;

    public AccountDAO() throws Exception
    {
        //Connect to database
        String url = "jdbc:mysql://localhost:3306/sacramento_vending";
        String user = "root";
        String password = "rootpurse";

        connection = DriverManager.getConnection(url,user,password);
        System.out.println("Database connection to " + url + " successful");


    }

    public List<Account> getAllAccounts() throws Exception
    {
        List<Account> list = new ArrayList<>();

        Statement stmt = null;
        ResultSet resultSet = null;


        try
        {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM account");

            while (resultSet.next())
            {
                Account account = rowToAccount(resultSet);
                list.add(account);
            }
            return list;
        }
        finally
        {
            close(stmt,resultSet);
        }

    }

    private Account rowToAccount(ResultSet resultSet) throws SQLException
    {
        int id = resultSet.getInt("account_id");
        String name = resultSet.getString("name");
        String address = resultSet.getString("address");

        Account account = new Account(id,name,address);

        return account;
    }

    private static void close(Connection connection, Statement stmt, ResultSet resultSet) throws SQLException
    {
        if(resultSet != null)
            resultSet.close();

        if(stmt != null)
            stmt.close();

        if(connection != null)
            connection.close();
    }

    private void close(Statement stmt, ResultSet resultSet) throws SQLException
    {
        close(null,stmt,resultSet);
    }
}