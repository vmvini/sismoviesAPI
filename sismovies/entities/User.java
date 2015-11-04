package sismovies.entities;

import sismovies.daoclasses.SQLUtils;

import java.util.Date;

/**
 * Created by marcusviniv on 14/10/2015.
 */
public class User {

    private int id;
    private String name;
    private String nickName;
    private String email;
    private String password;
    private Date birthDate;
    private String city;
    private String state;
    private String picturePath;
    private boolean admin;

    public User(){

    }

    public User(String name, String nickName, String email, String password, Date birthDate, String city, String state, String picturePath, boolean admin){
        id = 0;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.city = city;
        this.state = state;
        this.picturePath = picturePath;
        this.admin = admin;
    }

    public String toString(){
        return "id : " + id + " name: " + name + " nickname: " + nickName + " email: " + email + " password: " + password + " birthDate: " + birthDate + " city: " + city + " state: " + state + " picturePath: " + picturePath + " admin: " + admin;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public String getNickName(){
        return nickName;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }
    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;

    }
    public Date getBirthDate(){
        return birthDate;
    }
    public String getBirthDateString(){
        return SQLUtils.dateToString(birthDate);
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return city;
    }
    public void setState(String state){
        this.state = state;

    }
    public String getState(){
        return state;
    }
    public void setPicturePath(String picturePath){
        this.picturePath = picturePath;
    }
    public String getPicturePath(){
        return picturePath.replaceAll("\\\\", "/");
    }
    public void setAdmin(boolean admin){
        this.admin = admin;
    }
    public boolean isAdmin(){
        return admin;
    }




}
