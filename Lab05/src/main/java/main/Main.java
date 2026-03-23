package main;

import exception.CatalogException;
import repository.*;

public class Main {
    static void main() {

        Catalog catalog = new Catalog();
        Resource resource = new Resource("java25", "The Java Language Specification", "https://docs.oracle.com/javase/specs/jls/se25/jls25.pdf", "2025", "James Gosling & others");
        Resource badResource = new Resource("nuExista", "nuExista", "nuExista", "nuExista", "nuExista");

        catalog.getResources().add(resource);
        catalog.getResources().add(badResource);

        Command listCmd = new ListCommand(catalog);
        try {
            listCmd.execute();
        } catch (CatalogException e) {
            System.err.println(e.getMessage());
        }

        Command viewValidCmd = new ViewCommand(resource);
        try{
            viewValidCmd.execute();
            System.out.println("Successfully viewed the resource");
        }
        catch (CatalogException e) {
            System.err.println(e.getMessage());
        }


        Command viewInvalidCmd = new ViewCommand(badResource);
        try {
            viewInvalidCmd.execute();
            System.out.println("Successfully viewed the resource");
        }
        catch (CatalogException e) {
            System.err.println(e.getMessage());

            if(e.getCause() != null) {
                System.err.println(e.getCause());
            }
        }

        Command reportCmd = new ReportCommand(catalog);
        try{
            reportCmd.execute();
            System.out.println("Successfully report the resource");
        }
        catch (CatalogException e) {
            System.err.println(e.getMessage());
            if(e.getCause() != null) {
                System.err.println(e.getCause());
            }
        }

    }
}
