package repository;

import exception.CatalogException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Catalog {

    private List<Resource> resources = new ArrayList<>();

}
