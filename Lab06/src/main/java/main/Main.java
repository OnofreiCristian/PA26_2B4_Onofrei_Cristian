package main;

import DAO.GenreDAO;
import database.Database;

import java.sql.SQLException;

public class Main {
    static void main() throws SQLException {

        try {
            var genreDAO = new GenreDAO();
            genreDAO.create(3,"Action");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }
}
