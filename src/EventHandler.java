import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.CardLayout;
import java.awt.Component;

public class EventHandler implements ActionListener
{
    JPanel cardContainer;
    SimpleCardLayout cardLayout;

    public EventHandler(SimpleCardLayout cardLayout, JPanel cardContainer)
    {
        this.cardLayout = cardLayout;
        this.cardContainer = cardContainer;
    }
    public EventHandler()
    {

    }



    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        Object source = e.getSource();
        System.out.println("ActionCommand: " + command);
        System.out.println("Source: " + source);



        switch(e.getActionCommand())
        {
            case "Revert": revert();
                break;
        }

    }

    public void revert()
    {
        switch(cardLayout.getVisible())
        {
            case "Account": System.out.println("Account");
                break;
            case "Machine": System.out.println("Machine");
        }
    }


}