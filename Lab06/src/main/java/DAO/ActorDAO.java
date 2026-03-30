package DAO;

import database.Database;
import database.Genre;

import java.sql.*;

public class ActorDAO {
    public void create(Genre genre) throws SQLException {
        String sql = "INSERT INTO actors (id, name) VALUES (?, ?)";
        try(Connection conn = Database.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, genre.getId());
            pstmt.setString(2, genre.getName());
            pstmt.executeUpdate();
            conn.commit();
        }
    }

    public Genre findById(int id) throws SQLException {
        String sql = "SELECT * FROM actors WHERE id = ?";
        try(Connection conn = Database.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    return new Genre(rs.getInt("id"), rs.getString("name"));
                }
            }
        }
        return null;
    }

    public Genre findByName(String name) throws SQLException {
        String sql = "SELECT * FROM actors WHERE name = ?";
        try(Connection conn = Database.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try(ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    return new Genre(rs.getInt("id"), rs.getString("name"));
                }
            }
        }
        return null;
    }
}
