import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.List.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.CardLayout;
import java.util.*;
import javax.swing.border.*;


public class AccountPage
{
    private JPanel outerMain;
    private JPanel window;
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
    private JPanel accountPanel;
    AccountTableModel accountModel;
    private AccountDAO accountDAO;
    private MachineDAO machineDAO;
    MachineTableModel machinemodel;




    public AccountPage(TablesListModel tableList)
    {
        tableViewTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //machinemodel = new MachineTableModel();
        try
        {
            accountDAO = new AccountDAO();
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


        }
        catch(Exception exc)
        {
            System.out.println("this is exception:" + exc);

        }
        tableViewTable.setModel(accountModel);

    }

    public void newAccount(TablesListModel tableList)
    {
        try
        {
            Account account = accountDAO.newAccount();

            accountModel.addRow(account);
            updateAccountTableView();
            tableList.setElementAt("Accounts (" + accountDAO.getRowCount() + ")",0);

        } catch (Exception esc)
        {
            System.out.println("psh");
            System.out.println(esc);
            esc.printStackTrace();
        }
    }
    public void delete(TablesListModel tableList)
    {
        System.out.println("selected row is " + tableViewTable.getSelectedRow());

        if(tableViewTable.getSelectedRow() == -1)
            return;

        System.out.println("test1");

        try{


            int account_id = Integer.parseInt(tableViewTable.getModel().getValueAt(tableViewTable.getSelectedRow(),0).toString());
            System.out.println("accountid:" + account_id);
            accountDAO.deleteAccount(account_id);


            accountModel.removeRow(tableViewTable.getSelectedRow()-1);
            updateAccountTableView();
            tableList.setElementAt("Accounts (" + accountDAO.getRowCount() + ")",0);
        }
        catch(Exception esc)
        {
            System.out.println("Del Exception" + esc);
            esc.printStackTrace();
        }
    }

    public void revert()
    {
        System.out.println("Accoun345345345t");
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

    public void save()
    {
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

    public void setPanel()
    {
        setTextFields();
        setMachineTable();
    }



    public void setTextFields()
    {
        System.out.println("METHOD Selected Row == " + tableViewTable.getSelectedRow());
        if(tableViewTable.getSelectedRow() == -1)
        {
            formNameField.setText("");
            formAddressField.setText("");
            tableFieldsPanel.setBorder(new TitledBorder("Account"));
            setMachineTable();
            return;
        }

        System.out.println("The next line will fail the second trigger (Why trigger twice for Table?");
        formNameField.setText(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 1).toString());
        formAddressField.setText(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 2).toString());

        tableFieldsPanel.setBorder(new TitledBorder(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 1).toString()));

    }

    public void setMachineTable()
    {
        if (tableViewTable.getSelectedRow() == -1)
        {
            System.out.println("No row selected");
            java.util.List<Machine> machines = null;
            MachineTableModel machinemodel = new MachineTableModel(machines);
            machineTable.setModel(machinemodel);
            return;
        }

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

    public JPanel getCard()
    {
        return accountPanel;
    }
    public JTable getAccountTable()
    {
        return tableViewTable;
    }
    public JTextField getNameField()
    {
        return formNameField;
    }
    public JTextField getAddressField()
    {
        return formAddressField;
    }
    public JButton getRevertButton()
    {
        return buttonRevert;
    }
    public JButton getSaveButton()
    {
        return buttonSave;
    }
    public JButton getNewButton()
    {
        return buttonNew;
    }
    public JButton getDeleteButton()
    {
        return buttonDelete;
    }


}
