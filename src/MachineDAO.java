import java.util.*;
import java.sql.*;
import java.io.*;

public class MachineDAO extends DAO
{

    public MachineDAO() throws Exception
    {
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

    public Machine newMachine()
    {
        Statement stmt = null;
        ResultSet resultSet = null;

        try
        {
            stmt = connection.createStatement();
            System.out.println("ehey");
            stmt.execute("INSERT INTO machines (type, brand) VALUES ('New Machine','New Brand')");


            resultSet = stmt.executeQuery("SELECT * FROM accounts WHERE account_id=(SELECT max(account_id) FROM accounts)");
            Machine machine = rowToMachine(resultSet);
            return machine;

        }
        catch(Exception exc)
        {
            System.out.println("hey");

        }
        Machine machine = null;
        return machine;

    }

    public void deleteMachine(int machine_id)
    {
        Statement stmt = null;

        try
        {
            stmt = connection.createStatement();
            stmt.execute("DELETE FROM machines WHERE machine_id=" + machine_id);
            System.out.println("Machine Deleted in DATABASE");


        }
        catch(Exception exc)
        {
            System.out.println("failure in deleting accoutn in MachineDAO");
        }


    }
}