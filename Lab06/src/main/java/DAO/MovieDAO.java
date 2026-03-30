package DAO;

import database.Database;
import database.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDAO {

    public void create(Movie movie) throws SQLException {
        String sqlQuery = "INSERT INTO movies (id, title, release_date, duration, score, genre_id) VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
            stmt.setInt(1, movie.getId());
            stmt.setString(2, movie.getTitle());
            stmt.setString(3, movie.getReleaseDate());
            stmt.setInt(4, movie.getDuration());
            stmt.setFloat(5,movie.getRating());
            stmt.setInt(6, movie.getGenre_id());

            stmt.executeUpdate();
            conn.commit();
        }
    }

    public Movie findById(int id) throws SQLException {
        String sqlQuery = "SELECT id, title, TO_CHAR(release_date, 'YYYY-MM-DD') as release_date, duration, score, genre_id FROM movies WHERE id = ?";

        try (Connection conn = Database.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sqlQuery);) {
                stmt.setInt(1,id);
                try(ResultSet rs = stmt.executeQuery()) {
                    if(rs.next()) {
                        return new Movie(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("release_date"),
                                rs.getInt("duration"),
                                rs.getFloat("score"),
                                rs.getInt("genre_id")
                        );
                    }
                }
        }

        return null;
    }
}
