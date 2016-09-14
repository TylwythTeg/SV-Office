import javax.swing.*;
import java.util.List;

public class CardMachinePage
{
    private JPanel outerMain;
    protected JPanel window;
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
    private JButton buttonRevert;
    private JTextField formAssetField;
    private JTextField formModelField;
    private MachineDAO machineDAO;

    public CardMachinePage()
    {

        setMachineTableView();
    }

    public void setMachineTableView()
    {
        try
        {
            List<Machine> machines;

            machineDAO = new MachineDAO();
            machines = machineDAO.getAllMachines();
            MachineTableModel machineModel = new MachineTableModel(machines);

            tableViewTable.setModel(machineModel);
            System.out.println("gotmachines");

        }
        catch(Exception exc)
        {
            System.out.println("this " + exc);
            exc.printStackTrace();
        }


    }
}
