package sismovies.daoclasses;

import sismovies.entities.Movie;
import sismovies.entities.RecommendedMovie;
import sismovies.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class RecommendedMovieDAO implements GenericDAO<RecommendedMovie> {


    private Connection con;

    public RecommendedMovieDAO(Connection con){
        this.con = con;
    }

    /*private int id;
    private Movie movie;
    private User senderUser;
    private User targetUser;
    private Date date;
*/
    private List<RecommendedMovie> buildRecommendedMovieList(ResultSet r)throws SQLException{
        List<RecommendedMovie> rms = new ArrayList<>();
        RecommendedMovie rm;
        while(r.next()){
            rm = new RecommendedMovie();
            rm.setId(r.getInt("id"));
            rm.setDate(r.getDate("date"));
            rm.setViewed(r.getBoolean("viewed"));

            //finding movie and setting it
            HashMap<String, String> search = new HashMap<>();
            search.put("id", String.valueOf( r.getInt("movie") ));
            List<Movie> movies = new MovieDAO(con).search(search);
            rm.setMovie(movies.get(0));

            //finding senderUser
            UserDAO userDAO = new UserDAO(con);
            search.clear();
            search.put("id", String.valueOf( r.getInt("senderUser") ));
            List<User> users = userDAO.search(search);
            rm.setSenderUser(users.get(0));

            //finding targetUser
            search.clear();
            search.put("id", String.valueOf( r.getInt("targetUser") ));
            users.clear();
            users = userDAO.search(search);
            rm.setTargetUser(users.get(0));

            rms.add(rm);
        }

        return rms;
    }


    public RecommendedMovie save(RecommendedMovie object) throws SQLException{
        String sql = "insert into RecommendedMovie(movie, senderUser, targetUser, date, viewed) values(?,?,?,?, ?)";
        PreparedStatement p = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        p.setInt(1, object.getMovie().getId());
        p.setInt(2, object.getSenderUser().getId());
        p.setInt(3, object.getTargetUser().getId());
        p.setDate(4, new java.sql.Date( object.getDate().getTime() ));
        p.setBoolean(5, object.isViewed());
        p.executeUpdate();
        object.setId( SQLUtils.getGeneratedId(p) );
        return object;
    }

    public RecommendedMovie update(RecommendedMovie object) throws SQLException{
        String sql = "update RecommendedMovie set movie = ?, senderUser = ?, targetUser = ?, date = ?, viewed = ? where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getMovie().getId());
        p.setInt(2, object.getSenderUser().getId());
        p.setInt(3, object.getTargetUser().getId());
        p.setDate(4, new java.sql.Date( object.getDate().getTime() ));
        p.setBoolean(5, object.isViewed());
        p.setInt(6, object.getId());
        p.executeUpdate();
        return object;
    }

    public boolean remove(RecommendedMovie object) throws SQLException{
        String sql = "delete from RecommendedMovie where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getId());
        p.executeUpdate();
        return true;
    }

    public List<RecommendedMovie> list() throws SQLException{
        String sql = "select * from RecommendedMovie";
        Statement s = con.createStatement();
        ResultSet r = s.executeQuery(sql);
        return buildRecommendedMovieList(r);
    }


    public List<RecommendedMovie> search(HashMap<String, String> searchParams) throws SQLException{
        String sql = "select * from RecommendedMovie where ";
        PreparedStatement p = SQLUtils.buildPreparedStatement(searchParams, sql, con);
        ResultSet r = p.executeQuery();
        return buildRecommendedMovieList(r);
    }



}
