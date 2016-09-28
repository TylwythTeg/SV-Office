import java.util.*;
import java.sql.*;
import java.util.Date;

public class RevenueDAO extends DAO
{
    public RevenueDAO() throws SQLException
    {

    }

    public List<RevenueLog> getAllLogs() throws SQLException
    {
        List<RevenueLog> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery("SELECT * FROM log ORDER BY account_id, date"))
        {
            while(resultSet.next())
            {
                RevenueLog log = rowToRevenueLog(resultSet);
                System.out.println(log);
                list.add(log);
            }
            return list;
        }

    }

    public List<RevenueLog> getAllLogs(String sort) throws SQLException
    {
        List<RevenueLog> list = new ArrayList<>();

        if(sort == "date")
            sort = " ORDER BY date";
        else if(sort == "Month")
        {
            sort = " ORDER BY MONTH(date), account_id";
        }
        else sort = "";

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery("SELECT * FROM log" + sort))
        {
            while(resultSet.next())
            {
                RevenueLog log = rowToRevenueLog(resultSet);
                //System.err.println("HEY HEY HEY" + log + log.getAccountId());
                list.add(log);
            }
            return list;
        }

    }

    private RevenueLog rowToRevenueLog(ResultSet resultSet) throws SQLException
    {
        int id = resultSet.getInt("log_id");
        Date date = resultSet.getDate("date");
        Double money = resultSet.getDouble("money");
        int accountID = resultSet.getInt("account_id");

        RevenueLog log = new RevenueLog(id,date,money, accountID);
        System.out.println("Account:" + resultSet.getInt("account_id"));

        return log;
    }

    //Can't throw SQLException because RevenueTableModel.getValueAt uses the method and that method can't
    //throw it because its interface doesn't. I could store the accountName in the RevenueLog class but I don't know how
    //I feel about the (in)elegance of that
    public String getAccountName(RevenueLog log) //throws SQLException
    {

        String accountName = "";

        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT name FROM accounts WHERE account_id=" + log.getAccountId()))
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

    public void updateRevenueLog(int logID, Date logDate, Double money, Integer accountID) throws SQLException
    {
        String updateString =
                "UPDATE log "
                        + "set date = ?, money= ?, account_id=? where log_id = ?";

        try(PreparedStatement update = connection.prepareStatement(updateString))
        {
            update.setDate(1, new java.sql.Date(logDate.getTime()));
            update.setDouble(2,money);
            if(accountID != null)
                update.setInt(3,accountID);
            else
                update.setNull(3,Types.NULL);
            update.setInt(4,logID);

            update.executeUpdate();
        }

    }

    //Creates new log and retrieves it
    public RevenueLog newLog() throws SQLException
    {
        try(Statement stmt = connection.createStatement())
        {
            stmt.execute("INSERT INTO log (money) VALUES ('0')");
            RevenueLog log = getNewestLog();
            return log;
        }
    }

    //Retrieves newest log
    public RevenueLog getNewestLog() throws SQLException
    {
        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM log WHERE log_id=(SELECT max(log_id) FROM log)"))
        {
            resultSet.next();
            RevenueLog log = rowToRevenueLog(resultSet);
            return log;
        }
    }

    public RevenueLog getRevenueLog(int logID) throws SQLException
    {
        String accountName = "";

        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM log WHERE log_id=" + logID))
        {
            resultSet.next();
            RevenueLog log = rowToRevenueLog(resultSet);
            return log;
        }
    }

    public String getColumn(int logID, String column) throws SQLException
    {
        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT " + column + " FROM log WHERE log_id=" + logID))
        {
            resultSet.next();
            String value = resultSet.getString(column);

            return value;
        }
    }

    public Date getDate(int logID) throws SQLException
    {
        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT date FROM log WHERE log_id=" + logID))
        {
            resultSet.next();
            Date value = resultSet.getDate("date");

            return value;
        }
    }

    public List<RevenueLog> getLogsFromAccount(int  accountID) throws SQLException
    {
        List<RevenueLog> logs = new ArrayList<>();

        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM log WHERE account_id=" + accountID))
        {
            while(resultSet.next())
                logs.add(rowToRevenueLog(resultSet));


            return logs;
        }
    }

    public int getRowCount() throws SQLException
    {
        try(Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM log"))
        {
            int i = 0;
            while(resultSet.next())
            {
                i++;
            }
            return i;
        }
    }
}
