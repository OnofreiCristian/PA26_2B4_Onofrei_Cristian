package reports;

import database.Database;
import freemarker.template.Template;
import freemarker.template.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportGenerator {

    public void generateHTMLReport() {
        List<MovieReportItem> movieList = new ArrayList<>();

        String sql = "SELECT title, release_date, duration, score, genre_name FROM movie_report_view";

        try(Connection conn = Database.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                movieList.add(new MovieReportItem(
                        rs.getString("title"),
                        rs.getString("release_date"),
                        rs.getInt("duration"),
                        rs.getFloat("score"),
                        rs.getString("genre_name")
                ));
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return;
        }

        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);

            cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
            cfg.setDefaultEncoding("UTF-8");

            Template template = cfg.getTemplate("report.ftl");

            Map<String, Object> templateData = new HashMap<>();
            templateData.put("movies", movieList);

            try (Writer fileWriter = new FileWriter(new File("movie_report.html"))) {
                template.process(templateData, fileWriter);
                System.out.println("HTML report has been successfully generated");
            }

            } catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
