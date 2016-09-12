

public class Account
{
    private int account_id;
    private String name;
    private String address;

    public Account(int account_id, String name, String address)
    {
        this.account_id = account_id;
        this.name = name;
        this.address = address;
    }

    public int getId()
    {
        return account_id;
    }
    public String getName()
    {
        return name;
    }
    public String getAddress()
    {
        return address;
    }

    public void setId(int id)
    {
        this.account_id = id;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    @Override
    public String toString()
    {
        return String
                .format ("Account [account_id=%s, name=%s, address=%s]", account_id, name, address);
    }

}