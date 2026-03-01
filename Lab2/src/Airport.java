/**
 * This class represents an airport. Besides what it inherits, it also has a variable to represent the number of terminals.
 */

public final class Airport extends Location{

    private int numberOfTerminals;

    Airport(String name, double latitude, double longitude, int numberOfTerminals) {
        super(name, latitude, longitude);
        this.numberOfTerminals = numberOfTerminals;
    }

    public int getNumberOfTerminals() {
        return numberOfTerminals;
    }

    public void setNumberOfTerminals(int numberOfTerminals) {
        this.numberOfTerminals = numberOfTerminals;
    }

    @Override
    public String toString() {
        String airportString = "Orasul se numeste " + locationName + ", se afla la coordonatele " + coordinates[0] + " X, " + coordinates[1] + " Y" +
                " si are " + numberOfTerminals + " terminale" + ".\n";

        return airportString;
    }

}
