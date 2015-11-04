package sismovies.entities;

/**
 * Created by marcusviniv on 14/10/2015.
 */
public class Topic {

    private int id;
    private Movie movie;
    private Group sisgroup;

    public Topic(){}

    public Topic(Movie movie, Group group){
        this.movie = movie;
        this.sisgroup = group;
        id = 0;
    }

    public String toString(){
        return "id: " + id + " movie: " + movie.getTitle() + " group: " + sisgroup.getName();
    }

    public void setId(int id){
        this.id = id;
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

    public void setGroup(Group group){
        this.sisgroup = group;
    }
    public Group getGroup(){
        return sisgroup;
    }



}
