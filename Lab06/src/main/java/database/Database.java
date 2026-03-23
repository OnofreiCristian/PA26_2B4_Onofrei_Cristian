package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Database instance = null;
    private Connection connection = null;

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "student";
    private static final String PASSWORD = "student";

    private Database() {
        try {
            this.connection = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Connected to database successfully");

        } catch (SQLException e) {
           System.err.println(e.getMessage());
        }
    }

    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
