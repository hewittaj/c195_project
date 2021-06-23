package Models;

public class Country {
    private final int id;
    private final String countryName;

    public Country(int id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return countryName;
    }

    @Override
    public String toString() {
        return countryName;
    }
}
