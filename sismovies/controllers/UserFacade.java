package sismovies.controllers;

import sismovies.daoclasses.SQLUtils;
import sismovies.entities.User;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 21/10/2015.
 */
public class UserFacade {

    private CentralFacade central;

    public UserFacade(CentralFacade central){
        this.central = central;
    }

    public boolean isAdmin(){
        if(central.getLoggedUser() != null){
            return central.getLoggedUser().isAdmin();
        }
        return false;
    }

    public List<User> getAllUsers() throws SQLException, UnsupportedOperationException{
        return central.getUserController().list();
    }


    public List<User> searchUser(String name, String city, String state)throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        if(name != null)
            search.put("name", SQLUtils.addSearchIdentifier(name));
        if(city != null)
            search.put("city", SQLUtils.addSearchIdentifier(city));
        if(state != null)
            search.put("state", SQLUtils.addSearchIdentifier(state));

        return central.getUserController().search(search);

    }

    public void updateUser(String meID, String name, String nickName, String email, String password, String birthDate, String city, String state, String picturePath, String admin ) throws SQLException, UnsupportedOperationException, ParseException{
        //get user object
        HashMap<String, String> search = new HashMap<>();
        search.put("id", meID);
        List<User> users = central.getUserController().search(search);
        User user = users.get(0);

        user.setName(name);
        user.setNickName(nickName);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthDate(SQLUtils.stringToDate(birthDate));
        user.setCity(city);
        user.setState(state);
        user.setPicturePath(picturePath);
        user.setAdmin(SQLUtils.stringToBool(admin));

        central.login(user);
        central.getUserController().update(user);
    }

    public User registerUser(String name, String nickName, String email, String password, String birthDate, String city, String state, String picturePath, String admin) throws SQLException, UnsupportedOperationException, ParseException{
        User user = new User();
        user.setName(name);
        user.setNickName(nickName);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthDate(SQLUtils.stringToDate(birthDate));
        user.setCity(city);
        user.setState(state);
        user.setPicturePath(picturePath);
        user.setAdmin(SQLUtils.stringToBool(admin));

        user = (User)central.getUserController().save(user);
        return user;
    }

    public void login(String email, String password) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("email", email);
        search.put("password", password);
        List<User> users = central.getUserController().search(search);
        if(users.size() > 0){
            User user = users.get(0);
            central.login(user);
        }


    }

    public void logoff() throws SQLException{
        central.logoff();
    }


    public void removeUser(String meID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("id", meID);
        List<User> users = central.getUserController().search(search);
        User user = users.get(0);
        central.getUserController().remove(user);
        central.logoff();
    }

    public User getUser(String userID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("id", userID);
        List<User> users = central.getUserController().search(search);
        User user = users.get(0);
        return user;
    }


}
