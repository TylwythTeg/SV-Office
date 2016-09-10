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
    //private svLogic logic;

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

    private TablesListModel tablelist;
    AccountTableModel accountmodel;



    public static void main(String[] args)
    {
        JFrame frame = new JFrame("SV_OfficeAccounts");
        frame.setContentPane(new SV_OfficeAccounts().panelhh);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.setTitle("SV Office Alpha");
        //System.out.println("hey");


    }

    public void setTableList() throws Exception
    {
        TablesListModel tablelist = new TablesListModel();
        tablelist.initList(accountDAO.getRowCount());

        ListForSections.setModel(tablelist);


    }


    public SV_OfficeAccounts()
    {
        ListForSections.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        try
        {
            accountDAO = new AccountDAO();

            setTableList();

            machineDAO = new MachineDAO();
        } catch (Exception exc)
        {
            System.out.println("Error Creating AccountDAO");
        }
        tableViewTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //methodthis set table to accounts
        try
        {

            setAccountTableView();

        } catch (Exception exc)
        {
            System.out.println("Error on search");
        }
        //methodthis


        tableViewTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent event)
            {
                //if (tableViewTable.getValueIsAdjusting())
                    //return;


                System.out.println("The next line will fail the second trigger (Why trigger twice for Table?");
                formNameField.setText(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 1).toString());
                formAddressField.setText(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 2).toString());

                Object accountIdObj = tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 0);
                int account_id = (int) accountIdObj;


                List<Machine> machines = null;

                try
                {
                    machines = machineDAO.getMachinesFromAccountId(account_id);


                    MachineTableModel machinemodel = new MachineTableModel(machines);

                    machineTable.setModel(machinemodel);

                } catch (Exception exc)
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
                System.out.println("In list response");

                if (ListForSections.getValueIsAdjusting())
                    return;


                String selection = ListForSections.getSelectedValue().toString();


                if (selection.startsWith("Account"))
                {
                    System.out.println("You have Selected Account");
                    updateAccountTableView();
                }
                if (selection.startsWith("Machine"))
                {
                    System.out.println("You have Selected Machine");
                }
                if (selection.startsWith("Log"))
                {
                    System.out.println("You have Selected Log");
                }
                if (selection.startsWith("Product"))
                {
                    System.out.println("You have Selected Product");
                }
                if (selection.startsWith("Route"))
                {
                    System.out.println("You have Selected Route");
                }
                if (selection.startsWith("Employee"))
                {
                    System.out.println("You have Selected Employee");
                }

            }
        });

        buttonNew.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Account account = accountDAO.newAccount();

                    accountmodel.addRow(account);
                    updateAccountTableView();
                } catch (Exception esc)
                {
                    System.out.println("psh");
                }
            }

        });
        buttonDelete.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("selected row is " + tableViewTable.getSelectedRow());
                System.out.println("test1");

                try{


                    int account_id = Integer.parseInt(tableViewTable.getModel().getValueAt(tableViewTable.getSelectedRow(),0).toString());
                    accountDAO.deleteAccount(account_id);


                   accountmodel.removeRow(tableViewTable.getSelectedRow()-1);
                    updateAccountTableView();
                }
                catch(Exception esc)
                {
                    System.out.println("Del Exception" + esc);
                }
            }
        });
    }



    public void setAccountTableView()
    {
        try
        {
            List<Account> accounts;

            accounts = accountDAO.getAllAccounts();

            accountmodel = new AccountTableModel(accounts);

            tableViewTable.setModel(accountmodel);


        }
        catch(Exception exc)
        {
            System.out.println(exc);
        }
    }

    public void updateAccountTableView()
    {
        try
        {
            
            List<Account> accounts;


            accounts = accountDAO.getAllAccounts();


            accountmodel = new AccountTableModel(accounts);


            accountmodel.fireTableDataChanged();
            //System.out.println("hereeee4e");
           tableViewTable.setModel(accountmodel); //This line throws the exception below...but is absolute necessary???
           // System.out.println("hereeee");



        }
        catch(Exception exc)
        {
            System.out.println("this is exception:" + exc);
        }
    }



    private void createUIComponents()
    {
        // TODO: place custom component creation code here
    }


}
