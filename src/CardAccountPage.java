import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.CardLayout;
import java.util.*;

/**
 * Created by Rob on 9/12/2016.
 */
public class CardAccountPage
{
    private JPanel outerMain;
    protected JPanel window; //allow use in SV_Office
    private JPanel listPanel;
    private JList listForSections;
    private JPanel tablePanelEnclosure;
    private JPanel tableButtonPanel;
    private JButton buttonDelete;
    private JButton buttonNew;
    private JPanel panelForTable;
    private JScrollPane rowListScrollPane;
    private JTable tableViewTable;
    private JTextField nameFilterTextArea;
    private JPanel tableFieldsPanel;
    private JTextField formNameField;
    private JTextField formAddressField;
    private JLabel labelRevenue;
    private JScrollPane machineScrollPane;
    private JTable machineTable;
    private JButton buttonSave;
    private JButton buttonRevert;
    AccountTableModel accountModel;
    private AccountDAO accountDAO;
    private MachineDAO machineDAO;


    public void setTableList() throws Exception
    {
        //Will want to edit list elements rather than creating entirely new Objects when updating
        TablesListModel tableList = new TablesListModel();
        tableList.initList(accountDAO.getRowCount());

        listForSections.setModel(tableList);    //This may throw a nullpointer Exception. Why???;
    }


    public CardAccountPage(CardLayout cardLayout, JPanel cardContainer)
    {
        listForSections.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableViewTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //System.out.println(frame);
        try
        {
            accountDAO = new AccountDAO();

            setTableList();

            machineDAO = new MachineDAO();
        } catch (Exception exc)
        {
            System.out.println("Error Creating AccountDAO");
        }


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

                System.out.println("Selected Row == " + tableViewTable.getSelectedRow());
                if(tableViewTable.getSelectedRow() == -1)
                {
                    formNameField.setText("");
                    formAddressField.setText("");
                    return;
                }
                //cardLayout.show(cardContainer,"Machine");


                System.out.println("The next line will fail the second trigger (Why trigger twice for Table?");
                formNameField.setText(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 1).toString());
                formAddressField.setText(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 2).toString());

                Object accountIdObj = tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 0);
                int account_id = (int) accountIdObj;


                java.util.List<Machine> machines = null;

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

        listForSections.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                System.out.println("In list response");

                if (listForSections.getValueIsAdjusting() || listForSections.getSelectedValue() == null)
                    return;

                if(tableViewTable.getSelectedRow() != -1)
                    tableViewTable.clearSelection();



                String selection = listForSections.getSelectedValue().toString();


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

                    accountModel.addRow(account);
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

                if(tableViewTable.getSelectedRow() == -1)
                    return;

                System.out.println("test1");

                try{


                    int account_id = Integer.parseInt(tableViewTable.getModel().getValueAt(tableViewTable.getSelectedRow(),0).toString());
                    accountDAO.deleteAccount(account_id);


                    accountModel.removeRow(tableViewTable.getSelectedRow()-1);
                    updateAccountTableView();
                }
                catch(Exception esc)
                {
                    System.out.println("Del Exception" + esc);
                    esc.printStackTrace();
                }
            }
        });

        buttonSave.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //check if selection row
                if (tableViewTable.getSelectedRow() == -1)
                {
                    System.out.println("No row selected");
                    return;
                }

                //for Account
                //pull text from fields
                String accountName;
                String accountAddress;
                accountName = formNameField.getText();
                accountAddress = formAddressField.getText();
                System.out.println(accountName);
                System.out.println(accountAddress);

                //use fields to change info in database for row selection
                int accountID = Integer.parseInt(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 0).toString());
                try
                {
                    accountDAO.updateAccount(accountID, accountName, accountAddress);
                } catch (SQLException exc)
                {
                    System.out.println("Unable to update account in database " + exc);
                }
                updateAccountTableView();


            }
        });
        buttonRevert.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //check if selection row
                if (tableViewTable.getSelectedRow() == -1)
                {
                    System.out.println("No row selected");
                    return;
                }

                //for Account
                //pull fields from database
                int accountID = Integer.parseInt(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 0).toString());
                String accountName;
                String accountAddress;
                try
                {
                    accountName = accountDAO.getColumn(accountID, "name"); //query name from id
                    accountAddress = accountDAO.getColumn(accountID, "address"); //query address from id
                } catch (SQLException exc)
                {
                    System.out.println("Unable to retrieve account in database " + exc);
                    return;
                }

                //set textfields to new strings
                System.out.println("Account= " + accountName + " Address= " + accountAddress + " ID:" + accountID);
                formNameField.setText(accountName);
                formAddressField.setText(accountAddress);


                System.out.println("Reverted");
            }
        });
    }



    public void setAccountTableView()
    {
        try
        {
            java.util.List<Account> accounts;

            accounts = accountDAO.getAllAccounts();
            accountModel = new AccountTableModel(accounts);

            tableViewTable.setModel(accountModel);

            //window.repaint();

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

            java.util.List<Account> accounts;
            accounts = accountDAO.getAllAccounts();
            accountModel = new AccountTableModel(accounts);
            accountModel.fireTableDataChanged();

            System.out.println(tableViewTable.getRowCount());
            System.out.println(accountModel.getRowCount());
            setTableList();

        }
        catch(Exception exc)
        {
            System.out.println("this is exception:" + exc);

        }
        tableViewTable.setModel(accountModel);

    }
}
