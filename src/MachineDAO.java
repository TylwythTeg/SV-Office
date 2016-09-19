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

    protected Machine rowToMachine(ResultSet resultSet) throws SQLException
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

    public void updateMachine(int machineID, String machineType, String machineBrand, String machineModel, String machineAsset) throws SQLException
    {

        String updateString =
                "UPDATE machines "
                        + "set type = ?, brand= ?, model=?, asset=? where machine_id = ?";
        PreparedStatement update = null;
        update = connection.prepareStatement(updateString);
        update.setString(1,machineType);
        update.setString(2,machineBrand);
        update.setString(3,machineModel);
        update.setString(4,machineAsset);
        update.setInt(5,machineID);

        update.executeUpdate();

    }

    public void updateMachine(int machineID, String machineType, String machineBrand, String machineModel, String machineAsset, Integer accountID) throws SQLException
    {



        String updateString =
                "UPDATE machines "
                        + "set type = ?, brand= ?, model=?, asset=?, account_id=? where machine_id = ?";
        PreparedStatement update = null;
        update = connection.prepareStatement(updateString);
        update.setString(1,machineType);
        update.setString(2,machineBrand);
        update.setString(3,machineModel);
        update.setString(4,machineAsset);
        if(accountID != null)
            update.setInt(5,accountID);
        else
            update.setNull(5,Types.NULL);
        update.setInt(6,machineID);

        update.executeUpdate();

    }

    public String getColumn(int machineID, String column) throws SQLException
    {

        Statement stmt = null;
        ResultSet resultSet = null;

        stmt = connection.createStatement();
        resultSet = stmt.executeQuery("SELECT " + column + " FROM machines WHERE machine_id=" + machineID);
        resultSet.next();
        String value = resultSet.getString(column);

        return value;
    }

    public String getAccountName(Machine machine)
    {
        Statement stmt = null;
        ResultSet resultSet = null;
        String accountName = "";

        try
        {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT name FROM accounts WHERE account_id=" + machine.getAccountId());
            if(resultSet.next())
                accountName = resultSet.getString("name");
        }
        catch(Exception exc)
        {
            exc.printStackTrace();
        }
        return accountName;

    }

    public Machine getMachine(int machineID) throws Exception
    {
        Statement stmt = null;
        ResultSet resultSet = null;
        String accountName = "";
        Machine machine;


        stmt = connection.createStatement();
        resultSet = stmt.executeQuery("SELECT * FROM machines WHERE machine_id=" + machineID);

        resultSet.next();
        machine = rowToMachine(resultSet);
        return machine;


    }
}