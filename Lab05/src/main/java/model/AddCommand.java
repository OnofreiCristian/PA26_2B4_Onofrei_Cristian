package model;

import exception.CatalogException;
import repository.Catalog;
import repository.Command;
import repository.Resource;

import java.util.List;

public class AddCommand implements Command {

    Catalog catalog;
    Resource resource;

    public AddCommand(Catalog catalog, Resource resource) throws CatalogException {
        if (resource == null) {
            throw new CatalogException("Resource cannot be null.");
        }
        this.catalog = catalog;
        this.resource = resource;
    }

    @Override
    public void execute() throws CatalogException {

        if (resource == null) {
            throw new CatalogException("Resource cannot be null.");
        }

        List<Resource> resources = catalog.getResources();

        resources.add(resource);

        catalog.setResources(resources);
    }
}
