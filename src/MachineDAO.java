import java.util.*;
import java.sql.*;
import java.io.*;

public class MachineDAO
{
    private Connection connection;

    public MachineDAO() throws Exception
    {
            connectToDatabase();
    }

    public List<Machine> getAllMachines() throws Exception
    {
        List<Machine> list = new ArrayList<>();

        Statement stmt = null;
        ResultSet resultSet = null;


        try
        {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM machine");

            while (resultSet.next())
            {
                Machine machine = rowToMachine(resultSet);
                list.add(machine);
            }
            return list;
        }
        finally
        {
            close(stmt,resultSet);
        }

    }

    public List<Machine> getMachinesFromAccountId(int accountId) throws Exception
    {
        List<Machine> list = new ArrayList<>();

        Statement stmt = null;
        ResultSet resultSet = null;


        try
        {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM machine JOIN account ON machine.account_id = account.account_id WHERE machine.account_id=" + accountId);

            while (resultSet.next())
            {
                Machine machine = rowToMachine(resultSet);
                list.add(machine);
            }
            return list;
        }
        finally
        {
            close(stmt,resultSet);
        }

    }

    private Machine rowToMachine(ResultSet resultSet) throws SQLException
    {
        int id = resultSet.getInt("machine_id");
        String type = resultSet.getString("type");
        String brand = resultSet.getString("brand");
        String model = resultSet.getString("model");
        String asset = resultSet.getString("asset");
        int account_id = resultSet.getInt("account_id");

        Machine machine = new Machine(id,type,brand,model,asset,account_id);

        return machine;
    }

    public int getRowCount() throws Exception
    {
        Statement stmt = null;
        ResultSet resultSet = null;

        stmt = connection.createStatement();
        resultSet = stmt.executeQuery("SELECT * FROM machine");

        int i = 0;

        while(resultSet.next())
        {
            i++;
        }

        return i;
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