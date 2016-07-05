import java.util.List;

import javax.swing.table.AbstractTableModel;

class MachineTableModel extends AbstractTableModel
{
    private static final int ID_COL = 0;
    private static final int TYPE_COL = 1;
    private static final int BRAND_COL = 2;
    private static final int MODEL_COL = 3;
    private static final int ASSET_COL = 4;
    private static final int ACCOUNT_ID_COL = 5;


    private String[] columnNames = {"Id", "Type", "Brand", "Model", "Asset"};

    private List<Machine> machines;

    public MachineTableModel(List<Machine> machines)
    {
        this.machines = machines;
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return machines.size();
    }

    @Override
    public String getColumnName(int col)
    {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        Machine machine = machines.get(row);

        switch(col)
        {
            case ID_COL:
                return machine.getId();
            case TYPE_COL:
                return machine.getType();
            case BRAND_COL:
                return machine.getBrand();
            case MODEL_COL:
                return machine.getModel();
            case ASSET_COL:
                return machine.getAsset();
            case ACCOUNT_ID_COL:
                return machine.getAccountId();
            default:
                System.out.println("Unobtainable column");
                return machine.getBrand();
        }
    }

    @Override
    public Class getColumnClass(int c)
    {
        Object value=this.getValueAt(0,c);
        return (value==null?Object.class:value.getClass());
        //return getValueAt(0,c).getClass();
    }
}