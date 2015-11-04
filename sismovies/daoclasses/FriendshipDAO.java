package sismovies.daoclasses;

import sismovies.entities.Friendship;
import sismovies.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class FriendshipDAO implements GenericDAO<Friendship> {

    private Connection con;

    public FriendshipDAO(Connection con){
        this.con = con;
    }

    /*
    * private int id;
    private User sender;
    private User receiver;
    private boolean accepted;
    private Date requestDate;
    private Date responseDate;
    * */

    private List<Friendship> buildFriendshipList(ResultSet r) throws SQLException{
        List<Friendship> fs = new ArrayList<>();
        UserDAO userDAO = new UserDAO(con);
        Friendship f;
        while(r.next()){
            f = new Friendship();
            f.setId(r.getInt("id"));
            f.setAccepted(r.getBoolean("accepted"));
            f.setRequestDate(r.getDate("requestDate"));
            f.setResponseDate(r.getDate("responseDate"));
            f.setViewed(r.getBoolean("viewed"));
            f.setViewedByReceiver(r.getBoolean("viewedByReceiver"));

            //finding sender user and setting it
            HashMap<String, String> userSearch = new HashMap<>();
            userSearch.put("id", String.valueOf(r.getInt("sender")));
            List<User> users = userDAO.search(userSearch);
            f.setSender(users.get(0));

            //finding receiver user and setting it
            userSearch.clear();
            userSearch.put("id", String.valueOf( r.getInt("receiver") ));
            users.clear();
            users = userDAO.search(userSearch);
            f.setReceiver(users.get(0));

            fs.add(f);
        }
        return fs;

    }

    public Friendship save(Friendship object) throws SQLException{
        String sql = "insert into friendship(sender, receiver, accepted, requestDate, viewed, viewedByReceiver) values(?,?,?,?, ?, ?)";
        PreparedStatement p = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        p.setInt(1, object.getSender().getId());
        p.setInt(2, object.getReceiver().getId());
        p.setBoolean(3, object.isAccepted());
        p.setDate(4, new java.sql.Date(object.getRequestDate().getTime()));
        p.setBoolean(5, object.isViewed());
        p.setBoolean(6, object.isViewedByReceiver());
        p.executeUpdate();
        object.setId( SQLUtils.getGeneratedId(p) );
        return object;

    }

    public Friendship update(Friendship object) throws SQLException{
        String sql = "update friendship set sender = ?, receiver = ?, accepted = ?, requestDate = ?, responseDate = ?, viewed = ?, viewedByReceiver = ? where id = ? ";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getSender().getId());
        p.setInt(2, object.getReceiver().getId());
        p.setBoolean(3, object.isAccepted());
        p.setDate(4, new java.sql.Date(object.getRequestDate().getTime()));
        p.setBoolean(6, object.isViewed());
        p.setBoolean(7, object.isViewedByReceiver());
        try {
            p.setDate(5, new java.sql.Date(object.getResponseDate().getTime()));
        }
        catch(NullPointerException e){
            p.setDate(5, null);
        }
        p.setInt(8, object.getId());

        p.executeUpdate();
        return object;
    }

    public boolean remove(Friendship object) throws SQLException{
        String sql = "delete from friendship where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getId());
        p.executeUpdate();
        return true;
    }

    public List<Friendship> list() throws SQLException{
        String sql = "select * from friendship";
        Statement s = con.createStatement();
        ResultSet r = s.executeQuery(sql);
        return buildFriendshipList(r);
    }

    public List<Friendship> search(HashMap<String, String> searchParams) throws SQLException{
        String sql = "select * from friendship where ";
        PreparedStatement p = SQLUtils.buildPreparedStatement(searchParams, sql, con);
        ResultSet r = p.executeQuery();
        return buildFriendshipList(r);
    }
}
