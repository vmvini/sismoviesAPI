package sismovies.entities;

import java.util.Date;

/**
 * Created by marcusviniv on 14/10/2015.
 */
public class Friendship {

    private int id;
    private User sender;
    private User receiver;
    private boolean accepted;
    private Date requestDate;
    private Date responseDate;
    private boolean viewed;
    private boolean viewedByReceiver;

    public Friendship(){
        accepted = false;
        viewed = false;
        viewedByReceiver = false;
    }

    public Friendship(User sender, User receiver, Date requestDate){
        this.sender = sender;
        this.receiver = receiver;
        this.requestDate = requestDate;
        accepted = false;
        id = 0;
    }
    public void setViewed(boolean viewed){
        this.viewed = viewed;
    }
    public boolean isViewed(){
        return viewed;
    }
    public boolean isViewedByReceiver(){
        return viewedByReceiver;
    }
    public void setViewedByReceiver(boolean viewedByReceiver){
        this.viewedByReceiver = viewedByReceiver;
    }


    public String toString(){
        return "id: " + id + " sender: " + sender + " receiver: " + receiver + " accepted: " + accepted + " requestDate: " + requestDate + " response date: " + responseDate;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }


    public User getSender(){
        return sender;
    }
    public void setSender(User sender){
        this.sender = sender;
    }

    public User getReceiver(){
        return receiver;

    }

    public void setReceiver(User receiver){
        this.receiver = receiver;
    }

    public boolean isAccepted(){
        return accepted;
    }

    public void setAccepted(boolean accepted){
        this.accepted = accepted;
    }

    public Date getRequestDate(){
        return requestDate;
    }

    public void setRequestDate(Date requestDate){
        this.requestDate = requestDate;
    }

    public Date getResponseDate(){
        return responseDate;
    }

    public void setResponseDate(Date responseDate){
        this.responseDate = responseDate;
    }

}
