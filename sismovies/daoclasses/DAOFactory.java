package sismovies.daoclasses;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by marcusviniv on 20/10/2015.
 */
public class DAOFactory {

    private Connection con;

    public DAOFactory() throws SQLException{
        con = DBConnection.getConnection();
    }

    public GenericDAO buildFriendshipDAO(){
        return new FriendshipDAO(con);
    }

    public GenericDAO buildGroupDAO(){
        return new GroupDAO(con);
    }

    public GenericDAO buildGroupUserDAO(){
        return new GroupUserDAO(con);
    }

    public GenericDAO buildMiddleRateDAO(){
        return new MiddleRateDAO(con);
    }

    public GenericDAO buildMovieActorDAO(){
        return new MovieActorDAO(con);
    }

    public GenericDAO buildMovieDAO(){
        return new MovieDAO(con);
    }



    public GenericDAO buildMovieGenreDAO(){
        return new MovieGenreDAO(con);
    }



    public GenericDAO buildMovieRateDAO(){
        return new MovieRateDAO(con);
    }



    public GenericDAO buildRecommendedMovieDAO(){
        return new RecommendedMovieDAO(con);
    }

    public GenericDAO buildTopicCommentDAO(){
        return new TopicCommentDAO(con);
    }

    public GenericDAO buildTopicDAO(){
        return new TopicDAO(con);
    }

    public GenericDAO buildUserDAO(){
        return new UserDAO(con);
    }

}
