package org.example;

import net.datafaker.Faker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.graph4j.Edge;
import org.graph4j.Graph;
import org.graph4j.GraphBuilder;
import org.graph4j.spanning.MinimumSpanningTreeBase;
import org.graph4j.spanning.WeightedSpanningTreeIterator;

public class Main {
    static void main() {

        Faker faker = new Faker();

        //List implemented using streams
        List<Intersection> intersections = IntStream.rangeClosed(1, 10).mapToObj(i -> new Intersection(faker.address().streetAddress())).toList();
        intersections.forEach(System.out::println);


        //Linking intersections by creating streets between them - much like linking nodes in a graph
        LinkedList<Street> streets = new LinkedList<>();
        for (int i = 0; i < intersections.size() - 1; i++) {
            streets.add(new Street(faker.address().streetName(), faker.number().numberBetween(10, 50), intersections.get(i), intersections.get(i + 1)));
        }

        streets.add(new Street(faker.address().streetName(), faker.number().numberBetween(10, 50), intersections.get(0), intersections.get(7)));
        streets.add(new Street(faker.address().streetName(), faker.number().numberBetween(10, 50), intersections.get(3), intersections.get(5)));
        streets.add(new Street(faker.address().streetName(), faker.number().numberBetween(10, 50), intersections.get(2), intersections.get(9)));

        //sorting streets using the getLength getter from the street class
        streets.sort(Comparator.comparingDouble(Street::getLength));
        streets.forEach(System.out::println);

        Set<Intersection> intersectionSet = new HashSet<>(intersections);
        System.out.println("Initial HashSet size: " + intersectionSet.size());

        City city = new City();

        for (Street street : streets) {
            city.addStreet(street);
        }

        for (Intersection intersection : intersectionSet) {
            city.addIntersection(intersection);
        }

        int value = 15;

        //Created a map of the intersections, where for every intersection we get the amount of streets connecting them
        Map<Intersection, Long> intersectionMap = city.getStreets().stream()
                .flatMap(street -> Stream.of(street.getStartIntersection(), street.getEndIntersection()))
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        //List of streets filtered using the map;
        List<Street> filteredStreets = city.getStreets().stream().filter(street -> street.getLength() > value).filter(street -> {

            long begin = intersectionMap.getOrDefault(street.getStartIntersection(), 0L);
            long end = intersectionMap.getOrDefault(street.getEndIntersection(), 0L);

            long connectedStreets = (begin + end) - 2;

            return connectedStreets >= 3;
        }).toList();

        System.out.println("\nStrazile filtrate: ");
        filteredStreets.forEach(System.out::println);


        Graph graphOfCity = GraphBuilder.empty().estimatedNumVertices(intersections.size()).buildGraph();

        //Map of nodes - this will link the intersections to an interger index, which helps us when dealing with the indexes
        //inside of the graph
        Map<Intersection, Integer> mapNoduri = new HashMap<>();
        int index = 0;
        for (Intersection intersection : city.getIntersections()) {
            mapNoduri.put(intersection, index);
            graphOfCity.addVertex(index);
            index++;
        }

        for (Street street : city.getStreets()) {
            int v1 = mapNoduri.getOrDefault(street.getStartIntersection(), 0);
            int v2 = mapNoduri.getOrDefault(street.getEndIntersection(), 0);
            graphOfCity.addEdge(v1, v2, street.getLength());
        }

        int k = 3;
        WeightedSpanningTreeIterator iterator = new WeightedSpanningTreeIterator(graphOfCity);


        System.out.println("\nTop " + k + " retele cu cost minim:");
        for (int i = 0; i < k && iterator.hasNext(); i++) {

            Collection<Edge> edges = iterator.next();

            double totalCost = 0;

            System.out.println("Solutia " + (i + 1) + " contine strazile: ");
            for (Edge edge : edges) {
                totalCost += edge.weight();
            }

            System.out.println("Cost total solutie: " + totalCost);
            System.out.println("------------------------------------");

        }

    }
}
