package sismovies.controllers;

import sismovies.daoclasses.GenericDAO;
import sismovies.daoclasses.MovieDAO;
import sismovies.entities.Movie;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by marcusviniv on 21/10/2015.
 */
public class MovieController extends GenericController{



    public MovieController(ModelBehavior saveBehavior, ModelBehavior removeBehavior, ModelBehavior listBehavior, GenericDAO dao){
        super(saveBehavior, removeBehavior, listBehavior, dao);

    }

    public List<Movie> listByYear() throws SQLException, UnsupportedOperationException{

        return ((MovieDAO)dao).sortedList(" order by year desc");
    }

    public List<Movie> listByTitle() throws SQLException, UnsupportedOperationException{

        return ((MovieDAO)dao).sortedList(" order by title asc");
    }

    public List<Movie> smartSearch(String search, String searchBy, String genre, String orderBy) throws SQLException, UnsupportedOperationException{
        return ((MovieDAO)dao).smartSearch(search, searchBy, genre, orderBy);
    }

}
