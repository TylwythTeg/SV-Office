import javax.swing.*;
import java.awt.CardLayout;


public class SV_Office
{
    private static JFrame frame;
    private JPanel cardContainer;
    private static JPanel cardAccountPage;
    private static JPanel cardMachinePage;

    private static CardLayout cardLayout;



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
        //can change between eachother. Or maybe that's not the reason at all, but it works
        cardAccountPage = new CardAccountPage(cardLayout, cardContainer).window;
        cardMachinePage = new CardMachinePage().window;

        //cards add/ add Jpanels to cardContainer
        cardContainer.add(cardAccountPage, "Account");
        cardContainer.add(cardMachinePage, "Machine");


        //first layout view is Accounts
        cardLayout.show(cardContainer, "Account");
    }
}
