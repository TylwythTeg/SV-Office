import java.util.*;
import java.sql.*;
import java.io.*;

public class MachineDAO extends DAO
{

    public MachineDAO() throws SQLException
    {
    }

    public List<Machine> getAllMachines() throws SQLException
    {
        List<Machine> list = new ArrayList<>();

        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM machine"))
        {
            while(resultSet.next())
            {
                Machine machine = rowToMachine(resultSet);
                list.add(machine);
            }
            return list;
        }

    }

    public List<String> getAllBrands() throws SQLException
    {
        List<String> list = new ArrayList<>();

        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT brand FROM machine"))
        {
            while(resultSet.next())
            {

                if(!list.contains(resultSet.getString("brand")))
                    list.add(resultSet.getString("brand"));
            }
            return list;
        }
    }

    public List<String> getAllTypes() throws SQLException
    {
        List<String> list = new ArrayList<>();

        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT type FROM machine"))
        {
            while(resultSet.next())
            {
                if(!list.contains(resultSet.getString("type")))
                    list.add(resultSet.getString("type"));
            }
            return list;
        }

    }

    public List<Machine> getMachinesFromAccountId(int accountId) throws SQLException
    {

        List<Machine> list = new ArrayList<>();

        try(Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM machine JOIN account ON machine.account_id = account.account_id WHERE machine.account_id=" + accountId))
        {
            while(resultSet.next())
            {
                Machine machine = rowToMachine(resultSet);
                list.add(machine);
            }
            return list;
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

    public int getRowCount() throws SQLException
    {
        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM machine"))
        {
            int i = 0;
            while(resultSet.next())
            {
                i++;
            }
            return i;
        }
    }

    //Creates new machine and retrieves it
    public Machine newMachine() throws SQLException
    {
        try(Statement stmt = connection.createStatement())
        {
            stmt.execute("INSERT INTO machines (type, brand) VALUES ('New Machine','New Brand')");
            Machine machine = getNewMachine();
            return machine;
        }
    }

    //Retrieves newest machine
    public Machine getNewMachine() throws SQLException
    {
        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM machines WHERE machine_id=(SELECT max(machine_id) FROM machines)"))
        {
            resultSet.next();
            Machine machine = rowToMachine(resultSet);
            return machine;
        }
    }

    public void deleteMachine(int machine_id) throws SQLException
    {
        try(Statement stmt = connection.createStatement())
        {
            stmt.execute("DELETE FROM machines WHERE machine_id=" + machine_id);
            System.out.println("Machine Deleted in DATABASE");
        }
    }

    public void updateMachine(int machineID, String machineType, String machineBrand, String machineModel, String machineAsset, Integer accountID) throws SQLException
    {
        String updateString =
                "UPDATE machines "
                        + "set type = ?, brand= ?, model=?, asset=?, account_id=? where machine_id = ?";

        try(PreparedStatement update = connection.prepareStatement(updateString))
        {
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

    }

    public String getColumn(int machineID, String column) throws SQLException
    {
        try(Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT " + column + " FROM machines WHERE machine_id=" + machineID))
        {
            resultSet.next();
            String value = resultSet.getString(column);

            return value;
        }
    }

    //Can't throw SQLException because MachineTableModel.getValueAt uses the method and that method can't
    //throw it because its interface doesn't. I could store the accountName in the machine class but I don't know how
    //I feel about the (in)elegance of that
    public String getAccountName(Machine machine) //throws SQLException
    {

        String accountName = "";

        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT name FROM accounts WHERE account_id=" + machine.getAccountId()))
        {
            if(resultSet.next())
                accountName = resultSet.getString("name");

            return accountName;
        }
        catch(SQLException exc)
        {
            System.out.println(exc);
            exc.printStackTrace();
        }
        return accountName;

    }

    public Machine getMachine(int machineID) throws SQLException
    {
        String accountName = "";

        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM machines WHERE machine_id=" + machineID))
        {
            resultSet.next();
            Machine machine = rowToMachine(resultSet);
            return machine;
        }
    }
}