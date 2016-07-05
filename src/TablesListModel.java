import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class TablesListModel extends DefaultListModel
{
    private List<String> tableNames;

    public TablesListModel()
    {
        List<String> tableNames = new ArrayList<>();
    }

    //get Count based on index

    public List<String> getList()
    {
        return tableNames;
    }





}