/**
 * This class represents a city. Besides what it recieves from Location, it also has a population.
 */
public final class City extends Location {

    private int population;

    public City(String name, double lat, double lon, int population) {
        super(name, lat, lon);

        this.population = population;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public String toString() {
        String cityString = "Orasul se numeste " + locationName + ", se afla la coordonatele " + coordinates[0] + " X, " + coordinates[1] + " Y" +
                " si are o populatie de " + population + " de locuitori" + ".\n";

        return cityString;
    }
}
