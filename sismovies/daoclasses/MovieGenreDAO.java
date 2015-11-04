package sismovies.daoclasses;

import sismovies.entities.Genre;
import sismovies.entities.Movie;
import sismovies.entities.MovieGenres;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class MovieGenreDAO implements GenericDAO<MovieGenres> {

    private Connection con;

    public MovieGenreDAO(Connection con){
        this.con = con;
    }


    /*
    * private Genre genre;
    private Movie movie;
    private int id;
    * */
    private List<MovieGenres> buildMovieGenresList(ResultSet r) throws SQLException{
        List<MovieGenres> mgs = new ArrayList<>();
        MovieGenres mg;
        while(r.next()){
            mg = new MovieGenres();
            mg.setId(r.getInt("id"));
            mg.setGenre(Genre.getEnumByKey( r.getInt("genre") ));

            //finding movie and setting it
            HashMap<String, String> search = new HashMap<>();
            search.put("id", String.valueOf( r.getInt("movie") ) );
            List<Movie> movies = new MovieDAO(con).search(search);
            mg.setMovie(movies.get(0));

            mgs.add(mg);
        }
        return mgs;
    }

    public MovieGenres save(MovieGenres object) throws SQLException{
        String sql = "insert into MovieGenres(genre, movie) values(?, ?)";
        PreparedStatement p = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        p.setInt(1, object.getGenre().getKey() );
        p.setInt(2, object.getMovie().getId());
        p.executeUpdate();
        object.setId( SQLUtils.getGeneratedId(p) );
        return object;
    }

    public MovieGenres update(MovieGenres object) throws SQLException{
        String sql = "update MovieGenres set genre = ?, movie = ? where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getGenre().getKey() );
        p.setInt(2, object.getMovie().getId());
        p.setInt(3, object.getId());
        p.executeUpdate();
        return object;
    }

    public boolean remove(MovieGenres object) throws SQLException{
        String sql = "delete from MovieGenres where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getId());
        p.executeUpdate();
        return true;
    }

    public List<MovieGenres> list() throws SQLException{
        String sql = "select * from MovieGenres";
        Statement s = con.createStatement();
        ResultSet r = s.executeQuery(sql);
        return buildMovieGenresList(r);
    }

    public List<MovieGenres> search(HashMap<String, String> searchParams) throws SQLException{
        String sql = "select * from MovieGenres where ";
        PreparedStatement p = SQLUtils.buildPreparedStatement(searchParams, sql, con);
        ResultSet r = p.executeQuery();
        return buildMovieGenresList(r);
    }

}
