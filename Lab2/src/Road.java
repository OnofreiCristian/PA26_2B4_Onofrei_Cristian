/**
 * This is the class that handles the roads.
 */

public class Road {


    private Location location1;
    private Location location2;
    private RoadTypes roadType;
    private double roadLength;
    private int speedLimit;

    /**
     *
     * This method calculates the distance between two locations, given their latitudes and longitudes.
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return It returns the correct distance between the coordinates, in kilometers.
     */
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);

        //6371 is the radius of earth
        double distance = Math.sqrt(x * x + y * y) * 6371;

        return distance;
    }

    /**
     *
     * This is the constructor for the road class.
     * Given the two locations, it checks to see if the length given by the user is at least as long as the distance
     * between the coordinates of the locations. If not, it automatically replaces the length given with the minimum
     * distance.
     *
     * @param type
     * @param length
     * @param speedLimit
     * @param location1
     * @param location2
     */
    public Road( RoadTypes type, double length, int speedLimit, Location location1, Location location2) {

        this.roadType = type;
        this.speedLimit = speedLimit;
        this.location1 = location1;
        this.location2 = location2;

        double[] coordsLocation1 = location1.getCoordinates();
        double[] coordsLocation2 = location2.getCoordinates();

        double distanceBetweenLocations = calculateDistance(coordsLocation1[0], coordsLocation1[1], coordsLocation2[0], coordsLocation2[1]);

        if (length >= distanceBetweenLocations) {
            this.roadLength = length;
        } else {
            this.roadLength = distanceBetweenLocations;
        }
    }

    //getters for class Road
    public RoadTypes getRoadType() {
        return roadType;
    }

    //setters for class Road
    public void setRoadType(RoadTypes roadType) {
        this.roadType = roadType;
    }

    public double getRoadLength() {
        return roadLength;
    }

    public void setRoadLength(double roadLength) {
        this.roadLength = roadLength;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public Location[] getLocations() {

        return new Location[] {location1, location2};
    }

    public void setLocations (Location location1, Location location2) {
        this.location1 = location1;
        this.location2 = location2;
    }

    @Override
    public String toString() {
        String drum = "Drumul este de tip " + roadType + ", are lungimea " + roadLength + ", si are limita de viteza " + speedLimit + "KM/H.\n";
        return drum;
    }

    public boolean equals(Road o) {

        if(this.roadType == o.roadType && this.location1 == o.location1 && this.location2 == o.location2)
            return true;

        else if (this.roadType == o.roadType && this.location2 == o.location1 && this.location1 == o.location2)
            return true;

        else
            return false;

    }
}
