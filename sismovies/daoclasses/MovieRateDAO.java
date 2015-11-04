package sismovies.daoclasses;


import sismovies.entities.Movie;
import sismovies.entities.MovieRate;
import sismovies.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class MovieRateDAO implements GenericDAO<MovieRate> {

    private Connection con;

    public MovieRateDAO(Connection con){
        this.con = con;
    }
    /*
    *  private int id;
    private int rate;
    private Movie movie;
    private User user;*/

    private List<MovieRate> buildMovieRateList(ResultSet r) throws SQLException{
        List<MovieRate> mrs = new ArrayList<>();
        MovieRate mr;
        while(r.next()){
            mr = new MovieRate();
            //finding user and setting
            HashMap<String, String> search = new HashMap<>();
            search.put("id", String.valueOf(r.getInt("sisuser")));
            List<User> users = new UserDAO(con).search(search);
            mr.setUser(users.get(0));

            //finding movie and setting
            search.clear();
            search.put("id", String.valueOf( r.getInt("movie") ));
            List<Movie> movies = new MovieDAO(con).search(search);
            mr.setMovie(movies.get(0));

            //setting rate
            mr.setRate( r.getInt("rate") );

            //setting id
            mr.setId(r.getInt("id"));

            //setting comment
            mr.setComment(r.getString("comment"));

            mrs.add(mr);
        }
        return mrs;
    }

    public MovieRate save(MovieRate object) throws SQLException{
        String sql = "insert into MovieRate(rate, movie, sisuser, comment) values (?,?,?, ?)";
        PreparedStatement p = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        p.setInt(1, object.getRate());
        p.setInt(2, object.getMovie().getId());
        p.setInt(3, object.getUser().getId());
        p.setString(4, object.getComment());
        p.executeUpdate();
        object.setId( SQLUtils.getGeneratedId(p) );
        return object;
    }

    public MovieRate update(MovieRate object) throws SQLException{
        String sql = "update MovieRate set rate = ?, movie = ?, sisuser = ?, comment = ? where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getRate());
        p.setInt(2, object.getMovie().getId());
        p.setInt(3, object.getUser().getId());
        p.setInt(4, object.getId());
        p.setString(5, object.getComment());
        return object;
    }

    public boolean remove(MovieRate object) throws SQLException{
        String sql = "delete from MovieRate where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getId());
        p.executeUpdate();
        return true;
    }

    public List<MovieRate> list() throws SQLException{
        String sql = "select * from MovieRate order by rate desc";
        Statement s = con.createStatement();
        ResultSet r = s.executeQuery(sql);
        return buildMovieRateList(r);
    }

    public List<MovieRate> search(HashMap<String, String> searchParams) throws SQLException{
        String sql = "select * from MovieRate where ";
        PreparedStatement p = SQLUtils.buildPreparedStatement(searchParams, sql, con);
        ResultSet r = p.executeQuery();
        return buildMovieRateList(r);
    }


}
