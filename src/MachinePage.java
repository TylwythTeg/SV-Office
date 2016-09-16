import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


        buttonNew.addActionListener(new ActionListener()
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
        });
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

    public JPanel getCard()
    {
        return machinePanel;
    }
    public JButton getRevertButton()
    {
        return buttonRevert;
    }
    public JTable getMachineTable()
    {
        return tableViewTable;
    }
}
