import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
/**
 * Created by Rob on 7/3/2016.
 */
public class SV_OfficeAccounts extends JFrame
{
    private AccountDAO accountDAO;
    //more DAOs for other tables
    private MachineDAO machineDAO;

    private JPanel panelhh;
    private JPanel ListPanel;
    private JList ListForSections;

    private DefaultListModel tablelist;
    JFrame frame;


    public static void main(String[] args)
    {
        JFrame frame = new JFrame("SV_OfficeAccounts");
        frame.setContentPane(new SV_OfficeAccounts().panelhh);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    public void setTableList()
    {
        TablesListModel tablelist = new TablesListModel();
        //DefaultListModel tablelist = new DefaultListModel();
        Table newTable = new Table("Account");
        tablelist.addElement("Account");
        tablelist.addElement("Machine");
        tablelist.addElement("Log");
        tablelist.addElement("Product");
        tablelist.addElement("Route");
        tablelist.addElement("Employee");
        //ListForSections = new JList(tablelist);
        //ListForSections.setModel(tablelist);


        String tableNames[] =
                {
                        "Accounts"
                };

        System.out.println("whath");
        //ListForSections = new JList();
        //frame.getContentPane().add(ListForSections);
        System.out.println("whatu");

       ListForSections.setModel(tablelist);
        //ListPanel.add( ListForSections);
    }


    public SV_OfficeAccounts()
    {
        try
        {
            accountDAO = new AccountDAO();
            //System.out.println("test");
            setTableList();
            //more DAOs for other tables
        }
        catch(Exception exc)
        {
            System.out.println("Error Creating AccountDAOg");
        }


        /*
        try
        {
            machineDAO = new MachineDAO();
        }
        catch(Exception exc)
        {
            System.out.println("Error Creating MachineDAO");
        }*/
    }



}
