package sismovies.controllers;

import sismovies.entities.MiddleRate;
import sismovies.entities.Movie;
import sismovies.entities.MovieRate;
import sismovies.entities.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class RateFacade {

    private CentralFacade central;

    public RateFacade(CentralFacade central){
        this.central = central;
    }

    public List<MovieRate> getMovieRates(String movieID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("movie", movieID);
        return central.getMovieRateController().search(search);
    }


    public void doRate(String rate, String movieID, String meID, String comment) throws SQLException, UnsupportedOperationException{
        //criar movie object
        HashMap<String, String> search = new HashMap<>();
        search.put("id", movieID);
        List<Movie> movies = central.getMovieController().search(search);
        Movie movie = movies.get(0);

        //criar user object
        search.clear();
        search.put("id", meID);
        List<User> users = central.getUserController().search(search);
        User user = users.get(0);

        MovieRate mr = new MovieRate();
        mr.setUser(user);
        mr.setMovie(movie);
        mr.setRate(Integer.valueOf(rate));
        mr.setDate(new Date());
        mr.setComment(comment);

        central.getMovieRateController().save(mr);
    }

    public int getNumberOfStars(Movie movie) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        String movieID = String.valueOf(movie.getId());

        search.put("movie", movieID);
        List<MiddleRate> rates = central.getMiddleRateController().search(search);
        int rate = rates.get(0).getMrate();
        return rate;

    }

    public boolean userAlreadyDidRate(User user, String movieID) throws SQLException, UnsupportedOperationException{
        String userID = String.valueOf(user.getId());
        HashMap<String, String> search = new HashMap<>();
        search.put("sisuser", userID);
        search.put("movie", movieID);
        List<MovieRate> rates = central.getMovieRateController().search(search);
        if(rates.size() > 0)
            return true;
        return false;
    }

    public List<Movie> getAllMoviesRatedByUser(String userID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("sisuser", userID);
        List<MovieRate> mrs = central.getMovieRateController().search(search);
        List<Movie> movies = new ArrayList<>();
        for(MovieRate mr : mrs){
            movies.add(mr.getMovie());
        }
        return movies;
    }


}
