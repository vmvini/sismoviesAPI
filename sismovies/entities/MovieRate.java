package sismovies.entities;

import java.util.Date;
import java.util.List;

/**
 * Created by marcusviniv on 14/10/2015.
 */
public class MovieRate {

    private int id;
    private int rate;
    private Movie movie;
    private User sisuser;
    private String comment;

    private Date date;

    public MovieRate(){

    }

    public MovieRate(int rate, Movie movie, User user){
        this.rate = rate;
        this.movie = movie;
        this.sisuser = user;
        id = 0;
    }

    public String toString(){
        return "id: " + id + " rate: " + rate + " movie: " + movie.getTitle() + " user: " + sisuser.getName();
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getComment(){
        return comment;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setRate(int rate){
        this.rate = rate;
    }
    public int getRate(){
        return rate;
    }

    public void setMovie(Movie movie){
        this.movie = movie;
    }
    public Movie getMovie(){
        return movie;
    }
    public void setUser(User user){
        this.sisuser = user;
    }

    public User getUser(){
        return sisuser;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public Date getDate(){
        return date;
    }




}
