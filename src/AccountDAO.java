import java.util.*;
import java.sql.*;

public class AccountDAO extends DAO
{

    public AccountDAO() throws Exception
    {
       /* System.out.println("accountDAO1");
        if(connection == null)
        {
            System.out.println("accountDAO2");
            connectToDatabase();
        }*/
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

    public int getRowCount() throws Exception
    {
        Statement stmt = null;
        ResultSet resultSet = null;

        stmt = connection.createStatement();
        resultSet = stmt.executeQuery("SELECT * FROM account");

        int i = 0;

        while(resultSet.next())
        {
            i++;
        }

        return i;


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




}