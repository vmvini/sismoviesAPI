package sismovies.controllers;

import sismovies.entities.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
    public class CentralFacade {

    private User loggedUser;
    private ControllerFactory controllerFactory;
    private GenericController friendshipController;

    private GenericController groupUserController;

    private GenericController groupController;

    private GenericController middleRateController;

    private GenericController movieController;

    private GenericController movieActorController;



    private GenericController movieGenreController;



    private GenericController movieRateController;


    private GenericController recommendedMovieController;

    private GenericController topicController;

    private GenericController topicCommentController;

    private GenericController userController;

    public CentralFacade() throws SQLException{
        loggedUser = null;
        controllerFactory = new ControllerFactory(loggedUser);

    }






    private void clearControllers(){
        friendshipController = null;
        groupUserController = null;
        groupController = null;
        middleRateController = null;
        movieController = null;
        movieActorController = null;

        movieGenreController = null;

        movieRateController = null;

        recommendedMovieController = null;
        topicController = null;
        topicCommentController = null;
        userController = null;
    }

    public User getLoggedUser(){
        return loggedUser;
    }


    public void login(User user) throws SQLException{
        //verificar usuario
        //recriando fabrica

        clearControllers();
        loggedUser = user;
        controllerFactory = new ControllerFactory(loggedUser);
    }

    public void logoff() throws SQLException{
        loggedUser = null;
        //recriando fabrica
        clearControllers();
        controllerFactory = new ControllerFactory(loggedUser);
    }

    public GenericController getFriendshipController(){
        if(friendshipController == null)
            friendshipController =  controllerFactory.buildFriendshipController();
        return friendshipController;
    }

    public GenericController getGroupUserController(){
        if(groupUserController == null)
            groupUserController =  controllerFactory.buildGroupUserController();
        return groupUserController;
    }

    public GenericController getGroupController(){
        if(groupController == null)
            groupController = controllerFactory.buildGroupController();
        return groupController;
    }

    public GenericController getMiddleRateController(){
        if(middleRateController == null)
            middleRateController =  controllerFactory.buildMiddleRateController();
        return middleRateController;
    }

    public GenericController getMovieController(){
        if(movieController == null)
            movieController =  controllerFactory.buildMovieController();
        return movieController;
    }

    public GenericController getMovieActorController(){
        if(movieActorController == null)
            movieActorController = controllerFactory.buildMovieActorController();
        return movieActorController;
    }



    public GenericController getMovieGenreController(){
        if(movieGenreController == null)
            movieGenreController = controllerFactory.buildMovieGenreController();
        return movieGenreController;
    }


    public GenericController getMovieRateController(){
        if(movieRateController == null)
            movieRateController = controllerFactory.buildMovieRateController();
        return movieRateController;
    }

    public GenericController getRecommendedMovieController(){
        if(recommendedMovieController == null)
            recommendedMovieController = controllerFactory.buildRecommendedMovieController();
        return recommendedMovieController;
    }

    public GenericController getTopicController(){
        if(topicController == null)
            topicController = controllerFactory.buildTopicController();
        return topicController;
    }

    public GenericController getTopicCommentController(){
        if(topicCommentController == null)
            topicCommentController = controllerFactory.buildTopicCommentController();
        return topicCommentController;
    }

    public GenericController getUserController(){
        if(userController == null)
            userController = controllerFactory.buildUserController();
        return userController;
    }




}
