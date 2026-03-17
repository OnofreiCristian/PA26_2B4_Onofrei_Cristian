package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class City {

    private List<Intersection> intersections = new ArrayList<>();
    private List<Street>  streets = new ArrayList<>();

    public void addIntersection(Intersection intersection) {
        intersections.add(intersection);
    }

    public void addStreet(Street street) {
        streets.add(street);
    }

}
