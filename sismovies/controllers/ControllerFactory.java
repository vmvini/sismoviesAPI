package sismovies.controllers;

import sismovies.daoclasses.DAOFactory;
import sismovies.entities.User;

import java.sql.SQLException;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class ControllerFactory {

    private User loggedUser;
    private DAOFactory daoFactory;
    private ModelBehavior behaviorUnallowed;
    private ModelBehavior behaviorAllowed;

    public ControllerFactory(User loggedUser) throws SQLException{
        this.loggedUser = loggedUser;
        daoFactory = new DAOFactory();
        behaviorAllowed = new Allowed();
        behaviorUnallowed = new Unallowed("Não é possível realizar essa operacao");
    }

    public GenericController buildFriendshipController(){
        if(loggedUser == null)
            return new GenericController(behaviorUnallowed, behaviorUnallowed, behaviorUnallowed, daoFactory.buildFriendshipDAO() );
        else
            return new GenericController(behaviorAllowed, behaviorAllowed, behaviorAllowed, daoFactory.buildFriendshipDAO());

    }

    public GenericController buildGroupUserController(){
        if(loggedUser == null)
            return new GenericController(behaviorUnallowed,behaviorUnallowed,behaviorUnallowed, daoFactory.buildGroupUserDAO());
        else
            return new GenericController(behaviorAllowed,behaviorAllowed, behaviorAllowed, daoFactory.buildGroupUserDAO());

    }

    public GenericController buildGroupController(){
        if(loggedUser == null)
            return new GenericController(behaviorUnallowed, behaviorUnallowed, behaviorAllowed, daoFactory.buildGroupDAO());
        else
            return new GenericController(behaviorAllowed,behaviorAllowed,behaviorAllowed, daoFactory.buildGroupDAO());

    }

    public GenericController buildMiddleRateController(){
        return new GenericController(behaviorAllowed, behaviorAllowed, behaviorAllowed, daoFactory.buildMiddleRateDAO());

    }
    //ModelBehavior saveBehavior, ModelBehavior removeBehavior, ModelBehavior listBehavior, GenericDAO dao
    public GenericController buildMovieController(){
        if(loggedUser == null)
            return new MovieController(behaviorUnallowed, behaviorUnallowed, behaviorAllowed, daoFactory.buildMovieDAO() );
        else{
            if(loggedUser.isAdmin())
                return new MovieController(behaviorAllowed, behaviorAllowed, behaviorAllowed, daoFactory.buildMovieDAO());
            else
                return new MovieController(behaviorUnallowed, behaviorUnallowed, behaviorAllowed, daoFactory.buildMovieDAO() );
        }

    }

    public GenericController buildMovieActorController(){
        if(loggedUser == null)
            return new GenericController(behaviorUnallowed, behaviorUnallowed, behaviorAllowed, daoFactory.buildMovieActorDAO());
        else{
            if(loggedUser.isAdmin())
                return new GenericController(behaviorAllowed, behaviorAllowed, behaviorAllowed, daoFactory.buildMovieActorDAO());
            else
                return new GenericController(behaviorUnallowed, behaviorUnallowed, behaviorAllowed, daoFactory.buildMovieActorDAO());
        }
    }



    public GenericController buildMovieGenreController(){
        if(loggedUser == null)
            return new GenericController(behaviorUnallowed, behaviorUnallowed, behaviorAllowed, daoFactory.buildMovieGenreDAO());
        else{
            if(loggedUser.isAdmin())
                return new GenericController(behaviorAllowed, behaviorAllowed, behaviorAllowed,  daoFactory.buildMovieGenreDAO());
            else
                return new GenericController(behaviorUnallowed, behaviorUnallowed, behaviorAllowed,   daoFactory.buildMovieGenreDAO());
        }
    }



    public GenericController buildMovieRateController(){
        if(loggedUser == null)
            return new GenericController(behaviorUnallowed, behaviorUnallowed, behaviorAllowed, daoFactory.buildMovieRateDAO());
        else{
            if(loggedUser.isAdmin())
                return new GenericController(behaviorAllowed, behaviorAllowed, behaviorAllowed, daoFactory.buildMovieRateDAO());
            else
                return new GenericController(behaviorAllowed, behaviorUnallowed, behaviorAllowed,   daoFactory.buildMovieRateDAO());
        }
    }


    public GenericController buildRecommendedMovieController(){
        if(loggedUser == null)
            return new GenericController(behaviorUnallowed, behaviorUnallowed, behaviorAllowed, daoFactory.buildRecommendedMovieDAO());
        else{
            if(loggedUser.isAdmin())
                return new GenericController(behaviorAllowed, behaviorAllowed, behaviorAllowed, daoFactory.buildRecommendedMovieDAO());
            else
                return new GenericController(behaviorAllowed, behaviorUnallowed, behaviorAllowed,   daoFactory.buildRecommendedMovieDAO());
        }
    }

    public GenericController buildTopicController(){
        if(loggedUser == null)
            return new GenericController(behaviorUnallowed, behaviorUnallowed, behaviorUnallowed, daoFactory.buildTopicDAO());
        else{
            if(loggedUser.isAdmin())
                return new GenericController(behaviorAllowed, behaviorAllowed, behaviorAllowed, daoFactory.buildTopicDAO());
            else
                return new GenericController(behaviorAllowed, behaviorUnallowed, behaviorAllowed,   daoFactory.buildTopicDAO());
        }
    }

    public GenericController buildTopicCommentController(){
        if(loggedUser == null)
            return new GenericController(behaviorUnallowed, behaviorUnallowed, behaviorUnallowed, daoFactory.buildTopicCommentDAO());
        else{
            if(loggedUser.isAdmin())
                return new GenericController(behaviorAllowed, behaviorAllowed, behaviorAllowed, daoFactory.buildTopicCommentDAO());
            else
                return new GenericController(behaviorAllowed, behaviorAllowed, behaviorAllowed,   daoFactory.buildTopicCommentDAO());
        }
    }

    public GenericController buildUserController(){
        if(loggedUser == null)
            return new GenericController(behaviorAllowed, behaviorUnallowed, behaviorAllowed, daoFactory.buildUserDAO());
        else{
            if(loggedUser.isAdmin())
                return new GenericController(behaviorAllowed, behaviorAllowed, behaviorAllowed, daoFactory.buildUserDAO());
            else
                return new GenericController(behaviorAllowed, behaviorAllowed, behaviorAllowed,   daoFactory.buildUserDAO());
        }

    }

}
