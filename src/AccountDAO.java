import java.util.*;
import java.sql.*;

public class AccountDAO extends DAO
{

    public AccountDAO() throws SQLException
    {

    }

    public List<Account> getAllAccounts() throws SQLException
    {
        List<Account> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM accounts"))
        {
            while(resultSet.next())
            {
                Account account = rowToAccount(resultSet);
                list.add(account);
            }
            return list;
        }

    }

    public List<String> getAllAccountNames() throws SQLException
    {
        List<Account> accounts = getAllAccounts();
        List<String> names = new ArrayList<>();

        for(Account account : accounts)
        {
            names.add(account.getName());
        }

        return names;

    }

    public int getRowCount() throws SQLException
    {
        try(Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM accounts"))
        {
            int i = 0;
            while(resultSet.next())
            {
                i++;
            }
            return i;
        }
    }

    public Account newAccount() throws SQLException
    {
        try(Statement stmt = connection.createStatement())
        {
            stmt.execute("INSERT INTO accounts (name, address) VALUES ('New Account','New Address')");
            Account account = getNewAccount();
            return account;
        }
    }

    public Account getNewAccount() throws SQLException
    {
        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM accounts WHERE account_id=(SELECT max(account_id) FROM accounts)"))
        {
            resultSet.next();
            Account account = rowToAccount(resultSet);
            return account;
        }
    }

    public void deleteAccount(int account_id) throws SQLException
    {
        try(Statement stmt = connection.createStatement())
        {
            stmt.execute("DELETE FROM accounts WHERE account_id=" + account_id);
            System.out.println("Account Deleted in DATABASE");
        }
    }

    public List<Account> searchAccounts(String name) throws SQLException
    {
        List<Account> list = new ArrayList<>();

        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        name="%"+name;
        name+="%";

        try
        {
            stmt = connection.prepareStatement("SELECT * FROM account WHERE name LIKE ?");
            stmt.setString(1,name);
            resultSet = stmt.executeQuery();
            while(resultSet.next())
            {
                Account account = rowToAccount(resultSet);
                list.add(account);
            }
            close(resultSet);
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

        try(PreparedStatement update = connection.prepareStatement(updateString))
        {
            update.setString(1,accountName);
            update.setString(2,accountAddress);
            update.setInt(3,accountID);

            update.executeUpdate();
        }
    }

    public String getColumn(int accountID, String column) throws SQLException
    {
        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT " + column + " FROM accounts WHERE account_id=" + accountID))
        {
            resultSet.next();
            String value = resultSet.getString(column);

            return value;
        }
    }

    public int getIdFromName(String accountName) throws SQLException
    {
        System.out.println("ACCOUNTNAME IS " + accountName);

        try(Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT account_id FROM accounts WHERE name=\'" + accountName +"\'"))
        {
            resultSet.next();
            int accountID = resultSet.getInt("account_id");
            System.out.println("ACCOUNTID IS " + accountID);
            return accountID;
        }

    }


}