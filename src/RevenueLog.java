import java.util.Date;
import java.util.*;

public class RevenueLog
{
    private int log_id;
    private Date date;
    private double money;
    private int account_id = -1;

    public RevenueLog(int log_id, Date date, double money, int account_id)
    {
        this.log_id = log_id;
        this.date = date;
        this.money = money;
        this.account_id = account_id;
    }



    public void setId(int id)
    {
        this.log_id = id;
    }
    public void setDate(Date date)
    {
        this.date = date;
    }
    public void setMoney(double money)
    {
        this.money = money;
    }
    public void setAccountId(int id)
    {
        this.account_id = id;
    }
    public int getAccountId()
    {
        return account_id;
    }
    public int getId()
    {
        return log_id;
    }
    public Date getDate()
    {
        return date;
    }
    public double getMoney()
    {
        return money;
    }


    @Override
    public String toString()
    {
        return String
                .format ("RevenueLog [log_id=%s, date=%s, money=%s]", log_id, date, money);
    }




    public static List<RevenueLog> consolidateByYear(List<RevenueLog> logs)
    {
        List<RevenueLog> newLogs = new ArrayList<>();
        //RevenueLog prevLog = null;

        boolean first = true;
        for(int i = 0;i<logs.size();i++)
        {
            if(first == true)
            {
                //prevLog = logs.get(i);
                newLogs.add(logs.get(i));
                first = false;
                continue;
            }

            if(newLogs.get(newLogs.size()-1).getYear() == logs.get(i).getYear()
                    && newLogs.get(newLogs.size()-1).getAccountId() == logs.get(i).getAccountId())
            {
                //merge into new
                newLogs.get(newLogs.size()-1).setMoney( newLogs.get(newLogs.size()-1).getMoney() + logs.get(i).getMoney() );
            }
            else
            {
                newLogs.add(logs.get(i));
            }

        }
        return newLogs;
    }


    public int getYear()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }
    public int getMonth()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }
    public int getDay()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    public String monthString(int m)
    {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[m];
    }





}
