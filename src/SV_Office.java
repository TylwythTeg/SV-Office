import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;


public class SV_Office
{
    private static JFrame frame;
    private JPanel window;
    private JList listForSections;
    private JPanel cardContainer;
    private static JPanel cardAccountPage;
    private static JPanel cardMachinePage;
    private static JPanel cardRevenuePage;
    private static AccountPage accountPage;
    private static MachinePage machinePage;
    private static RevenuePage revenuePage;

    private static SimpleCardLayout cardLayout;

    private AccountDAO accountDAO;
    private MachineDAO machineDAO;

    private TablesListModel tableList;



    public static void main(String[] args)
    {
        /*try{
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception exc)
        {

        }*/
        frame = new JFrame("SV_Office");
        frame.setContentPane(new SV_Office().window);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("SV Office Alpha");
        frame.pack();
        frame.setVisible(true);
    }

    public SV_Office()
    {
        setCards();

        listForSections.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                System.out.println(e.getSource().getClass());

                if (listForSections.getValueIsAdjusting() || listForSections.getSelectedValue() == null)
                    return;
                //listForSections.getSource

                String selection = listForSections.getSelectedValue().toString();


                if (selection.startsWith("Account"))
                {
                    System.out.println("You have Selected Account");
                    cardLayout.show(cardContainer, "Account");
                    cardLayout.setVisible("Account");
                }
                if (selection.startsWith("Machine"))
                {
                    System.out.println("You have Selected Machine");
                    cardLayout.show(cardContainer, "Machine");
                    cardLayout.setVisible("Machine");
                }
                if (selection.startsWith("Revenue"))
                {
                    System.out.println("You have Selected Revenue");
                    cardLayout.show(cardContainer, "Revenue");
                    cardLayout.setVisible("Revenue");
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
    }

    public void setCards()
    {
        try
        {
            setTableList();
        } catch (Exception exc)
        {
            System.out.println(exc);
        }

        cardContainer.setLayout(new SimpleCardLayout());
        cardLayout = (SimpleCardLayout) (cardContainer.getLayout());

        System.out.println(cardLayout);
        System.out.println(cardContainer.getLayout());

        accountPage = new AccountPage();
        machinePage = new MachinePage();
        revenuePage = new RevenuePage();
        cardAccountPage = accountPage.getCard();
        cardMachinePage = machinePage.getCard();
        cardRevenuePage = revenuePage.getCard();


        //add JPanel pages to cardContainer JPanel
        cardContainer.add(cardAccountPage, "Account");
        cardContainer.add(cardMachinePage, "Machine");
        cardContainer.add(cardRevenuePage, "Revenue");

        System.out.println(cardContainer.getComponents());


        //first layout view is Accounts
        cardLayout.show(cardContainer, "Account");
        cardLayout.setVisible("Account");

        ActionListener actionListener = new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println(e.getActionCommand());
                switch(e.getActionCommand())
                {
                    case "New...":
                        newEntry();
                        break;
                    case "Delete":
                        delete();
                        break;
                    case "Revert":
                        revert();
                        break;
                    case "Save":
                        save();
                        break;
                    case "Filter":
                        filter();
                        break;
                }
            }
        };


        //will the subTables need a new listener? otherwise how will I be able to tell what they are?
        //This is for the main table (tableViewTable) for each cardpanel
        ListSelectionListener listSelectionListener = new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {

                switch(cardLayout.getVisible())
                {
                    case "Account":
                        accountPage.setPanel();
                        break;
                    case "Machine":
                        machinePage.setTextFields();
                        machinePage.setDropDown();
                        break;
                    case "Revenue":
                        revenuePage.setFields();
                        revenuePage.setDropDown();


                }

            }

        };

        accountPage.getNewButton().addActionListener(actionListener);
        accountPage.getDeleteButton().addActionListener(actionListener);
        accountPage.getSaveButton().addActionListener(actionListener);
        accountPage.getRevertButton().addActionListener(actionListener);


        machinePage.getNewButton().addActionListener(actionListener);
        machinePage.getDeleteButton().addActionListener(actionListener);
        machinePage.getSaveButton().addActionListener(actionListener);
        machinePage.getRevertButton().addActionListener(actionListener);

        revenuePage.getNewButton().addActionListener(actionListener);
        revenuePage.getSaveButton().addActionListener(actionListener);
        revenuePage.getRevertButton().addActionListener(actionListener);
        revenuePage.getFilterButton().addActionListener(actionListener);

        accountPage.getAccountTable().getSelectionModel().addListSelectionListener(listSelectionListener);
        machinePage.getMachineTable().getSelectionModel().addListSelectionListener(listSelectionListener);
        revenuePage.getRevenueTable().getSelectionModel().addListSelectionListener(listSelectionListener);

        machinePage.getDropDown().addActionListener(actionListener);



    }






    public void setTableList() throws Exception
    {
        //Will want to edit list elements rather than creating entirely new Objects when updating
        tableList = new TablesListModel();
        accountDAO = new AccountDAO();
        machineDAO = new MachineDAO();
        tableList.initList(accountDAO.getRowCount(), machineDAO.getRowCount());

        listForSections.setModel(tableList);    //This may throw a nullpointer Exception. Why???;
        //tableList.setElementAt("hey",0); //I just need to inject the tableListModel to client codes
        //tableListModel would have methods such as tableList.setAccountsNum() tableList.setMachinesNum()
    }

    public void newEntry()
    {
        System.out.println("check" + cardLayout.getVisible());
        switch(cardLayout.getVisible())
        {
            case "Account":
                accountPage.newAccount(tableList);
                machinePage.populateDropDown();
                revenuePage.populateDropDowns();
                break;
            case "Machine":
                machinePage.newMachine(tableList);
                break;
            case "Revenue":
                revenuePage.newLog(tableList);
                break;
        }
    }

    public void delete()
    {
        System.out.println("check" + cardLayout.getVisible());
        switch(cardLayout.getVisible())
        {
            case "Account":
                System.out.println("Going to delete");
                accountPage.delete(tableList);
                break;
            case "Machine":
                machinePage.delete(tableList);
                break;
        }
    }



    public void revert()
    {
        System.out.println("check" + cardLayout.getVisible());
        switch(cardLayout.getVisible())
        {
            case "Account":
                accountPage.revert();
                break;
            case "Machine":
                machinePage.revert();
                break;
            case "Revenue":
                revenuePage.revert();
                break;
        }
    }

    public void save()
    {
        System.out.println("check" + cardLayout.getVisible());
        switch(cardLayout.getVisible())
        {
            case "Account":
                accountPage.save();
                machinePage.populateDropDown();
                machinePage.setDropDown();
                revenuePage.populateDropDowns();
                revenuePage.setDropDown();
                break;
            case "Machine":
                machinePage.save();
                accountPage.setMachineTable();
                break;
            case "Revenue":
                revenuePage.save();
                //accountPage.setRevenueTable();
                break;
        }
    }

    public void filter()
    {
        System.out.println("check" + cardLayout.getVisible());
        switch(cardLayout.getVisible())
        {
            case "Account":

                break;
            case "Machine":
                break;
            case "Revenue":
                revenuePage.filter();
                break;
        }
    }


}
