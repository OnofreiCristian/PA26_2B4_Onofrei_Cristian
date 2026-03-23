package repository;

import exception.CatalogException;

public interface Command {
    void execute() throws CatalogException;
}
