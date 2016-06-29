
import java.sql.*;
public class Driver
{
    public static void main(String[] args)
    {
        String url = "jdbc:mysql://localhost:3306/sacramento_vending";
        String user = "root";
        String password = "erg";

        try
        {
            Connection Conn = DriverManager.getConnection(url, user, password);

            Statement Stmt = Conn.createStatement();

            //list all machines
            ResultSet Rs = Stmt.executeQuery("SELECT account.name,machine.type,machine.brand FROM account" +
                                                " JOIN machine ON account.account_id=machine.account_id " +
                                                    "ORDER BY account.account_id ASC");

            while(Rs.next())
            {
                System.out.println(Rs.getString("account.name") + " " + Rs.getString("machine.brand"));
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
