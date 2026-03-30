package reports;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovieReportItem {
    private String title;
    private String releaseDate;
    private int duration;
    private float score;
    private String genre;
}
