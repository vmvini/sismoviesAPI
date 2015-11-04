package sismovies.entities;

import java.util.Date;

/**
 * Created by marcusviniv on 14/10/2015.
 */
public class Group {

    private int id;
    private User owner;
    private String name;
    private String description;
    private Date creationDate;


    public Group(){

    }

    public Group(User owner, String name, String description, Date creationDate){
        id = 0;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
    }

    public String toString(){
        return "id: " + id + " owner: " + owner.getName() + " name: " + name + " description: " + description + " creationDate: " + creationDate;
    }


    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public User getOwner(){
        return owner;
    }
    public void setOwner(User owner){
        this.owner = owner;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String desc){
        description = desc;
    }

    public void setCreationDate(Date creationDate){
        this.creationDate = creationDate;
    }

    public Date getCreationDate(){
        return creationDate;
    }




}
