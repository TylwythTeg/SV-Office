import java.util.List;

import javax.swing.table.AbstractTableModel;

class AccountTableModel extends AbstractTableModel
{
    private static final int NAME_COL = 0;
    private static final int ADDRESS_COL = 1;

    private String[] columnNames = {"Name", "Address"};

    private List<Account> accounts;

    public AccountTableModel(List<Account> accounts)
    {
        this.accounts = accounts;
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return accounts.size();
    }

    @Override
    public String getColumnName(int col)
    {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        Account account = accounts.get(row);

        switch(col)
        {
            case NAME_COL:
                return account.getName();
            case ADDRESS_COL:
                return account.getAddress();
            default:
                System.out.println("Unobtainable column");
                return account.getName();
        }
    }

    @Override
    public Class getColumnClass(int c)
    {
        return getValueAt(0,c).getClass();
    }
}