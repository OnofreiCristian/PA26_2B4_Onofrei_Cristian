import java.util.Vector;

/**
 * This is the class that describes an instance of the problem presented in Lab2.
 * Objects (in our case, locations and roads) are added and handles by this class.
 *
 * @author Onofrei Cristian
 */
public class ProblemManager {

    private final Vector<Location> locations;
    private final Vector<Road> roads;

    ProblemManager() {
        locations = new Vector<Location>();
        roads = new Vector<Road>();
    }

    public void addLocation(Location newLocation) {

        boolean alreadyExists = false;

        for (Location l : locations) {
            if (l.equals(newLocation)) {
                alreadyExists = true;
            }
        }
        if (alreadyExists) {
            System.out.println("This location already exists!");
        } else {
            locations.add(newLocation);
        }

    }

    public void addRoad(Road newRoad) {

        boolean alreadyExists = false;

        for (Road r : roads) {
            if (r.equals(newRoad)) {
                alreadyExists = true;
            }
        }
        if (alreadyExists) {
            System.out.println("This road already exists!");
        } else {
            roads.add(newRoad);
        }

    }

    /**
     *
     * This method verifies if the instance of our problem is valid.
     * If there is a road that leads towards a location that does not exist, our instance is invalid.
     *
     * @return isInstanceValid this is a boolean variable updates in case we find an inconsistency within the problem.
     */
    public boolean isValid()
    {

        boolean isInstanceValid = true;
        Location[] currentRoadLocations;

        for( Road r : roads)
        {
            currentRoadLocations = r.getLocations();

            if(!(locations.contains(currentRoadLocations[0]) && locations.contains(currentRoadLocations[1])))
            {
                isInstanceValid = false;
                break;
            }

        }

        return isInstanceValid;
    }

    public void getLocations()
    {
        for(Location l : locations)
        {
            System.out.println(l);
        }

    }

    /**
     *
     * This method uses a BFS style searching method, going through all available roads, to check if it's possible to
     * travel from location one to location two
     *
     * @param l1 this is the location we are starting from
     * @param l2 this is the location we are trying to reach
     * @return the method returns whether it is possible to get from location one to location two;
     */
    public boolean roadBetweenLocations(Location l1, Location l2)
    {

        Vector<Location> visitedLocations = new Vector<Location>();
        Location[] currentRoadLocations;

        for(Road r : roads)
        {
            currentRoadLocations = r.getLocations();


                if (!(visitedLocations.contains(currentRoadLocations[0]))) {
                    visitedLocations.add(currentRoadLocations[0]);
                }

                if (!(visitedLocations.contains(currentRoadLocations[1]))) {
                    visitedLocations.add(currentRoadLocations[1]);
                }
            }


        if(visitedLocations.contains(l1) && visitedLocations.contains(l2))
            return true;

        return false;

    }

}
