import java.util.*;
import java.sql.*;
import java.io.*;

public class MachineDAO extends DAO
{

    public MachineDAO() throws Exception
    {
       /* System.out.println("machineDAO1");
       if(connection == null)
        {
            System.out.println("machineDAO2");
            connectToDatabase();
        }*/
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
}