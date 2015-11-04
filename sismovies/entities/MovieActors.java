package sismovies.entities;

/**
 * Created by marcusviniv on 18/10/2015.
 */
public class MovieActors {

    private String actor;
    private Movie movie;
    private int id;

    public MovieActors(){

    }

    public MovieActors(Movie movie, String actor){
        id = 0;
        this.movie = movie;
        this.actor = actor;
    }

    public String toString(){
        return "id: " + id + " Movie: " + movie.getTitle() + " actor: " + actor;
    }

    public void setMovie(Movie movie){
        this.movie = movie;
    }
    public Movie getMovie(){
        return movie;

    }
    public void setActor(String actor){
        this.actor = actor;
    }
    public String getActor(){
        return actor;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

}
