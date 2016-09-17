import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
    private JTable tableViewTable;
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
    private MachineDAO machineDAO;
    MachineTableModel machineModel;

    public MachinePage(TablesListModel tableList)
    {
        tableViewTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setMachineTableView();


       /* buttonNew.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Machine machine = machineDAO.newMachine();
                    System.out.println("ge1");
                    machineModel.addRow(machine);
                    System.out.println("ge2");
                    updateMachineTableView();
                    System.out.println("ge3");
                    tableList.setElementAt("Machines (" + machineDAO.getRowCount() + ")",1);

                } catch (Exception esc)
                {
                    System.out.println("thi " + esc);
                    esc.printStackTrace();
                }
            }
        });*/

        /*buttonDelete.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("selected row is " + tableViewTable.getSelectedRow());

                if(tableViewTable.getSelectedRow() == -1)
                    return;

                System.out.println("test1");

                try{


                    int machine_id = Integer.parseInt(tableViewTable.getModel().getValueAt(tableViewTable.getSelectedRow(),0).toString());
                    machineDAO.deleteMachine(machine_id);


                    machineModel.removeRow(tableViewTable.getSelectedRow()-1);
                    updateMachineTableView();
                    tableList.setElementAt("Machines (" + machineDAO.getRowCount() + ")",1);
                }
                catch(Exception esc)
                {
                    System.out.println("Del Exception" + esc);
                    esc.printStackTrace();
                }
            }
        });*/
    }
    public JButton getButtonRevert()
    {
        return buttonRevert;
    }

    public void setMachineTableView()
    {
        try
        {
            java.util.List<Machine> machines;
            machineDAO = new MachineDAO();
            machines = machineDAO.getAllMachines();
            machineModel = new MachineTableModel(machines);

            tableViewTable.setModel(machineModel);

        }
        catch(Exception exc)
        {
            System.out.println(exc);
        }


    }

    public void updateMachineTableView()
    {

        try
        {

            java.util.List<Machine> machines;
            machines = machineDAO.getAllMachines();
            machineModel = new MachineTableModel(machines);
            machineModel.fireTableDataChanged();

            System.out.println(tableViewTable.getRowCount());
            System.out.println(machineModel.getRowCount());


        }
        catch(Exception exc)
        {
            System.out.println("this is exception:" + exc);

        }
        tableViewTable.setModel(machineModel);

    }

    public void newMachine(TablesListModel tableList)
    {
        try
        {
            Machine machine = machineDAO.newMachine();
            System.out.println("ge1");
            machineModel.addRow(machine);
            System.out.println("ge2");
            updateMachineTableView();
            System.out.println("ge3");
            tableList.setElementAt("Machines (" + machineDAO.getRowCount() + ")",1);

        } catch (Exception esc)
        {
            System.out.println("thi " + esc);
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


            int machine_id = Integer.parseInt(tableViewTable.getModel().getValueAt(tableViewTable.getSelectedRow(),0).toString());
            machineDAO.deleteMachine(machine_id);


            machineModel.removeRow(tableViewTable.getSelectedRow()-1);
            updateMachineTableView();
            tableList.setElementAt("Machines (" + machineDAO.getRowCount() + ")",1);
        }
        catch(Exception esc)
        {
            System.out.println("Del Exception" + esc);
            esc.printStackTrace();
        }
    }

    public void save()
    {
        if (tableViewTable.getSelectedRow() == -1)
        {
            System.out.println("No row selected");
            return;
        }

        //for Machine
        //pull text from fields
        String machineType;
        String machineBrand;
        String machineModel;
        String machineAsset;
        machineType = formTypeField.getText();
        machineBrand = formBrandField.getText();
        machineModel = formModelField.getText();
        machineAsset = formAssetField.getText();
        System.out.println(machineType);
        System.out.println(machineBrand);

        //use fields to change info in database for row selection
        int machineID = Integer.parseInt(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 0).toString());
        try
        {
            machineDAO.updateMachine(machineID,machineType, machineBrand, machineModel, machineAsset);
        } catch (SQLException exc)
        {
            System.out.println("Unable to update machine in database " + exc);
        }
        updateMachineTableView();
    }
    public void revert()
    {
        System.out.println("Machine345345345t");
        //check if selection row
        if (tableViewTable.getSelectedRow() == -1)
        {
            System.out.println("No row selected");
            return;
        }

        //for Machine
        //pull fields from database
        int machineID = Integer.parseInt(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 0).toString());
        String machineType;
        String machineBrand;
        String machineModel;
        String machineAsset;
        try
        {
            machineType = machineDAO.getColumn(machineID, "type"); //query from id
            machineBrand = machineDAO.getColumn(machineID, "brand"); //query from id
            machineModel = machineDAO.getColumn(machineID, "model"); //query from id
            machineAsset = machineDAO.getColumn(machineID, "asset"); //query from id
        } catch (SQLException exc)
        {
            System.out.println("Unable to retrieve machine in database " + exc);
            return;
        }

        //set textfields to new strings
        System.out.println("machine= " + machineType + " Brand= " + machineBrand + " ID:" + machineID);
        formTypeField.setText(machineType);
        formBrandField.setText(machineBrand);
        formModelField.setText(machineModel);
        formAssetField.setText(machineAsset);


        System.out.println("Reverted");
    }

    public void setTextFields()
    {
        System.out.println("METHODMACHINE Selected Row == " + tableViewTable.getSelectedRow());
        if(tableViewTable.getSelectedRow() == -1)
        {
            formTypeField.setText("");
            formBrandField.setText("");
            formModelField.setText("");
            formAssetField.setText("");
            return;
        }

        System.out.println("The next line will fail the second trigger (Why trigger twice for Table?");
        //check nulls

        formTypeField.setText(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 1).toString());
        formBrandField.setText(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 2).toString());
        //System.out.println("/// +" + tableViewTable.getValueAt(tableViewTable.getSelectedRow(),2));
        //System.out.println("/// +" + tableViewTable.getValueAt(tableViewTable.getSelectedRow(),3));
        if(tableViewTable.getValueAt(tableViewTable.getSelectedRow(),3) != null)
            formModelField.setText(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 3).toString());
        else
            formModelField.setText("");
        //if(tableViewTable.getValueAt(tableViewTable.getSelectedRow(),4).toString().isEmpty())
        if(tableViewTable.getValueAt(tableViewTable.getSelectedRow(),4) != null)
            formAssetField.setText(tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 4).toString());
        else
            formAssetField.setText("");

        /*Object accountIdObj = tableViewTable.getValueAt(tableViewTable.getSelectedRow(), 0);
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
        }*/
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
        return tableViewTable;
    }
}
