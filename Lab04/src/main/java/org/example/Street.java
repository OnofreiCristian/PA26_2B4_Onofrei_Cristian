package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Street implements Comparable<Street> {

    private String name;
    private int length;

    @Override
    public int compareTo(Street street) {
        return this.length - street.length;
    }

    @Override
    public String toString() {
        return name;
    }

}
