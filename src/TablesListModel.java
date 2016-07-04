import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class TablesListModel extends DefaultListModel
{
    private List<String> tableNames;

    public TablesListModel()
    {
        Table newTable = new Table("Account");
        List<String> tableNames = new ArrayList<>();
        System.out.println("check");
        tableNames.add("Account");
        System.out.println("Table added to table list");
    }

    //get Count based on index

    public List<String> getList()
    {
        return tableNames;
    }





}