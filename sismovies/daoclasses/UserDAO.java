package sismovies.daoclasses;

import sismovies.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class UserDAO implements GenericDAO<User> {

    private Connection con;

    public UserDAO(Connection con){
        this.con = con;
    }



    private List<User> buildUserList(ResultSet r) throws SQLException{
        User user;
        List<User> users = new ArrayList<>();
        while(r.next()){
            user = new User();
            user.setName(r.getString("name"));
            user.setNickName(r.getString("nickName"));
            user.setEmail(r.getString("email"));
            user.setPassword(r.getString("password"));
            user.setBirthDate(r.getDate("birthDate"));
            user.setCity(r.getString("city"));
            user.setState(r.getString("state"));
            user.setPicturePath(r.getString("picturePath"));
            user.setAdmin(r.getBoolean("admin"));
            user.setId(r.getInt("id"));
            users.add(user);
        }
        return users;
    }


    public User save(User object) throws SQLException{
        String sql = "insert into sisuser(name, nickName, email, password, birthDate, city, state, picturePath, admin) values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement p = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        p.setString(1, object.getName());
        p.setString(2, object.getNickName());
        p.setString(3, object.getEmail());
        p.setString(4, object.getPassword());
        p.setDate(5, new java.sql.Date(object.getBirthDate().getTime()));
        p.setString(6, object.getCity());
        p.setString(7, object.getState());
        p.setString(8, object.getPicturePath());
        p.setBoolean(9, object.isAdmin());
        p.executeUpdate();
        object.setId( SQLUtils.getGeneratedId(p) );
        return object;

    }

    public User update(User object) throws SQLException{
        String sql = "update sisuser set name = ?, nickName = ?, email = ?, password = ?, birthDate = ?, city = ?, state = ?, picturePath = ?, admin = ? where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, object.getName());
        p.setString(2, object.getNickName());
        p.setString(3, object.getEmail());
        p.setString(4, object.getPassword());
        p.setDate(5, new Date(object.getBirthDate().getTime()));
        p.setString(6, object.getCity());
        p.setString(7, object.getState());
        p.setString(8, object.getPicturePath());
        p.setBoolean(9, object.isAdmin());
        p.setInt(10, object.getId());
        p.executeUpdate();
        return object;

    }

    public boolean remove(User  object) throws SQLException{
        String sql = "delete from sisuser where id = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setInt(1, object.getId());
        p.executeUpdate();
        return true;
    }

    public List<User> list() throws SQLException{
        String sql = "select * from sisuser";
        Statement s = con.createStatement();
        ResultSet r = s.executeQuery(sql);
        List<User> users = buildUserList(r);
        return users;
    }

    public List<User> search(HashMap<String, String> searchParams) throws SQLException{
        String sql = "select * from sisuser where ";
        PreparedStatement p = SQLUtils.buildPreparedStatement(searchParams, sql, con);
        ResultSet r = p.executeQuery();
        return buildUserList(r);
    }
}
