/**
 * This is the abstract, sealed class Location. It is the base class for every location type.
 * It allows for a location to be mapped using coordinates.
 */
abstract sealed class Location permits City, GasStation, Airport {

    protected String locationName;
    protected double[] coordinates;

    //constructor for class Location
    public Location(String name, double lat, double lon) {
        this.locationName = name;
        coordinates = new double[]{lat, lon};

    }

    //getters for class Location
    public String getLocationName() {
        return locationName;
    }

    //setters for class Location
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double lat, double lon) {
        this.coordinates = new double[]{lat, lon};
    }



    public boolean equals(Location o) {

        if (this.locationName.equals(o.locationName) && this.getCoordinates() == o.getCoordinates() )
            return true;
        else
            return false;

    }
}
