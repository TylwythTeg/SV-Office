import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class SV_Office
{
    private static JFrame frame;
    private JPanel window;
    private JList listForSections;
    private JPanel cardContainer;
    private static JPanel cardAccountPage;
    private static JPanel cardMachinePage;
    private static CardAccountPage accountPage; //remove card from these class names
    private static CardMachinePage machinePage;

    private static SimpleCardLayout cardLayout;

    private AccountDAO accountDAO;
    private MachineDAO machineDAO;

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

                if (listForSections.getValueIsAdjusting() || listForSections.getSelectedValue() == null)
                    return;
                //listForSections.getSource

                String selection = listForSections.getSelectedValue().toString();


                if (selection.startsWith("Account"))
                {
                    System.out.println("You have Selected Account");
                    cardLayout.show(cardContainer, "Account");
                }
                if (selection.startsWith("Machine"))
                {
                    System.out.println("You have Selected Machine");
                    cardLayout.show(cardContainer, "Machine");
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

        accountPage = new CardAccountPage(tableList);
        machinePage = new CardMachinePage(tableList);
        cardAccountPage = accountPage.getCard();
        cardMachinePage = machinePage.getCard();


        //cards add/ add Jpanels to cardContainer
        cardContainer.add(cardAccountPage, "Account");
        cardContainer.add(cardMachinePage, "Machine");

        System.out.println(cardContainer.getComponents());


        //first layout view is Accounts
        cardLayout.show(cardContainer, "Account");
        cardLayout.setVisible("Account");


        EventHandler event = new EventHandler(cardLayout, cardContainer);
        machinePage.buttonRevert.addActionListener(event);
        //machinePage.tableViewTable.addActionListener(event);
        //machinePage.buttonRevert.addActionListener(event);
        //event.findVisibleCard(cardContainer);
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
}
