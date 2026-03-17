package repository;

import exception.CatalogException;
import model.Resource;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private final List<Resource> resources = new ArrayList<>();

    public void add(Resource resource) throws CatalogException {

        if (resource == null) {
            throw new CatalogException("Resursa nu poate sa fie NULL!");
        }
        resources.add(resource);
    }

    public void open(Resource resource) throws CatalogException {

        try {
            Desktop desktop = Desktop.getDesktop();
            String location = resource.getLocation();

            if (location.contains("https://") || location.contains("http://")) {
                desktop.browse(URI.create(location));
            } else {
                File file = new File(location);
                if (!file.exists()) {
                    throw new CatalogException("Fisierul nu exista la locatia " + location);
                }
                desktop.open(file);
            }
        } catch (Exception e) {
            throw new CatalogException("A aparut exceptia: " + e.getMessage());
        }

    }

}
