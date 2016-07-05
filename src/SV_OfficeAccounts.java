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

    JFrame frame;
    private JPanel panelhh;
    private JPanel ListPanel;
    private JList ListForSections;
    private JButton buttonNew;
    private JButton buttonDelete;
    private JPanel topButtonPanel;
    private JTable tableViewTable;
    private JPanel panelForTable;
    private JPanel tablePanelEnclosure;
    private JScrollPane RowListScrollPane;

    private DefaultListModel tablelist;



    public static void main(String[] args)
    {
        JFrame frame = new JFrame("SV_OfficeAccounts");
        frame.setContentPane(new SV_OfficeAccounts().panelhh);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.setTitle("SV Office Alpha");


    }

    public void setTableList() throws Exception
    {
        TablesListModel tablelist = new TablesListModel();

       // String accountTableName = "Account";
        //accountDAO = new AccountDAO();

      // accountTableName += accountDAO.getRowCount();

        tablelist.addElement("Accounts " + "(" + accountDAO.getRowCount() + ")");
        tablelist.addElement("Machine");
        tablelist.addElement("Log");
        tablelist.addElement("Product");
        tablelist.addElement("Route");
        tablelist.addElement("Employee");

       ListForSections.setModel(tablelist);
    }


    public SV_OfficeAccounts()
    {
        try
        {
            accountDAO = new AccountDAO();
            setTableList();
        }
        catch(Exception exc)
        {
            System.out.println("Error Creating AccountDAOg");
        }

        //methodthis set table to accounts
        try
        {

            List<Account> accounts = null;

            accounts = accountDAO.getAllAccounts();

            AccountTableModel accountmodel = new AccountTableModel(accounts);
            tableViewTable.setModel(accountmodel);

        }

        catch (Exception exc)
        {
            System.out.println("Error on search");
        }
        //methodthis

    }


    private void createUIComponents()
    {
        // TODO: place custom component creation code here
    }
}
