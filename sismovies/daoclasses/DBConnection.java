package sismovies.daoclasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by marcusviniv on 21/10/2015.
 */
public class DBConnection {

    private static Connection con;
    private static String url = "jdbc:postgresql://localhost/sismovies";
    private static String user = "postgres";
    private static String password = "123456";

    public static Connection getConnection() throws SQLException {
        if(con == null)
            con = DriverManager.getConnection(url, user, password);
        return con;

    }

}
