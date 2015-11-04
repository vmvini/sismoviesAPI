package sismovies.entities;

import java.util.Date;

/**
 * Created by marcusviniv on 14/10/2015.
 */
public class GroupUser {

    private int id;
    private User sisuser;
    private Group sisgroup;
    private Date requestDate;
    private Date responseDate;
    private boolean accepted;
    private boolean viewed;
    private boolean viewedByOwner;


    public GroupUser(){
        accepted = false;
        viewed = false;
        viewedByOwner = false;
    }

    public GroupUser(User user, Group group, Date requestDate){
        this.sisuser = user;
        this.sisgroup = group;
        this.requestDate = requestDate;
        accepted = false;
        viewed = false;
    }

    public String toString(){
        return "user: " + sisuser.getName() + " group: " + sisgroup.getName() + " requestDate: " + requestDate + " isAccepted: " + accepted + " responseDate: " + responseDate;
    }

    public boolean isViewedByOwner(){
        return viewedByOwner;
    }
    public void setViewedByOwner(boolean viewedByOwner){
        this.viewedByOwner = viewedByOwner;
    }
    public boolean isViewed(){
        return viewed;
    }
    public void setViewed(boolean viewed){
        this.viewed = viewed;
    }

    public int getId(){
        return id;

    }
    public void setId(int id){
        this.id = id;
    }

    public User getUser(){
        return sisuser;
    }

    public void setUser(User user){
        this.sisuser = user;
    }

    public Group getGroup(){
        return sisgroup;
    }
    public void setGroup(Group group){
        this.sisgroup = group;
    }

    public void setRequestDate(Date requestDate){
        this.requestDate = requestDate;
    }
    public Date getRequestDate(){
        return requestDate;
    }
    public void setResponseDate(Date responseDate){
        this.responseDate = responseDate;
    }

    public Date getResponseDate(){
        return responseDate;
    }

    public boolean isAccepted(){
        return accepted;
    }
    public void setAccepted(boolean accepted){
        this.accepted = accepted;
    }



}
