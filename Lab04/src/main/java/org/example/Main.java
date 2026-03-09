package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    static void main() {


        //List implemented using streams
        List<Intersection> intersections = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> new Intersection("Intersection " + i))
                .toList();
        intersections.forEach(System.out::println);

        LinkedList<Street> streets = new LinkedList<>();
        streets.add(new Street("Street 1", 10));
        streets.add(new Street("Street 2", 20));
        streets.add(new Street("Street 3", 5));
        streets.add(new Street("Street 4", 90));
        streets.add(new Street("Street 5", 15));

        //sorting streets using the getLength getter from the street class
        streets.sort(Comparator.comparingDouble(Street::getLength));
        streets.forEach(System.out::println);

        Set<Intersection> intersectionSet = new HashSet<>(intersections);
        System.out.println("Initial HashSet size: " + intersectionSet.size());
        Intersection duplicateIntersection = new Intersection("Intersection 1");
        intersectionSet.add(duplicateIntersection);
        System.out.println("HashSet size after attempt to add duplicate: " + intersectionSet.size());


    }
}
