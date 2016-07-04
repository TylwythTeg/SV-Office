import java.util.*;
import java.sql.*;

public class DAO
{
    private Connection connection;

    public DAO() throws Exception
    {
        //Connect to database
        String url = "jdbc:mysql://localhost:3306/sacramento_vending";
        String user = "root";
        String password = "rootpurse";

        connection = DriverManager.getConnection(url,user,password);
        System.out.println("Database connection to " + url + " successful");
    }
}