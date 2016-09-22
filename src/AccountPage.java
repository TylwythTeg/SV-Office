import javax.swing.*;
import java.sql.SQLException;
import javax.swing.border.*;
import java.util.List;


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
    private JTable mainTable;
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
    private JButton filterButton;
    AccountTableModel accountModel;

    MachineTableModel machinemodel;

    private AccountDAO accountDAO;
    private MachineDAO machineDAO;




    public AccountPage()
    {
        mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        try
        {
            accountDAO = new AccountDAO();
            machineDAO = new MachineDAO();
        }
        catch (SQLException exc)
        {
            System.err.println("Error Creating Data Access Objects");
            System.err.println(exc);
        }


        setAccountTableView();

    }

    public void setAccountTableView()
    {
            List<Account> accounts;
            try
            {
                accounts = accountDAO.getAllAccounts();
                accountModel = new AccountTableModel(accounts);
            }
            catch(SQLException exc)
            {
                System.err.println("Error Setting/Updating Main Account Table");
                System.err.println(exc);
            }

            mainTable.setModel(accountModel);
    }

    public void newAccount(TablesListModel tableList)
    {
        try
        {
            Account account = accountDAO.newAccount();

            accountModel.addRow(account);
            setAccountTableView();
            tableList.setElementAt("Accounts (" + accountDAO.getRowCount() + ")",0);

        } catch (SQLException exc)
        {
            System.err.println(exc);
            exc.printStackTrace();
        }
    }

    public void delete(TablesListModel tableList)
    {
        System.out.println("selected row to be deleted is " + mainTable.getSelectedRow());

        if(mainTable.getSelectedRow() == -1)
            return;

        int accountID = Integer.parseInt(mainTable.getModel().getValueAt(mainTable.getSelectedRow(),0).toString());
        System.out.println("account_id:" + accountID);

        try
        {
            accountDAO.deleteAccount(accountID);

            accountModel.removeRow(mainTable.getSelectedRow()-1);
            setAccountTableView();
            tableList.setElementAt("Accounts (" + accountDAO.getRowCount() + ")",0);
        }
        catch(SQLException exc)
        {
            System.err.println("Del Exception " + exc);
            exc.printStackTrace();
        }
    }

    public void revert()
    {
        if (mainTable.getSelectedRow() == -1)
        {
            System.out.println("No row selected");
            return;
        }

        //for Account
        //This pull fields from database. Should we pull from the already instantiated table instead? It would be
        //one less SQL query but does it matter?
        int accountID = Integer.parseInt(mainTable.getValueAt(mainTable.getSelectedRow(), 0).toString());
        String accountName;
        String accountAddress;

        try
        {
            accountName = accountDAO.getColumn(accountID, "name"); //query name from id
            accountAddress = accountDAO.getColumn(accountID, "address"); //query address from id

        }
        catch (SQLException exc)
        {
            System.err.println("Unable to retrieve account in database " + exc);
            exc.printStackTrace();
            return;
        }


        System.out.println("Account= " + accountName + " Address= " + accountAddress + " ID:" + accountID);

        //set textfields to new strings
        formNameField.setText(accountName);
        formAddressField.setText(accountAddress);


        System.out.println("Reverted");
    }

    public void save()
    {
        if (mainTable.getSelectedRow() == -1)
        {
            System.out.println("No row selected");
            return;
        }

        //for Account
        //pull text from fields and update account in database
        String accountName = formNameField.getText();
        String accountAddress = formAddressField.getText();
        int accountID = Integer.parseInt(mainTable.getValueAt(mainTable.getSelectedRow(), 0).toString());

        System.out.println(accountName);
        System.out.println(accountAddress);

        try
        {
            accountDAO.updateAccount(accountID, accountName, accountAddress);
        }
        catch (SQLException exc)
        {
            System.err.println("Unable to update account in database " + exc);
            exc.printStackTrace();
        }

        setAccountTableView();
    }

    public void setPanel()
    {
        setTextFields();
        setMachineTable();
    }


    public void setTextFields()
    {
        System.out.println("Selected Row == " + mainTable.getSelectedRow());

        if(mainTable.getSelectedRow() == -1)
        {
            formNameField.setText("");
            formAddressField.setText("");
            tableFieldsPanel.setBorder(new TitledBorder("Account"));
            return;
        }

        System.out.println("The next line will fail the second trigger (Why trigger twice for Table?");

        formNameField.setText(mainTable.getValueAt(mainTable.getSelectedRow(), 1).toString());
        formAddressField.setText(mainTable.getValueAt(mainTable.getSelectedRow(), 2).toString());

        tableFieldsPanel.setBorder(new TitledBorder(mainTable.getValueAt(mainTable.getSelectedRow(), 1).toString()));

    }

    public void setMachineTable()
    {
        if (mainTable.getSelectedRow() == -1)
        {
            System.out.println("No row selected");
            List<Machine> machines = null;
            MachineTableModel machinemodel = new MachineTableModel(machines);
            machineTable.setModel(machinemodel);
            return;
        }

        Object accountIdObj = mainTable.getValueAt(mainTable.getSelectedRow(), 0);
        int accountID = (int) accountIdObj;

        List<Machine> machines = null;

        try
        {
            machines = machineDAO.getMachinesFromAccountId(accountID);
            MachineTableModel machinemodel = new MachineTableModel(machines);
            machineTable.setModel(machinemodel);

        } catch (SQLException exc)
        {
            System.err.println("Couldn't query machine list");
            System.err.println(exc);
            exc.printStackTrace();
        }
    }

    public JPanel getCard()
    {
        return accountPanel;
    }
    public JTable getAccountTable()
    {
        return mainTable;
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
