public class Machine
{
    private int machine_id;
    private String brand;
    private String type;
    private String model;
    private String asset;
    private int account_id;

    public Machine(int machine_id, String brand, String type, String model, String asset, int account_id)
    {
        this.machine_id = machine_id;
        this.brand = brand;
        this.type = type;
        this.model = model;
        this.asset = model;
        this.account_id = account_id;
    }

    public int getId()
    {
        return machine_id;
    }

    public void setId(int id)
    {
        this.machine_id = id;
    }

    public String getBrand()
    {
        return brand;
    }

    public void setBrand(String name)
    {
        this.brand = brand;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String address)
    {
        this.type = type;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String address)
    {
        this.model = model;
    }

    public int getAccountId()
    {
        return account_id;
    }

    public void setAccountId(int id)
    {
        this.account_id = id;
    }

    @Override
    public String toString()
    {
        String later = new String("deal with this later");
        return later;

    }

}