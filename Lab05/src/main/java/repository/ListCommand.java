package repository;

import exception.CatalogException;

public class ListCommand implements Command {

    private final Catalog catalog;

    public ListCommand(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public void execute() throws CatalogException {

        if (catalog == null || catalog.getResources() == null)
            throw new CatalogException("Catalog is null or empty.");
        for (Resource r : catalog.getResources()) {
            System.out.println(r);
        }

    }

}
