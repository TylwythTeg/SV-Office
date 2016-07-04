
public class Table
{

    private String name;

    public Table(String name)
    {
        this.name = name;
        System.out.println("New Table created by name: " + this.name);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}