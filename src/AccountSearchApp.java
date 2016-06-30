

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class AccountSearchApp extends JFrame
{
    private AccountDAO accountDAO;
    private JLabel EnterName;
    private JTextField NameTextField;
    private JButton searchButton;
    private JTable table1;
    private JPanel ASAForm;

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("AccountSearchApp");
        frame.setContentPane(new AccountSearchApp().ASAForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public AccountSearchApp()
    {
        try
        {
            accountDAO = new AccountDAO();
        }
        catch(Exception exc)
        {
            System.out.println("Error Creating AccountDAO");
        }

        setTitle("Account Search App");
        searchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Get name from text field

                //Call DAO and get accounts from name string

                //if last name is empty, get all accounts

                //print out accounts


                try
                {
                    String name = NameTextField.getText();

                    List<Account> accounts = null;

                    if(name!=null && name.trim().length() > 0)
                        accounts = accountDAO.searchAccounts(name);
                    else
                        accounts = accountDAO.getAllAccounts();

                    for (Account temp : accounts)
                        System.out.println(temp);
                }

                catch (Exception exc)
                {
                    System.out.println("Error on search");
                }
            }
        });
    }




}
