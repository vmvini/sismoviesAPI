package sismovies.daoclasses;

import sismovies.entities.Group;
import sismovies.entities.GroupUser;
import sismovies.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class GroupUserDAO implements GenericDAO<GroupUser> {

    private Connection con;

    public GroupUserDAO(Connection con){
        this.con = con;
    }

    /* private int id;
    private User user;
    private Group group;
    private Date requestDate;
    private Date responseDate;
    private boolean accepted;*/


    private List<GroupUser> buildGroupUserList(ResultSet r) throws SQLException{
        List<GroupUser> gus = new ArrayList<>();
        GroupUser gu;
        while(r.next()){
            gu = new GroupUser();
            gu.setId(r.getInt("id"));
            gu.setAccepted(r.getBoolean("accepted"));
            gu.setRequestDate(r.getDate("requestDate"));
            gu.setResponseDate(r.getDate("responseDate"));
            gu.setViewed(r.getBoolean("viewed"));
            gu.setViewedByOwner(r.getBoolean("viewedByOwner"));
            //finding group and setting it
            HashMap<String, String> search = new HashMap<>();
            search.put("id", String.valueOf( r.getInt("sisgroup") ) );
            List<Group> groups = new GroupDAO(con).search(search);
            gu.setGroup(groups.get(0));

            //finding user and setting it
            search.clear();
            search.put("id", String.valueOf( r.getInt("sisuser") ));
            List<User> users = new UserDAO(con).search(search);
            gu.setUser(users.get(0));

            gus.add(gu);

        }

        return gus;
    }

    public GroupUser save(GroupUser object) throws SQLException{
        String sql = "insert into groupUser(sisuser, sisgroup, requestDate, accepted, viewed, viewedByOwner ) values(?,?,?,?, ?, ?)";
        PreparedStatement p = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        p.setInt(1, object.getUser().getId());
        p.setInt(2, object.getGroup().getId());
        p.setDate(3, new java.sql.Date( object.getRequestDate().getTime() ));
        p.setBoolean(4, object.isAccepted());
        p.setBoolean(5, object.isViewed());
        p.setBoolean(6, object.isViewedByOwner());
        p.executeUpdate();
        object.setId( SQLUtils.getGeneratedId(p) );
        return object;
    }

    public GroupUser update(GroupUser object) throws SQLException{
        String sql = "update groupUser set sisuser = ?, sisgroup = ?, requestDate = ?, accepted = ?, responseDate = ?, viewed = ?, viewedByOwner = ? where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getUser().getId());
        p.setInt(2, object.getGroup().getId());
        p.setDate(3, new java.sql.Date( object.getRequestDate().getTime() ));
        p.setBoolean(4, object.isAccepted());
        p.setBoolean(6, object.isViewed());
        p.setBoolean(7, object.isViewedByOwner());
        try{
            p.setDate(5, new java.sql.Date( object.getResponseDate().getTime() ));
        }
        catch(NullPointerException e){
            p.setDate(5, null);
        }

        p.setInt(8, object.getId());
        p.executeUpdate();
        return object;
    }

    public boolean remove(GroupUser object) throws SQLException{
        String sql = "delete from groupUser where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getId());
        p.executeUpdate();
        return true;
    }

    public List<GroupUser> list() throws SQLException{
        String sql = "select * from groupUser";
        Statement s = con.createStatement();
        ResultSet r = s.executeQuery(sql);
        return buildGroupUserList(r);
    }

    public List<GroupUser> search(HashMap<String, String> searchParams) throws SQLException{
        String sql = "select * from groupUser where ";
        PreparedStatement p = SQLUtils.buildPreparedStatement(searchParams, sql, con);
        ResultSet r = p.executeQuery();
        return buildGroupUserList(r);
    }

}
