package sismovies.entities;

import java.util.Date;

/**
 * Created by marcusviniv on 14/10/2015.
 */
public class TopicComment {

    private int id;
    private Topic topic;
    private User sisuser;
    private Date date;
    private String comment;

    public TopicComment(){}

    public TopicComment(Topic topic, User user, String comment, Date date){
        this.topic = topic;
        this.sisuser = user;
        this.comment = comment;
        this.date = date;
        id = 0;
    }

    public String toString(){
        return "id: " + id + " topic: " + topic + " user: " + sisuser.getName() + " date: " + date + " comment: " + comment;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setTopic(Topic topic){
        this.topic = topic;
    }
    public Topic getTopic(){
        return topic;
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
    public void setComment(String comment){
        this.comment = comment;
    }
    public String getComment(){
        return comment;
    }


}
