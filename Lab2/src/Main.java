
public static void main() {

    ProblemManager problemManager = new ProblemManager();

    City Iasi = new City("Iasi", 47.16363040986314, 27.61909782136043, 270000);
    System.out.println(Iasi.toString());

    problemManager.addLocation(Iasi);

    City Bucuresti = new City("Bucuresti",  44.44754786932638, 26.123960527390235, 2000000);

    City Suceava = new City("Suceava",47.663922776783714, 26.472244124159477, 110000);

    problemManager.addLocation(Bucuresti);

    problemManager.addLocation(Suceava);

    System.out.println("Coordonatele Bucurestiului sunt: " + Bucuresti.getCoordinates()[0] + " X, " + Bucuresti.getCoordinates()[1] + " Y.\n");

    Road drum1 = new Road(RoadTypes.NATIONAL, 400, 90, Iasi, Bucuresti);

    Road drum2 = new Road(RoadTypes.AUTOSTRADA, 200, 100, Iasi, Bucuresti);

   // Road drum3 = new Road(RoadTypes.EUROPEAN, 200, 80, Iasi, Suceava);

    problemManager.addRoad(drum1);
    problemManager.addRoad(drum2);
   // problemManager.addRoad(drum3);

    System.out.println(problemManager.isValid());

    System.out.println(problemManager.roadBetweenLocations(Iasi, Bucuresti));

    System.out.println(problemManager.roadBetweenLocations(Iasi, Suceava));

}
