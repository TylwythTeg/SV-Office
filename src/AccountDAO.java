import java.util.*;
import java.sql.*;
//import java.io.*;

public class AccountDAO
{
    private Connection connection;

    public AccountDAO() throws Exception
    {
        connectToDatabase();

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

    public List<Account> searchAccounts(String name) throws Exception
    {
        List<Account> list = new ArrayList<>();

        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try
        {
            name="%"+name;
            name+="%";

            stmt = connection.prepareStatement("SELECT * FROM account WHERE name LIKE ?");
            stmt.setString(1,name);

            resultSet = stmt.executeQuery();

            while(resultSet.next())
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

    private void connectToDatabase()
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
}