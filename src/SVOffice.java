import java.util.*;

public class SVOffice
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("SV Office starting...");

        try
        {
            AccountDAO dao = new AccountDAO();

            //List list = dao.getAllAccounts();
            List list = dao.searchAccounts("math");
            for(int i=0;i<list.size();i++)
            {
                System.out.println(list.get(i));
            }
        } finally
        {
            System.out.println("success");
        }
    }
}