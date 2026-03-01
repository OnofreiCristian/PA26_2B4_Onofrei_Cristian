/**
 * This class represents a gas station. Besides what it inherits, it also has a variable for gas prices.
 */

public final class GasStation extends Location {

    private double gasPrice;

    GasStation(String name, double latitude, double longitude, double gasPrice) {

        super(name,latitude,longitude);
        this.gasPrice = gasPrice;

    }

    public void setGasPrice(double gasPrice){
        this.gasPrice = gasPrice;
    }

    public double getGasPrice(double gasPrice){
        return gasPrice;
    }

    @Override
    public String toString() {
        String gasStationString = "Benzinaria se numeste " + locationName + ", se afla la coordonatele " + coordinates[0] + " X, " + coordinates[1] + " Y" +
                " si are pretul la benzina " + gasPrice + " de lei" + ".\n";

        return gasStationString;
    }

}
