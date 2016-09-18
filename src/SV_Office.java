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
    private static AccountPage accountPage; //remove card from these class names
    private static MachinePage machinePage;

    private static SimpleCardLayout cardLayout;

    private AccountDAO accountDAO;
    private MachineDAO machineDAO; //

    private TablesListModel tableList;



    public static void main(String[] args)
    {
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
    }

    public void setCards()
    {
        //may have to pull from origin unless I can figure out how to deal with class interconnectivity
        try
        {
            setTableList();
        } catch (Exception exc)
        {

        }

        //set card layout
        //cardLayout = (CardLayout) (cardContainer.getLayout());
        cardContainer.setLayout(new SimpleCardLayout());
        cardLayout = (SimpleCardLayout) (cardContainer.getLayout());
        //SimpleCardLayout cardLayout1 = (SimpleCardLayout) (cardContainer.getLayout());
        System.out.println(cardLayout);
        System.out.println(cardContainer.getLayout());

        accountPage = new AccountPage(tableList);
        machinePage = new MachinePage(tableList);
        cardAccountPage = accountPage.getCard();
        cardMachinePage = machinePage.getCard();


        //cards add/ add Jpanels to cardContainer
        cardContainer.add(cardAccountPage, "Account");
        cardContainer.add(cardMachinePage, "Machine");

        System.out.println(cardContainer.getComponents());


        //first layout view is Accounts
        cardLayout.show(cardContainer, "Account");
        cardLayout.setVisible("Account");


        //MyActionListener event = new MyActionListener(cardLayout, cardContainer);
        //machinePage.buttonRevert.addActionListener(event);

        //just do here

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
                }
            }
        };


        //will the subTables need a new listener? otherwise how will I be able to tell what they are?
        ListSelectionListener listSelectionListener = new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                //if(listSelectionListener.getV)
                //listSelectionListener.get
                //if(accountPage.getAccountTable().getValueIs)

                switch(cardLayout.getVisible())
                {
                    case "Account":
                        accountPage.setTextFields();
                        break;
                    case "Machine":
                        machinePage.setTextFields();
                        machinePage.setDropDown();
                        break;

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

        accountPage.getAccountTable().getSelectionModel().addListSelectionListener(listSelectionListener);
        machinePage.getMachineTable().getSelectionModel().addListSelectionListener(listSelectionListener);

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
                break;
            case "Machine":
                machinePage.newMachine(tableList);
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
                break;
            case "Machine":
                machinePage.save();
                accountPage.setMachineTable();
                break;
        }
    }


}
