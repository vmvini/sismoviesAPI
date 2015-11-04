package sismovies.daoclasses;

import sismovies.entities.Movie;
import sismovies.entities.MovieActors;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class MovieActorDAO implements GenericDAO<MovieActors> {

    private Connection con;

    public MovieActorDAO(Connection con){
        this.con = con;
    }

    /*
    *  private String actor;
    private Movie movie;
    private int id;

    * */
    private List<MovieActors> buildMovieActorsList(ResultSet r ) throws SQLException{
        List<MovieActors> mas = new ArrayList<>();
        MovieActors ma;
        while(r.next()){
            ma = new MovieActors();
            ma.setId(r.getInt("id"));
            ma.setActor(r.getString("actor"));

            //finding movie and setting it
            HashMap<String, String> search = new HashMap<>();
            search.put("id", String.valueOf( r.getInt("movie") ) );
            List<Movie> movies = new MovieDAO(con).search(search);
            ma.setMovie(movies.get(0));

            mas.add(ma);
        }

        return mas;
    }

    public MovieActors save(MovieActors object) throws SQLException{
        String sql = "insert into MovieActors(movie, actor) values(?,?)";
        PreparedStatement p = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        p.setInt(1, object.getMovie().getId());
        p.setString(2, object.getActor());
        p.executeUpdate();
        object.setId( SQLUtils.getGeneratedId(p) );
        return object;
    }

    public MovieActors update(MovieActors object) throws SQLException{
        String sql = "update MovieActors set movie = ?, actor = ? where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getMovie().getId());
        p.setString(2, object.getActor());
        p.setInt(3, object.getId());
        p.executeUpdate();
        return object;
    }

    public boolean remove(MovieActors object) throws SQLException{
        String sql = "delete from MovieActors where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getId());
        p.executeUpdate();
        return true;
    }

    public List<MovieActors> list() throws SQLException{
        String sql = "select * from MovieActors";
        Statement s = con.createStatement();
        ResultSet r = s.executeQuery(sql);
        return buildMovieActorsList(r);
    }

    public List<MovieActors> search(HashMap<String, String> searchParams) throws SQLException{
        String sql = "select * from MovieActors where ";
        PreparedStatement p = SQLUtils.buildPreparedStatement(searchParams, sql, con);
        ResultSet r = p.executeQuery();
        return buildMovieActorsList(r);
    }

}
