package main;

import DAO.ActorDAO;
import DAO.GenreDAO;
import DAO.MovieDAO;
import database.Actor;
import database.Genre;
import database.Movie;
import reports.ReportGenerator;

import java.sql.SQLException;

public class Main {
    static void main() throws SQLException {
        Genre drama = new Genre(1,"Drama");
        Genre comedy = new Genre(2,"Comedy");
        Movie uncutGems = new Movie(1,"Uncut Gems", "2019-12-25", 135, 7.4F,1 );
        Actor adamSandler = new Actor(1, "Adam Sandler");
        Movie martySupreme = new Movie(2,"Marty Supreme", "2025-12-25", 149, 7.7F,1 );
        Movie goodTime = new Movie(3,"Good Time", "2017-05-25", 101, 7.3F,1 );
        try {
            var genreDAO = new GenreDAO();
            var movieDAO = new MovieDAO();
            var actorDAO = new ActorDAO();

            genreDAO.create(drama);
            genreDAO.create(comedy);

            movieDAO.create(uncutGems);
            movieDAO.create(martySupreme);
            movieDAO.create(goodTime);



        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        new ReportGenerator().generateHTMLReport();
    }
}
