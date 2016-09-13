import javax.swing.*;
import java.awt.CardLayout;

import java.util.concurrent.TimeUnit;

/**
 * Created by Rob on 9/12/2016.
 */
public class SV_Office
{
    private static JFrame frame;
    protected JPanel cardContainer; //protected so old SV_OfficeAccounts.java can access
    private static JPanel cardAccountPage;
    private static JPanel cardMachinePage;

    private static CardLayout cardLayout;

    //cards
    //private static CardAccountPage cardAccountPage;


    public static void main(String[] args)
    {
        frame = new JFrame("SV_Office");
        frame.setContentPane(new SV_Office().cardContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("SV Office Alpha");
        frame.pack();
        frame.setVisible(true);






    }

    public SV_Office()
    {
        setCards();








    }

    public void setCards()
    {
        //set card layout
        cardLayout = (CardLayout) (cardContainer.getLayout());

        //cards setup, get Jpanels
        //because java passes objects by reference(?) we can pass cardLayout and cardContainer so listeners on those panels
        //can change between eachother
        cardAccountPage = new CardAccountPage(cardLayout, cardContainer).window;
        cardMachinePage = new CardMachinePage().window;

        //cards add/ add Jpanels to cardContainer
        cardContainer.add(cardAccountPage, "Account");
        cardContainer.add(cardMachinePage, "Machine");



        //first layout view is Accounts
        cardLayout.show(cardContainer, "Account");
        try
        {
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception exc)
        {
            System.out.println("error");
        }
        //cardLayout.show(cardContainer,"Machine");
    }
}
