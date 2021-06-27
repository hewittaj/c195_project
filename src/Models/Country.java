package Models;

/**
 * This method is used to construct/represent a country object
 */
public class Country {
    private final int id;
    private final String countryName;

    /**
     * Constructor for a country
     * @param id Id of country
     * @param countryName Name of country
     */
    public Country(int id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }

    /**
     * Gets id of country
     * @return Id of country
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name of country
     * @return Name of country
     */
    public String getName() {
        return countryName;
    }

    /**
     * Overridden .toString() method for any calls for the string representation
     * @return Country name
     */
    @Override
    public String toString() {
        return countryName;
    }
}
