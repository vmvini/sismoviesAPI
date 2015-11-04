package sismovies.daoclasses;

import sismovies.entities.Topic;
import sismovies.entities.TopicComment;
import sismovies.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class TopicCommentDAO implements GenericDAO<TopicComment> {


    private Connection con;

    public TopicCommentDAO(Connection con){
        this.con = con;
    }

    /*private int id;
    private Topic topic;
    private User user;
    private Date date;
    private String comment;*/

    private List<TopicComment> buildTopicCommentList(ResultSet r)throws SQLException{
        List<TopicComment> tcs = new ArrayList<>();
        TopicComment tc;
        while(r.next()){
            tc = new TopicComment();
            tc.setId(r.getInt("id"));
            tc.setComment(r.getString("comment"));
            tc.setDate(r.getDate("date"));

            //finding topic
            HashMap<String, String> search = new HashMap<>();
            search.put("id", String.valueOf( r.getInt("topic") ));
            List<Topic> topics = new TopicDAO(con).search(search);
            tc.setTopic(topics.get(0));

            //finding user
            search.clear();
            search.put("id", String.valueOf( r.getInt("sisuser") ));
            List<User> users = new UserDAO(con).search(search);
            tc.setUser(users.get(0));

            tcs.add(tc);
        }
        return tcs;
    }

    public TopicComment save(TopicComment object) throws SQLException{
        String sql = "insert into TopicComment(topic, sisuser, date, comment) values(?,?,?,?)";
        PreparedStatement p = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        p.setInt(1, object.getTopic().getId());
        p.setInt(2, object.getUser().getId());
        p.setDate(3, new java.sql.Date( object.getDate().getTime() ));
        p.setString(4, object.getComment());
        p.executeUpdate();
        object.setId( SQLUtils.getGeneratedId(p) );
        return object;
    }

    public TopicComment update(TopicComment object) throws SQLException{
        String sql = "update TopicComment set topic = ?, sisuser = ?, date = ?, comment = ? where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getTopic().getId());
        p.setInt(2, object.getUser().getId());
        p.setDate(3, new java.sql.Date( object.getDate().getTime() ));
        p.setString(4, object.getComment());
        p.setInt(5, object.getId());
        p.executeUpdate();
        return object;
    }

    public boolean remove(TopicComment object) throws SQLException{
        String sql = "delete from TopicComment where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getId());
        p.executeUpdate();
        return true;

    }

    public List<TopicComment> list() throws SQLException{
        String sql = "select * from TopicComment";
        Statement s = con.createStatement();
        ResultSet r = s.executeQuery(sql);
        return buildTopicCommentList(r);
    }

    public List<TopicComment> search(HashMap<String, String> searchParams) throws SQLException{
        String sql = "select * from TopicComment where ";
        PreparedStatement p = SQLUtils.buildPreparedStatement(searchParams, sql, con);
        ResultSet r = p.executeQuery();
        return buildTopicCommentList(r);
    }


}
