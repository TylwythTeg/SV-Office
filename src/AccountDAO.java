import java.util.*;
import java.sql.*;

public class AccountDAO extends DAO
{

    public AccountDAO() throws Exception
    {
        //System.out.println(connection.getAutoCommit());
    }

    public List<Account> getAllAccounts() throws Exception
    {
        List<Account> list = new ArrayList<>();

        Statement stmt = null;
        ResultSet resultSet = null;


        try
        {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM accounts");

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
        resultSet = stmt.executeQuery("SELECT * FROM accounts");

        int i = 0;

        while(resultSet.next())
        {
            i++;
        }

        return i;


    }

    public Account newAccount()
    {
        Statement stmt = null;
        ResultSet resultSet = null;

        try
        {
            stmt = connection.createStatement();
            System.out.println("ehey");
            //String query = "INSERT INTO account ("
            stmt.execute("INSERT INTO accounts (name, address) VALUES ('New Account','New Address')");


            resultSet = stmt.executeQuery("SELECT * FROM accounts WHERE account_id=(SELECT max(account_id) FROM accounts)");
            Account account = rowToAccount(resultSet);
            return account;

        }
        catch(Exception exc)
        {
            System.out.println("hey");

        }
        Account account = null;
        return account;

    }

    public void deleteAccount(int account_id)
    {
        Statement stmt = null;

        try
        {
            stmt = connection.createStatement();
            stmt.execute("DELETE FROM accounts WHERE account_id=" + account_id);
            System.out.println("Account Deleted in DATABASE");


        }
        catch(Exception exc)
        {
            System.out.println("failure in deleting accoutn in accountDAO");
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
        System.out.println("Account:" + resultSet.getInt("account_id"));

        return account;
    }

    public void updateAccount(int accountID, String accountName, String accountAddress) throws SQLException
    {

        String updateString =
               "UPDATE accounts "
                       + "set name = ?, address= ? where account_id = ?";
        PreparedStatement update = null;
        update = connection.prepareStatement(updateString);
        update.setString(1,accountName);
        update.setString(2,accountAddress);
        update.setInt(3,accountID);
        update.executeUpdate();

    }

    public String getColumn(int accountID, String column) throws SQLException
    {

        Statement stmt = null;
        ResultSet resultSet = null;

        stmt = connection.createStatement();
        resultSet = stmt.executeQuery("SELECT " + column + " FROM accounts WHERE account_id=" + accountID);
        resultSet.next();
        String value = resultSet.getString(column);




        return value;
    }




}