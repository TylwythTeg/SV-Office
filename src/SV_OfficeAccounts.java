import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.EventObject;
import javax.swing.event.*;
/**
 * Created by Rob on 7/3/2016.
 */
public class SV_OfficeAccounts extends JFrame
{
    private svLogic logic;

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
    private JTextField nameFilter;
    private JTextField formNameField;
    private JTextField formAddressField;
    private JLabel labelRevenue;
    private JScrollPane machineScrollPane;
    private JTable machineTable;

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

        tablelist.addElement("Accounts " + "(" + accountDAO.getRowCount() + ")");
        tablelist.addElement("Machine");
        tablelist.addElement("Log");
        tablelist.addElement("Product");
        tablelist.addElement("Route");
        tablelist.addElement("Employee");

        ListForSections.setModel(tablelist);
        ListForSections.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


    public SV_OfficeAccounts()
    {
        try
        {
            accountDAO = new AccountDAO();


            setTableList();

           machineDAO = new MachineDAO();

            //logic = new svLogic();
        }
        catch(Exception exc)
        {
            System.out.println("Error Creating AccountDAO");
        }
        tableViewTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //methodthis set table to accounts
        try
        {

            setAccountTableView();

        }

        catch (Exception exc)
        {
            System.out.println("Error on search");
        }
        //methodthis


        tableViewTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent event)
            {
                formNameField.setText(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 1).toString());
                formAddressField.setText(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 2).toString());

                Object accountIdObj = tableViewTable.getValueAt(tableViewTable.getSelectedRow(),0);
                int account_id =  (int) accountIdObj;




                List<Machine> machines = null;

                try
                {
                    machines = machineDAO.getMachinesFromAccountId(account_id);


                    MachineTableModel machinemodel = new MachineTableModel(machines);

                    machineTable.setModel(machinemodel);

                }
                catch(Exception exc)
                {
                    System.out.println("Couldn't query machine list");
                }

            }
        });

        ListForSections.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                String selection = ListForSections.getSelectedValue().toString();

                if(ListForSections.getValueIsAdjusting())
                    return;

                if(selection.startsWith("Account"))
                {
                    System.out.println("You have Selected Account");
                    setAccountTableView();
                }
                if(selection.startsWith("Machine"))
                {
                    System.out.println("You have Selected Machine");
                }
                if(selection.startsWith("Log"))
                {
                    System.out.println("You have Selected Log");
                }
                if(selection.startsWith("Product"))
                {
                    System.out.println("You have Selected Product");
                }
                if(selection.startsWith("Route"))
                {
                    System.out.println("You have Selected Route");
                }
                if(selection.startsWith("Employee"))
                {
                    System.out.println("You have Selected Employee");
                }

            }
        });
    }

    public void setAccountTableView()
    {
        try
        {
            List<Account> accounts = null;
            accounts = accountDAO.getAllAccounts();
            AccountTableModel accountmodel = new AccountTableModel(accounts);
            tableViewTable.setModel(accountmodel);
        }
        catch(Exception exc)
        {
            System.out.println("Table set failed");
        }
    }


    private void createUIComponents()
    {
        // TODO: place custom component creation code here
    }


}
