import java.util.Date;

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





}
