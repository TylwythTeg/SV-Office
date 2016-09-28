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




    public static List<RevenueLog> consolidateByYear(List<RevenueLog> logs, String mode)
    {
        List<RevenueLog> newLogs = new ArrayList<>();

        boolean first = true;
        for(int i = 0;i<logs.size();i++)
        {
            if(first == true)
            {
                newLogs.add(logs.get(i));
                first = false;
                continue;
            }

            if( (newLogs.get(newLogs.size()-1).getYear() == logs.get(i).getYear()
                    && newLogs.get(newLogs.size()-1).getAccountId() == logs.get(i).getAccountId() )
                    || (newLogs.get(newLogs.size()-1).getYear() == logs.get(i).getYear()) && mode == "compact")
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

    public static List<RevenueLog> consolidateByMonth(List<RevenueLog> logs, String mode)
    {
        List<RevenueLog> newLogs = new ArrayList<>();

        boolean first = true;
        for(int i = 0;i<logs.size();i++)
        {
            if(first == true)
            {
                newLogs.add(logs.get(i));
                first = false;
                continue;
            }

            if(mode == "listbydate")
            {
                if( newLogs.get(newLogs.size()-1).getYear() == logs.get(i).getYear()
                        && newLogs.get(newLogs.size()-1).getMonth() == logs.get(i).getMonth()
                        && newLogs.get(newLogs.size()-1).getAccountId() == logs.get(i).getAccountId())
                {
                    newLogs.get(newLogs.size()-1).setMoney( newLogs.get(newLogs.size()-1).getMoney() + logs.get(i).getMoney() );
                }
                else
                {
                    newLogs.add(logs.get(i));
                }

                continue;
            }

            if( ( newLogs.get(newLogs.size()-1).getYear() == logs.get(i).getYear()
                    && newLogs.get(newLogs.size()-1).getMonth() == logs.get(i).getMonth()
                    && newLogs.get(newLogs.size()-1).getAccountId() == logs.get(i).getAccountId())
                    || (newLogs.get(newLogs.size()-1).getYear() == logs.get(i).getYear()
                    && newLogs.get(newLogs.size()-1).getMonth() == logs.get(i).getMonth()
                    && mode == "compact"))
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

    public static List<RevenueLog> consolidateByWeek(List<RevenueLog> logs, String mode)
    {
        List<RevenueLog> newLogs = new ArrayList<>();

        boolean first = true;
        for(int i = 0;i<logs.size();i++)
        {
            if(first == true)
            {
                newLogs.add(logs.get(i));
                first = false;
                continue;
            }

            if( (newLogs.get(newLogs.size()-1).getYear() == logs.get(i).getYear()
                    //&& newLogs.get(newLogs.size()-1).getMonth() == logs.get(i).getMonth()
                    && newLogs.get(newLogs.size()-1).getWeek() == logs.get(i).getWeek()
                    && newLogs.get(newLogs.size()-1).getAccountId() == logs.get(i).getAccountId())
                    || (newLogs.get(newLogs.size()-1).getYear() == logs.get(i).getYear()
                        && newLogs.get(newLogs.size()-1).getWeek() == logs.get(i).getWeek()
                        && mode == "compact"))
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

    public static List<RevenueLog> consolidateByDay(List<RevenueLog> logs, String mode)
    {
        List<RevenueLog> newLogs = new ArrayList<>();

        boolean first = true;
        for(int i = 0;i<logs.size();i++)
        {
            if(first == true)
            {
                newLogs.add(logs.get(i));
                first = false;
                continue;
            }

            if( (newLogs.get(newLogs.size()-1).getYear() == logs.get(i).getYear()
                    && newLogs.get(newLogs.size()-1).getMonth() == logs.get(i).getMonth()
                    && newLogs.get(newLogs.size()-1).getDay() == logs.get(i).getDay()
                    && newLogs.get(newLogs.size()-1).getAccountId() == logs.get(i).getAccountId())
                    || (newLogs.get(newLogs.size()-1).getYear() == logs.get(i).getYear()
                    && newLogs.get(newLogs.size()-1).getMonth() == logs.get(i).getMonth()
                    && newLogs.get(newLogs.size()-1).getDay() == logs.get(i).getDay()
                    && mode == "compact"))
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
    public int getWeek()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }
    public String getFirstDayOfWeek()
    {
        Calendar cal = Calendar.getInstance();
        //cal.setTime(date);
        cal.setTime(firstDayOfWeek(date));
        return this.monthString(cal.get(Calendar.MONTH)) + " " +  cal.get(Calendar.DAY_OF_MONTH) + ", " +  cal.get(Calendar.YEAR);
    }
    public Date firstDayOfWeek(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if( cal.get(Calendar.DAY_OF_WEEK) == 1)
            return date;

        int adder = cal.get(Calendar.DAY_OF_WEEK) -1;
        cal.add(Calendar.DAY_OF_YEAR, -adder);

        Date newDate = cal.getTime();

        return newDate;

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
