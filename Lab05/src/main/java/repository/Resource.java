package repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    private String id;
    private String title;
    private String location;
    private String year;
    private String author;

    @Override
    public String toString() {
        return "Resource{" + "id='" + id + '\'' + ", title='" + title + '\'' + '}';
    }
}
