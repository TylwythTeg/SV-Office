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