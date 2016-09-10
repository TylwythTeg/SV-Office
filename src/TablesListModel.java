import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class TablesListModel extends DefaultListModel
{
    //private List<String> tableNames;

    public TablesListModel()
    {
       //List<String> tableNames = new ArrayList<>();
    }

    public void initList(int accountRows)
    {
        this.addElement("Accounts " + "(" + accountRows + ")");
        this.addElement("Machine");
        this.addElement("Log");
        this.addElement("Product");
        this.addElement("Route");
        this.addElement("Employee");
    }

    /*public String getSelection(String selection)
    {
        System.out.println("test");
        if(selection.startsWith("Account"))
        {
            return "Account";
        }
        if(selection.startsWith("Machine"))
        {
            return "Machine";
        }
        if(selection.startsWith("Log"))
        {
            return "Log";
        }
        if(selection.startsWith("Product"))
        {
            return "Product";
        }
        if(selection.startsWith("Route"))
        {
            return "Route";
        }
        if(selection.startsWith("Employee"))
        {
            return "Employee";
        }

        return "Invalid Selection";
    }*/

    //get Count based on index

   // public List<String> getList()
    //{
        //return tableNames;
    //}





}