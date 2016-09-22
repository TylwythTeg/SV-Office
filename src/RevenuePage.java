import javax.swing.*;
import java.sql.SQLException;

public class RevenuePage
{
    private JPanel outerMain;
    private JPanel window;
    private JPanel listPanel;
    private JList listForSections;
    private JPanel revenuePanel;
    private JPanel tablePanelEnclosure;
    private JPanel tableButtonPanel;
    private JButton buttonDelete;
    private JButton buttonNew;
    private JPanel panelForTable;
    private JScrollPane rowListScrollPane;
    private JTable mainTable;
    private JComboBox nameFilterTextArea;
    private JButton filterButton;
    private JPanel tableFieldsPanel;
    private JTextField formTypeField;
    private JTextField formBrandField;
    private JLabel labelRevenue;
    private JButton buttonSave;
    private JButton buttonRevert;
    private JComboBox locationDropDown;

    private AccountDAO accountDAO;
    private MachineDAO machineDAO;

    public RevenuePage()
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


        //setRevenueTableView();

    }











    public JPanel getCard()
    {
        return revenuePanel;
    }

}
