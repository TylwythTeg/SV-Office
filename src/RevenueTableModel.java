import java.util.List;
import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.Date;
import java.util.*;

class RevenueTableModel extends AbstractTableModel
{
    private static final int ACCOUNT_COL = 0;
    private static final int ID_COL = 1;
    private static final int DATE_COL = 2;
    private static final int MONEY_COL = 3;

    private static RevenueDAO revenueDAO;

    //private boolean consolidated = false;
    private Consolidated consolidated = Consolidated.NONE;


    private String[] columnNames = {"Account", "Id", "Date", "Money"};

    private List<RevenueLog> logs;
    private List<RevenueLog> consolidatedLogs;

    public RevenueTableModel(List<RevenueLog> logs)
    {
        try
        {
            revenueDAO = new RevenueDAO();
        }
        catch(SQLException exc)
        {
            System.out.println(exc);
        }

        this.logs = logs;
    }

    //public RevenueTableModel(List<RevenueLog> logs)
    {
        this.logs = logs;
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return logs.size();
    }

    @Override
    public String getColumnName(int col)
    {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        RevenueLog log = logs.get(row);

        switch(col)
        {
            case ACCOUNT_COL:
                return revenueDAO.getAccountName(log);
            case ID_COL:
                return log.getId();
            case DATE_COL:
                if(consolidated == Consolidated.YEAR)
                    return log.getYear();
                if(consolidated == Consolidated.MONTH)
                    return log.monthString(log.getMonth()) + " " + log.getYear();
                if(consolidated == Consolidated.WEEK)
                {
                    System.out.println("WEEK OF " + log.getFirstDayOfWeek());
                    return "Week of " + log.getFirstDayOfWeek();
                }

                return log.getDate();
            case MONEY_COL:
                return log.getMoney();
            default:
                System.out.println("Unobtainable revenuelog column");
                return log.getId();
        }
    }

    public void removeRow(int row) {
        // remove a row from your internal data structure
        fireTableRowsDeleted(row, row);
    }

    @Override
    public Class getColumnClass(int c)
    {
        if((getValueAt(0,c) == null))
            return String.class;
        return getValueAt(0,c).getClass();
    }

    public void addRow(RevenueLog log)
    {
        logs.add(log);
        fireTableRowsInserted(logs.size() - 1, logs.size() - 1);
    }

    public void setConsolidated(Consolidated con)
    {
        consolidated = con;
    }

    public Consolidated consolidated()
    {
        return consolidated;
    }
}