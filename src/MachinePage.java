import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachinePage
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
    private JTextField formTypeField;
    private JTextField formBrandField;
    private JLabel labelRevenue;
    private JButton buttonSave;
    private JButton buttonRevert; ///////////////
    private JTextField formAssetField;
    private JTextField formModelField;
    private JPanel machinePanel;
    private JComboBox locationDropDown;
    private MachineDAO machineDAO;
    private AccountDAO accountDAO;
    MachineTableModel machineModel;



    public MachinePage()
    {
        mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        try
        {
            accountDAO = new AccountDAO();
            machineDAO = new MachineDAO();
        }
        catch(SQLException exc)
        {
            System.err.println("Error Creating Data Access Objects");
            System.err.println(exc);
        }


        setMachineTableView();
        populateDropDown();

    }

    public void populateDropDown()
    {
        List<String> list = new ArrayList<>();

        try
        {
            list = accountDAO.getAllAccountNames();
        }
        catch(SQLException exc)
        {
            System.err.println("Error Populating dropdown");
            System.err.println(exc);
        }

        list.add(0, "");
        locationDropDown.setModel(new DefaultComboBoxModel(list.toArray()));
    }



    public void setMachineTableView()
    {
        List<Machine> machines;
        try
        {
            machines = machineDAO.getAllMachines();
            machineModel = new MachineTableModel(machines);
        }
        catch(SQLException exc)
        {
            System.err.println("Error Setting/Updating Main Machine Table");
            System.err.println(exc);
        }
        mainTable.setModel(machineModel);

    }

    public void newMachine(TablesListModel tableList)
    {
        try
        {
            Machine machine = machineDAO.newMachine();
            machineModel.addRow(machine);
            setMachineTableView();

            tableList.setElementAt("Machines (" + machineDAO.getRowCount() + ")",1);

        }
        catch (SQLException exc)
        {
            System.err.println("Error creating new machine " + exc);
            exc.printStackTrace();
        }
    }

    public void delete(TablesListModel tableList)
    {
        System.out.println("selected row is " + mainTable.getSelectedRow());

        if(mainTable.getSelectedRow() == -1)
            return;

        int machineID = Integer.parseInt(mainTable.getModel().getValueAt(mainTable.getSelectedRow(),0).toString());
        try
        {
            machineDAO.deleteMachine(machineID);

            machineModel.removeRow(mainTable.getSelectedRow()-1);
            setMachineTableView();
            tableList.setElementAt("Machines (" + machineDAO.getRowCount() + ")",1);
        }
        catch(SQLException exc)
        {
            System.err.println("Del Exception " + exc);
            exc.printStackTrace();
        }
    }

    public void save()
    {
        if (mainTable.getSelectedRow() == -1)
        {
            System.out.println("No row selected");
            return;
        }

        //for Machine
        //pull text from fields and update in database
        int accountID = -1;

        int machineID = Integer.parseInt(mainTable.getValueAt(mainTable.getSelectedRow(), 0).toString());
        String machineType = formTypeField.getText();
        String machineBrand = formBrandField.getText();
        String machineModel = formModelField.getText();
        String machineAsset = formAssetField.getText();

        String accountName = (String)locationDropDown.getSelectedItem(); //note, don't have two accounts with the same name. But that's a bad idea anyway.
        try
        {
            if(!accountName.isEmpty())
            {
                accountID = accountDAO.getIdFromName(accountName);
                machineDAO.updateMachine(machineID,machineType, machineBrand, machineModel, machineAsset, accountID);
            }
            else
                machineDAO.updateMachine(machineID,machineType,machineBrand,machineModel,machineAsset, null);
        }
        catch(SQLException exc)
        {
            System.err.println("Unable to update Machine in database");
            System.err.println(exc);
            exc.printStackTrace();
        }

        setMachineTableView();
    }

    public void revert()
    {
        //check if selection row
        if (mainTable.getSelectedRow() == -1)
        {
            System.out.println("No row selected");
            return;
        }

        //for Machine
        //pull fields from database
        int machineID = Integer.parseInt(mainTable.getValueAt(mainTable.getSelectedRow(), 0).toString());
        String machineType;
        String machineBrand;
        String machineModel;
        String machineAsset;
        String accountName;
        try
        {
            //Queries database. Would it be more efficient to check values in table?
            Machine machine = machineDAO.getMachine(machineID);

            machineType = machineDAO.getColumn(machineID, "type"); //query from id
            machineBrand = machineDAO.getColumn(machineID, "brand"); //query from id
            machineModel = machineDAO.getColumn(machineID, "model"); //query from id
            machineAsset = machineDAO.getColumn(machineID, "asset"); //query from id
            accountName = machineDAO.getAccountName(machine);
        }
        catch (SQLException exc)
        {
            System.out.println("Unable to retrieve machine in database " + exc);
            exc.printStackTrace();
            return;
        }


        System.out.println("machine= " + machineType + " Brand= " + machineBrand + " ID:" + machineID);
        //set textfields to new strings
        formTypeField.setText(machineType);
        formBrandField.setText(machineBrand);
        formModelField.setText(machineModel);
        formAssetField.setText(machineAsset);
        locationDropDown.setSelectedItem(accountName);


        System.out.println("Reverted");
    }

    public void setTextFields()
    {
        System.out.println("METHODMACHINE Selected Row == " + mainTable.getSelectedRow());

        if(mainTable.getSelectedRow() == -1)
        {
            formTypeField.setText("");
            formBrandField.setText("");
            formModelField.setText("");
            formAssetField.setText("");
            locationDropDown.setSelectedIndex(0);
            return;
        }

        System.out.println("The next line will fail the second trigger (Why trigger twice for Table?");
        //check nulls

        formTypeField.setText(mainTable.getValueAt(mainTable.getSelectedRow(), 1).toString());
        formBrandField.setText(mainTable.getValueAt(mainTable.getSelectedRow(), 2).toString());

        if(mainTable.getValueAt(mainTable.getSelectedRow(),3) != null)
            formModelField.setText(mainTable.getValueAt(mainTable.getSelectedRow(), 3).toString());
        else
            formModelField.setText("");

        if(mainTable.getValueAt(mainTable.getSelectedRow(),4) != null)
            formAssetField.setText(mainTable.getValueAt(mainTable.getSelectedRow(), 4).toString());
        else
            formAssetField.setText("");

    }

    public void setDropDown()
    {
        if(mainTable.getSelectedRow() == -1)
        {
            formTypeField.setText("");
            formBrandField.setText("");
            formModelField.setText("");
            formAssetField.setText("");
            locationDropDown.setSelectedIndex(0);
            return;
        }

        System.out.println("setDropDown selectedRow is " + mainTable.getSelectedRow());

        if(mainTable.getValueAt(mainTable.getSelectedRow(),5) == null)
        {
            locationDropDown.setSelectedIndex(0);
            return;
        }
        locationDropDown.setSelectedItem(mainTable.getValueAt(mainTable.getSelectedRow(),5));

    }

    public JPanel getCard()
    {
        return machinePanel;
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
    public JTable getMachineTable()
    {
        return mainTable;
    }
    public JComboBox getDropDown() { return locationDropDown; }
}
