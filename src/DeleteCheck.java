/**
 * Created by Rob on 9/11/2016.
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.EventObject;
import javax.swing.event.*;
public class DeleteCheck
{
    private JPanel Deletecheck;

    JFrame frame;
    private JPanel DeleteCheck;
    private JButton buttonYesDelete;
    private JButton buttonNoDelete;
    public JPanel deleteCheck;
    private JPanel sfffef;

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("DeleteCheck");
        frame.setContentPane(new DeleteCheck().DeleteCheck);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.setTitle("SV Office Alpha");
        //System.out.println("hey");


    }

    public DeleteCheck()
    {

    }

    public void setPane()
    {
        frame.setContentPane(new DeleteCheck().DeleteCheck);
    }

    public JPanel getJPanel()
    {
        return new DeleteCheck().DeleteCheck;
    }
}
