package sismovies.controllers;

import sismovies.entities.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 14/10/2015.
 */
public class MovieFacade {

    private CentralFacade central;

    public MovieFacade(CentralFacade central){
        this.central = central;
    }

    public Movie registerMovie(String title, String year,String plot, String director, String picture) throws SQLException, UnsupportedOperationException{
        Movie movie = new Movie();
        movie.setPlot(plot);
        movie.setTitle(title);
        movie.setYear(Integer.valueOf(year));
        movie.setDirector(director);
        movie.setPicture(picture);

        movie = (Movie)central.getMovieController().save(movie);

        return movie;
    }

    public List<String> getActors(String movieID)throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("movie", movieID );
        List<MovieActors> mas = central.getMovieActorController().search(search);

        List<String> actors = new ArrayList<>();
        for(MovieActors ma : mas){
            actors.add(ma.getActor());
        }
        return actors;
    }

    public void removeActor(String actorName, String movieID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("movie", movieID);
        search.put("actor", actorName);
        List<MovieActors> mas = central.getMovieActorController().search(search);
        central.getMovieActorController().remove(mas.get(0));

    }



    public void removeGenre(String movieGenreID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("id", movieGenreID);
        List<MovieGenres> mgs = central.getMovieGenreController().search(search);
        central.getMovieGenreController().remove(mgs.get(0));
    }

    public MovieActors addActor(String movieID, String actorName) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("id", movieID);
        List<Movie> movies = central.getMovieController().search(search);
        Movie movie = movies.get(0);

        MovieActors ma = new MovieActors();
        ma.setMovie(movie);
        ma.setActor(actorName);
        ma = (MovieActors)central.getMovieActorController().save(ma);
        return ma;

    }

    public MovieGenres addGenre(String movieID, Genre genre) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("id", movieID);
        List<Movie> movies = central.getMovieController().search(search);
        Movie movie = movies.get(0);

        MovieGenres mg = new MovieGenres();
        mg.setMovie(movie);
        mg.setGenre(genre);
        mg = (MovieGenres)central.getMovieGenreController().save(mg);
        return mg;
    }


    public List<MovieGenres> getGenres(String movieID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("movie", movieID );
        List<MovieGenres> mgs = central.getMovieGenreController().search(search);
        return mgs;

    }

    public List<MovieRate> getRates(String movieID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("movie", movieID );
        List<MovieRate> mrs = central.getMovieRateController().search(search);
        return mrs;
    }




    public void removeMovie(String movieID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("id", movieID);
        List<Movie> movies = central.getMovieController().search(search);
        Movie movie = movies.get(0);
        central.getMovieController().remove(movie);
    }

    public List<Movie> allMovies() throws SQLException, UnsupportedOperationException{
        return central.getMovieController().list();
    }

    public List<Movie> allMoviesSortedByRate() throws SQLException, UnsupportedOperationException{
        List<MiddleRate> mrs = central.getMiddleRateController().list();
        List<Movie> movies = new ArrayList<>();
        for(MiddleRate mr : mrs){
            movies.add(mr.getMovie());
        }
        return movies;
    }



    public List<Movie> allMoviesOfGenre(Genre genre) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("genre", Integer.toString(genre.getKey()));
        List<MovieGenres> mgs = central.getMovieGenreController().search(search);
        List<Movie> movies = new ArrayList<>();
        for(MovieGenres mg : mgs){
            movies.add(mg.getMovie());
        }
        return movies;

    }

    public List<Movie> allMoviesSortedByTitle() throws SQLException, UnsupportedOperationException{
        MovieController mc = (MovieController)central.getMovieController();
        return mc.listByTitle();
    }

    public List<Movie> allMoviesSortedByYear() throws SQLException, UnsupportedOperationException{
        MovieController mc = (MovieController)central.getMovieController();
        return mc.listByYear();
    }

    public List<Movie> allMoviesWithActor(String actorName) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("actor", actorName);
        List<MovieActors> mas = central.getMovieActorController().search(search);
        List<Movie> movies = new ArrayList<>();
        for(MovieActors ma : mas){
            movies.add(ma.getMovie());
        }
        return movies;
    }



    public List<Movie> searchMovie(String title)throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("title", title);
        return central.getMovieController().search(search);
    }

    public Movie getMovie(String movieID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("id", movieID);
        List<Movie> ms = central.getMovieController().search(search);
        return ms.get(0);
    }


    public List<Movie> advancedSearch(String search, String searchBy, String Genre, String order ) throws SQLException, UnsupportedOperationException{
        MovieController mc = (MovieController)central.getMovieController();
        return mc.smartSearch(search, searchBy, Genre, order);
    }


    public String getGenreDescription(int key ){
        return Genre.getEnumByKey(key).getDescription();
    }




}
