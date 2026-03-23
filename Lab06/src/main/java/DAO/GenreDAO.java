package DAO;

import database.Database;

import java.sql.*;

public class GenreDAO {

    public void create(int id, String name) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO genres VALUES (?, ?)")) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        }
    }

    public Integer findGenreId(int id) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT id FROM genres WHERE id=" + id
             ))
        {
            return rs.next() ? rs.getInt(1) : null;
        }

    }

    public Integer findGenreName(String name) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT id FROM genres WHERE name='" + name + "'"
             ))
        {
            return rs.next() ? rs.getInt(1) : null;
        }

    }

}
