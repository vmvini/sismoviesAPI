package sismovies.daoclasses;

import sismovies.entities.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class MovieDAO implements GenericDAO<Movie> {

    private Connection con;
    public MovieDAO(Connection con){
        this.con = con;
    }
    /*
    *  private int id;
    private String title;
    private Date year;
    private String plot;
    * */

    private List<Movie> buildMovieList(ResultSet r) throws SQLException{
        List<Movie> movies = new ArrayList<>();
        Movie movie;
        while(r.next()){
            movie = new Movie();
            movie.setId(r.getInt("id"));
            movie.setTitle(r.getString("title"));
            movie.setPlot(r.getString("plot"));
            movie.setYear(r.getInt("year"));
            movie.setPicture(r.getString("picture"));
            movie.setDirector(r.getString("director"));
            movies.add(movie);
        }
        return movies;
    }

    public Movie save(Movie object) throws SQLException{
        String sql = "insert into movie(title, year, plot, picture, director) values(?,?,?, ?, ?)";
        PreparedStatement p = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        p.setString(1, object.getTitle());
        p.setInt(2, object.getYear());
        p.setString(3, object.getPlot());
        p.setString(4, object.getPicture());
        p.setString(5, object.getDirector());
        p.executeUpdate();
        object.setId( SQLUtils.getGeneratedId(p) );

        return object;
    }

    public Movie update(Movie object) throws SQLException{
        String sql = "update movie set title = ?, year = ?, plot = ?, picture = ?, director = ? where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, object.getTitle());
        p.setInt(2, object.getYear());
        p.setString(3, object.getPlot());
        p.setString(4, object.getPicture());
        p.setString(5, object.getDirector());
        p.setInt(6, object.getId());
        p.executeUpdate();
        return object;

    }

    public boolean remove(Movie object) throws SQLException{
        String sql = "delete from movie where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getId());
        p.executeUpdate();
        return true;
    }

    public List<Movie> list() throws SQLException{
        String sql = "select * from movie";
        Statement s = con.createStatement();
        ResultSet r = s.executeQuery(sql);
        return buildMovieList(r);
    }

    public List<Movie> sortedList(String order) throws SQLException{
        String sql = "select * from movie " + order;
        Statement s = con.createStatement();
        ResultSet r = s.executeQuery(sql);
        return buildMovieList(r);
    }


    public List<Movie> smartSearch(String search, String searchBy, String genre, String orderBy) throws SQLException{

        List<String> values = new ArrayList<>();
        String sqlGenre = "";
        String sqlOrderBy = "";
        String sqlRateJoin = "";
        String sqlSearchBy = "";
        String sqlActorJoin = "";
        String sqlAnd = "";
        String beforefrom = "select distinct  m.id, m.title, m.year, m.plot, m.picture, m.director from ";

        String from = " movie m ";


        if(orderBy.equals("0")){
            sqlOrderBy = " ;";
        }
        else if(orderBy.equals("title")){
            sqlOrderBy = " order by m.title asc; ";
        }
        else if(orderBy.equals("year")){
            sqlOrderBy = " order by m.year desc; ";
        }
        else if(orderBy.equals("rating")){
            sqlOrderBy = " order by mr.mrate desc; ";
            sqlRateJoin = " join middlerate mr on mr.movie = m.id ";
        }

        if(search.isEmpty()){
            searchBy = "0";
        }

        if(searchBy.equals("0")){

        }
        else{
            values.add(SQLUtils.addSearchIdentifier(search));
            if(searchBy.equals("title")){

                sqlSearchBy = " where lower(m.title) like ? ";
            }
            else if(searchBy.equals("director")){

                sqlSearchBy = " where lower(m.director) like ? ";
            }
            else if(searchBy.equals("actor")){

                sqlSearchBy = " where lower(ma.actor) like ? ";
                sqlActorJoin = " join movieactors ma on ma.movie = m.id ";
            }
        }


        if(!genre.equals("0")){
            String where = "";
            if(searchBy.equals("0"))
                where = " where ";
            sqlGenre = where + " m.id in (select mg.movie from moviegenres mg where mg.genre = ?) ";
            values.add(genre);
        }

        if(!sqlSearchBy.isEmpty() && !sqlGenre.isEmpty()){
            sqlAnd = " and ";
        }
        String finalSQL = beforefrom + from + sqlRateJoin + sqlActorJoin  + sqlSearchBy + sqlAnd + sqlGenre + sqlOrderBy;

        PreparedStatement p = con.prepareStatement(finalSQL);

        SQLUtils.setPreparedStatementValues(values, p);

        return buildMovieList(p.executeQuery());



    }



    public List<Movie> search(HashMap<String, String> searchParams) throws SQLException{
        String sql = "select * from movie where ";
        PreparedStatement p = SQLUtils.buildPreparedStatement(searchParams, sql, con);
        ResultSet r = p.executeQuery();
        return buildMovieList(r);
    }

}
