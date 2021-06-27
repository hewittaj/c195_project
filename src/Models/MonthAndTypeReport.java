package Models;

/**
 * This method represents/constructs a report for the month and type report based on appointment info in database
 */
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

    /**
     * Gets the month for a report object
     * @return Month
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets the month of a report object
     * @param month Month to be set to
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Gets the type associated with report object
     * @return Type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type for an appointment report object
     * @param type Type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the count of appointments for the report object
     * @return Count
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count of the report object
     * @param count Count
     */
    public void setCount(int count) {
        this.count = count;
    }
}
