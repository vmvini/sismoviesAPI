package sismovies.controllers;

import sismovies.entities.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class TopicFacade {

    private CentralFacade central;
    public TopicFacade(CentralFacade central){
        this.central = central;
    }

    //topicos
    public void createTopic(String groupID, String movieID) throws SQLException, UnsupportedOperationException{
        //get group
        HashMap<String, String> search = new HashMap<>();
        search.put("id", groupID);
        List<Group> groups = central.getGroupController().search(search);
        Group group = groups.get(0);

        //get movie
        search.clear();
        search.put("id", movieID);
        List<Movie> movies = central.getMovieController().search(search);
        Movie movie = movies.get(0);

        Topic topic = new Topic();
        topic.setMovie(movie);
        topic.setGroup(group);

        central.getTopicController().save(topic);
    }



    public void commentOnTopic(String topicID, String meID, String comment) throws SQLException, UnsupportedOperationException{
        //get topic
        HashMap<String, String> search = new HashMap<>();
        search.put("id", topicID);
        List<Topic> topics = central.getTopicController().search(search);
        Topic topic = topics.get(0);

        //get User
        search.clear();
        search.put("id", meID);
        List<User> users = central.getUserController().search(search);
        User user = users.get(0);

        TopicComment tc = new TopicComment();
        tc.setUser(user);
        tc.setComment(comment);
        tc.setTopic(topic);
        tc.setDate(new Date());

        central.getTopicCommentController().save(tc);


    }

    public Topic searchTopics(String movieTitle, String groupID)throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("title", movieTitle);
        List<Movie> movies = central.getMovieController().search(search);
        if(movies.size() == 0)
            return null;
        Movie movie = movies.get(0);

        //search topic
        search.clear();
        search.put("movie", String.valueOf( movie.getId() ) );
        search.put("sisgroup", groupID);
        List<Topic> topics = central.getTopicController().search(search);
        if(topics.size()==0)
            return null;
        return topics.get(0);


    }

    public List<Topic> showAllTopics(String groupID)throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("sisgroup", groupID);
        return central.getTopicController().search(search);
    }

    public List<TopicComment> showAllTopicComments(String topicID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("topic", topicID);
        return central.getTopicCommentController().search(search);
    }

}
