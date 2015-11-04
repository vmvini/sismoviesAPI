package sismovies.daoclasses;

import sismovies.entities.Group;
import sismovies.entities.Movie;
import sismovies.entities.Topic;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class TopicDAO implements GenericDAO<Topic> {


    private Connection con;

    public TopicDAO(Connection con){
        this.con = con;
    }

    private List<Topic> buildTopicList(ResultSet r) throws SQLException{
        List<Topic> topics = new ArrayList<>();
        Topic topic;
        while(r.next()){
            topic = new Topic();
            topic.setId(r.getInt("id"));

            //finding group and setting it
            HashMap<String, String> groupSearch = new HashMap<String, String>();
            groupSearch.put("id", String.valueOf( r.getInt("sisgroup") ) );
            List<Group> groups = new GroupDAO(con).search(groupSearch);
            topic.setGroup(groups.get(0));

            //finding movie and setting it
            HashMap<String, String> movieSearch = new HashMap<String, String>();
            movieSearch.put("id", String.valueOf( r.getInt("movie") ) );
            List<Movie> movies = new MovieDAO(con).search(movieSearch);
            topic.setMovie(movies.get(0));

            topics.add(topic);
        }
        return topics;
    }

    public Topic save(Topic object) throws SQLException{
        String sql = "insert into topic(movie, sisgroup) values(?,?)";
        PreparedStatement p = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        p.setInt(1, object.getMovie().getId());
        p.setInt(2, object.getGroup().getId());
        p.executeUpdate();
        object.setId(SQLUtils.getGeneratedId(p));
        return object;
    }

    public  Topic update(Topic object) throws SQLException{
        String sql = "update topic set movie = ?, sisgroup = ? where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getMovie().getId());
        p.setInt(2, object.getGroup().getId());
        p.setInt(3, object.getId());
        p.executeUpdate();
        return object;
    }

    public boolean remove(Topic object) throws SQLException{
        String sql = "delete from topic where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getId());
        p.executeUpdate();
        return true;

    }

    public List<Topic> list() throws SQLException{
        String sql = "select * from topic";
        Statement s = con.createStatement();
        ResultSet r = s.executeQuery(sql);
        return buildTopicList(r);
    }

    public List<Topic> search(HashMap<String, String> searchParams) throws SQLException{
        String sql = "select * from topic where ";
        PreparedStatement p = SQLUtils.buildPreparedStatement(searchParams, sql, con);
        ResultSet r = p.executeQuery();
        return buildTopicList(r);
    }

}
