package Models;

/**
 * This class represents/constructs a division object
 */
public class Division {
    private final int divisionId;
    private final String divisionName;
    private final int countryId;

    /**
     * Constructor for a division object
     *
     * @param divisionId   Division id of division
     * @param divisionName Name of division
     * @param countryId    Country id associated with division
     */
    public Division(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    /**
     * Gets the division id
     *
     * @return Division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Gets the country id
     *
     * @return Country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Gets the division name
     *
     * @return Division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Overriden method for any .toString() calls
     *
     * @return Division name
     */
    @Override
    public String toString() {
        return divisionName;
    }
}
