package Models;

public class MonthAndTypeReport {
    private String month;
    private String type;
    private int count;

    /**
     * Constructor for the report that gets appointments by month and type
     *
     * @param month Month passed to construct a new report object
     * @param type  Type of appointment passed to construct a new report object
     * @param count Count of appointments by month and type
     */
    public MonthAndTypeReport(String month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
