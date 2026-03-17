package main;

import exception.CatalogException;
import model.Resource;
import repository.Catalog;

public class Main {
    static void main() {

        Catalog catalog = new Catalog();
        Resource resource = new Resource("java25", "The Java Language Specification", "https://docs.oracle.com/javase/specs/jls/se25/jls25.pdf", "2025", "James Gosling & others");
        Resource badResource = new Resource("nuExista", "nuExista", "nuExista", "nuExista", "nuExista");

        try {
            catalog.add(resource);
            catalog.add(badResource);

            System.out.println("Incercam sa deschidem resursele din catalog.");
            catalog.open(resource);

            System.out.println("Incercam sa deschidem resursa rea din catalog: ");
            catalog.open(badResource);
        } catch (CatalogException e) {
            System.out.println(e.getMessage());

            if (e.getCause() != null) {
                System.out.println(e.getCause());
            }
        }
    }
}
