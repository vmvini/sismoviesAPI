package sismovies.daoclasses;

import sismovies.entities.Group;
import sismovies.entities.User;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class GroupDAO implements GenericDAO<Group> {

    private Connection con;

    public GroupDAO(Connection con){
        this.con = con;
    }

/*
*  private int id;
    private User owner;
    private String name;
    private String description;
    private Date creationDate;

*
* */

    private List<Group> buildGroupList(ResultSet r) throws SQLException{
        List<Group> groups = new ArrayList<>();
        Group group;
        while(r.next()){
            group = new Group();
            group.setId(r.getInt("id"));
            group.setName(r.getString("name"));
            group.setDescription(r.getString("description"));
            group.setCreationDate(r.getDate("creationDate"));

            //finding user and setting
            HashMap<String, String> userSearch = new HashMap<>();
            userSearch.put("id", String.valueOf(r.getInt("owner")));
            List<User> users = new UserDAO(con).search(userSearch);
            group.setOwner(users.get(0));

            groups.add(group);
        }
        return groups;
    }

    public Group save(Group object) throws SQLException{
        String sql = "insert into sisgroup(owner, name, description, creationDate) values (?,?,?,?)";
        PreparedStatement p = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        p.setInt(1, object.getOwner().getId());
        p.setString(2, object.getName());
        p.setString(3, object.getDescription());
        p.setDate(4, new java.sql.Date(object.getCreationDate().getTime()));
        p.executeUpdate();
        object.setId( SQLUtils.getGeneratedId(p) );
        return object;
    }

    public Group update(Group object) throws SQLException{
        String sql = "update sisgroup set owner = ?, name = ?, description = ?, creationDate = ? where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getOwner().getId());
        p.setString(2, object.getName());
        p.setString(3, object.getDescription());
        p.setDate(4, new java.sql.Date(object.getCreationDate().getTime()));
        p.setInt(5, object.getId());
        p.executeUpdate();
        return object;
    }

    public boolean remove(Group object) throws SQLException{
        String sql = "delete from sisgroup where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getId());
        p.executeUpdate();
        return true;
    }

    public List<Group> list() throws SQLException{
        String sql = "select * from sisgroup";
        Statement s = con.createStatement();
        ResultSet r = s.executeQuery(sql);
        return buildGroupList(r);
    }

    public List<Group> search(HashMap<String, String> searchParams) throws SQLException{
        String sql = "select * from sisgroup where ";
        PreparedStatement p = SQLUtils.buildPreparedStatement(searchParams, sql, con);
        ResultSet r = p.executeQuery();
        return buildGroupList(r);
    }

}
