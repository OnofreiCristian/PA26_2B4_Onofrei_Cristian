package repository;

import exception.CatalogException;

import java.awt.*;
import java.io.File;
import java.net.URI;

public class ViewCommand implements Command {

    Resource resource;

    public ViewCommand(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void execute() throws CatalogException {

        try {
            Desktop desktop = Desktop.getDesktop();
            String location = resource.getLocation();

            if (location.contains("https://") || location.contains("http://")) {
                desktop.browse(URI.create(location));
            } else {
                File file = new File(location);
                if (!file.exists()) {
                    throw new CatalogException("File does not exist at location " + location);
                }
                desktop.open(file);
            }
        } catch (Exception e) {
            throw new CatalogException("Exception met: " + e.getMessage());
        }
    }

}
