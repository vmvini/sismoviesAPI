package sismovies.entities;

import java.util.Date;

/**
 * Created by marcusviniv on 14/10/2015.
 */
public class RecommendedMovie {

    private int id;
    private Movie movie;
    private User senderUser;
    private User targetUser;
    private boolean viewed;
    private Date date;

    public RecommendedMovie(){
        viewed = false;
    }

    public RecommendedMovie(Movie movie, User sender, User target, Date date){
        this.movie = movie;
        senderUser = sender;
        targetUser = target;
        this.date = date;
        id = 0;
    }

    public String toString(){
        return "id: " + id + " movie: " + movie.getTitle() + " senderUser: " + senderUser.getName() + " targetUser: " + targetUser.getName() + " date: " + date;
     }

    public void setId(int id){
        this.id = id;

    }

    public void setViewed(boolean viewed){
        this.viewed = viewed;
    }

    public boolean isViewed(){
        return viewed;
    }

    public int getId(){
        return id;
    }
    public void setMovie(Movie movie){
        this.movie = movie;
    }
    public Movie getMovie(){
        return movie;
    }
    public void setSenderUser(User sender){
        senderUser = sender;
    }
    public User getSenderUser(){
        return senderUser;
    }
    public void setTargetUser(User target){
        targetUser = target;
    }
    public User getTargetUser(){
        return targetUser;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public Date getDate(){
        return date;
    }



}
