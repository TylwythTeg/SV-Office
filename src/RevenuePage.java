import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.toedter.calendar.JDateChooser;
import java.util.Date;

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
    private JComboBox accountFilterBox;
    private JButton filterButton;
    private JPanel tableFieldsPanel;
    private JDateChooser formDateField;
    private JTextField formMoneyField;
    private JLabel labelRevenue;
    private JButton buttonSave;
    private JButton buttonRevert;
    private JComboBox locationDropDown;
    private JComboBox sortFilterBox;
    private JComboBox dateFilterBox;

    private AccountDAO accountDAO;
    private MachineDAO machineDAO;
    private RevenueDAO revenueDAO;

    private RevenueTableModel revenueModel;

    public RevenuePage()
    {
        mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //accountFilterBox.setActionCommand

        try
        {
            accountDAO = new AccountDAO();
            machineDAO = new MachineDAO();
            revenueDAO = new RevenueDAO();
        }
        catch (SQLException exc)
        {
            System.err.println("Error Creating Data Access Objects");
            System.err.println(exc);
        }

        setRevenueTableView();
        populateDropDowns();

    }

    public void populateDropDowns()
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

        list.remove(0);
        list.add(0,"All (By Account)");
        list.add(1,"All (By Date)");
        list.add(2,"All (Summary)");
        accountFilterBox.setModel(new DefaultComboBoxModel(list.toArray()));

        //sort drop down
        //List<String> sortList = new ArrayList<>();
        //sortList.add("");
        //sortList.add("Account");
        //sortList.add("Date");
        //sortFilterBox.setModel(new DefaultComboBoxModel(sortList.toArray()));

        //date drop down
        List<String> dateList = new ArrayList<>();
        dateList.add("");
        dateList.add("Day");
        dateList.add("Week");
        dateList.add("Month");
        dateList.add("Year");
        dateFilterBox.setModel(new DefaultComboBoxModel(dateList.toArray()));

    }

    public void setRevenueTableView()
    {
        List<RevenueLog> logs;
        try
        {
            logs = revenueDAO.getAllLogs();

            revenueModel = new RevenueTableModel(logs);
        }
        catch(SQLException exc)
        {
            System.err.println("Error Setting/Updating Main Revenue Table");
            System.err.println(exc);
        }

        mainTable.setModel(revenueModel);
    }

    public void newLog(TablesListModel tableList)
    {
        try
        {
            RevenueLog log = revenueDAO.newLog();
            revenueModel.addRow(log);
            setRevenueTableView();

            tableList.setElementAt("Revenue (" + revenueDAO.getRowCount() + ")",2);

        }
        catch (SQLException exc)
        {
            System.err.println("Error creating new log " + exc);
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

        //for REVENUE
        //pull text from fields and update in database
        int accountID = -1;

        int logID = Integer.parseInt(mainTable.getValueAt(mainTable.getSelectedRow(), 1).toString());
        Date logDate = formDateField.getDate();
        Double logMoney = Double.parseDouble(formMoneyField.getText());


        String accountName = (String)locationDropDown.getSelectedItem(); //note, don't have two accounts with the same name. But that's a bad idea anyway.
        try
        {
            if(!accountName.isEmpty())
            {
                accountID = accountDAO.getIdFromName(accountName);
                revenueDAO.updateRevenueLog(logID,logDate, logMoney, accountID);
            }
            else
                revenueDAO.updateRevenueLog(logID,logDate,logMoney, null);
        }
        catch(SQLException exc)
        {
            System.err.println("Unable to update Log in database");
            System.err.println(exc);
            exc.printStackTrace();
        }

        setRevenueTableView();
    }

    public void revert()
    {
        //check if selection row
        if (mainTable.getSelectedRow() == -1)
        {
            System.out.println("No row selected");
            return;
        }

        //for RevenueLog
        //pull fields from database
        int logID = Integer.parseInt(mainTable.getValueAt(mainTable.getSelectedRow(), 1).toString());
        Date logDate;
        Double logMoney;
        String accountName;
        try
        {
            //Queries database. Would it be more efficient to check values in table?
            RevenueLog log = revenueDAO.getRevenueLog(logID);

            logDate = (Date)revenueDAO.getDate(logID); //query from id
            logMoney = Double.parseDouble((revenueDAO.getColumn(logID, "money"))); //query from id
            accountName = revenueDAO.getAccountName(log);
        }
        catch (SQLException exc)
        {
            System.out.println("Unable to retrieve log in database " + exc);
            exc.printStackTrace();
            return;
        }


        System.out.println("revenue log= " + logDate + " Money= " + logMoney + " ID:" + logID);
        //set textfields to new strings
        formDateField.setDate(logDate);
        formMoneyField.setText(logMoney.toString());
        locationDropDown.setSelectedItem(accountName);

        System.out.println("Reverted");
    }

    public void setFields()
    {
        System.out.println("METHOD LOG Selected Row == " + mainTable.getSelectedRow());

        if(mainTable.getSelectedRow() == -1)
        {
            formDateField.setDate(null);
            formMoneyField.setText("");
            locationDropDown.setSelectedIndex(0);
            return;
        }

        System.out.println("The next line will fail the second trigger (Why trigger twice for Table?)");
        //check nulls


        formDateField.setDate((Date)mainTable.getValueAt(mainTable.getSelectedRow(), 2));

        System.out.println("sdfdsf" + (Date)mainTable.getValueAt(mainTable.getSelectedRow(), 2));

        formMoneyField.setText(mainTable.getValueAt(mainTable.getSelectedRow(), 3).toString());

    }

    public void setDropDown()
    {
        if(mainTable.getSelectedRow() == -1)
        {
            formDateField.setDate(null);
            formMoneyField.setText("");
            locationDropDown.setSelectedIndex(0);
            return;
        }

        System.out.println("setDropDown selectedRow is " + mainTable.getSelectedRow());

        if(mainTable.getValueAt(mainTable.getSelectedRow(),0) == null)
        {
            locationDropDown.setSelectedIndex(0);
            return;
        }
        locationDropDown.setSelectedItem(mainTable.getValueAt(mainTable.getSelectedRow(),0));

    }

    public void filter()
    {
        String accountName = "";
        accountName = (String)accountFilterBox.getSelectedItem();

        System.out.println("Selected item is " + accountName);

        if(accountName == "All (By Account)")
        {
            //setRevenueTableView();
            //List<RevenueLog> logs;

            filterAll("list");


        }
        else if(accountName == "All (By Date)")
        {
            filterAll("listbydate");
        }
        else if(accountName == "All (Summary)")
        {
            filterAll("compact");
        }
        else if(!accountName.isEmpty())
        {
            /*List<RevenueLog> logs;
            int accountID = -1;
            try
            {
                accountID = accountDAO.getIdFromName(accountName);
                logs = revenueDAO.getLogsFromAccount(accountID);
            }
            catch(SQLException exc)
            {
                System.err.println(exc);
                return;
            }

            revenueModel = new RevenueTableModel(logs);
            mainTable.setModel(revenueModel);*/

            filterAll("list");

        }
        //else if(f)
        else if(accountName.isEmpty())
        {
            setRevenueTableView();
        }


    }

    public void filterAll(String mode)
    {

        String accountName = "";
        accountName = (String)accountFilterBox.getSelectedItem();
        int accountID = -1;


        List<RevenueLog> logs;
        try
        {
            logs = revenueDAO.getAllLogs();

            switch((String)dateFilterBox.getSelectedItem())
            {
                case "Year":
                    if(mode == "compact")
                        logs = revenueDAO.getAllLogs("date");
                    else if(!accountName.isEmpty() && accountName != "All (By Account)" && accountName != "All (By Date")
                    {
                        accountID = accountDAO.getIdFromName(accountName);
                        logs = revenueDAO.getLogsFromAccount(accountID);
                    }
                    logs = RevenueLog.consolidateByYear(logs, mode);
                    revenueModel = new RevenueTableModel(logs);
                    revenueModel.setAccountListType(mode);
                    revenueModel.setConsolidated(Consolidated.YEAR);
                    break;
                case "Month":
                    if(mode == "compact")
                        logs = revenueDAO.getAllLogs("date");
                    else if(mode == "listbydate")
                        logs = revenueDAO.getAllLogs("Month");
                    else if(!accountName.isEmpty() && accountName != "All (By Account)" && accountName != "All (By Date")
                    {
                        accountID = accountDAO.getIdFromName(accountName);
                        logs = revenueDAO.getLogsFromAccount(accountID);
                    }
                    logs = RevenueLog.consolidateByMonth(logs, mode);
                    revenueModel = new RevenueTableModel(logs);
                    revenueModel.setAccountListType(mode);
                    revenueModel.setConsolidated(Consolidated.MONTH);
                    break;
                case "Week":
                    if(mode == "compact")
                        logs = revenueDAO.getAllLogs("date");
                    else if(!accountName.isEmpty() && accountName != "All (By Account)" && accountName != "All (By Date")
                    {
                        accountID = accountDAO.getIdFromName(accountName);
                        logs = revenueDAO.getLogsFromAccount(accountID);
                    }
                    logs = RevenueLog.consolidateByWeek(logs ,mode);
                    revenueModel = new RevenueTableModel(logs);
                    revenueModel.setAccountListType(mode);
                    revenueModel.setConsolidated(Consolidated.WEEK);
                    break;
                case "Day":
                case "":
                default:
                    if(mode == "compact")
                    {
                        logs = revenueDAO.getAllLogs("date");
                        logs = RevenueLog.consolidateByDay(logs,mode);
                    }
                    else if(!accountName.isEmpty() && (accountName != "All (By Account)" && accountName != "All (By Date"))
                    {
                        accountID = accountDAO.getIdFromName(accountName);
                        logs = revenueDAO.getLogsFromAccount(accountID);
                    }

                    revenueModel = new RevenueTableModel(logs);
                    revenueModel.setAccountListType(mode);
                    revenueModel.setConsolidated(Consolidated.NONE);

            }

        }
        catch(SQLException exc)
        {
            System.err.println(exc);
            exc.printStackTrace();
            return;
        }


        mainTable.setModel(revenueModel);


    }











    public JPanel getCard()
    {
        return revenuePanel;
    }
    public JButton getNewButton()
    {
        return buttonNew;
    }
    public JTable getRevenueTable()
    {
        return mainTable;
    }
    public JButton getRevertButton()
    {
        return buttonRevert;
    }
    public JButton getSaveButton()
    {
        return buttonSave;
    }
    public JButton getFilterButton() { return filterButton; }

}
