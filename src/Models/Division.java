package Models;

public class Division {
    private int divisionId;
    private String divisionName;
    private int countryId;

    public Division(int divisionId, String divisionName, int countryId){
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }
    public int getDivisionId(){
        return divisionId;
    }
    public int getCountryId(){
        return countryId;
    }

    public String getDivisionName(){
        return divisionName;
    }

    @Override
    public String toString() {
        return divisionName;
    }
}
