package sismovies.entities;

/**
 * Created by marcusviniv on 18/10/2015.
 */
public class MovieGenres {

    private Genre genre;
    private Movie movie;
    private int id;

    public MovieGenres(){

    }
    public MovieGenres(Movie movie, Genre genre){
        this.movie = movie;
        this.genre = genre;
        id = 0;
    }

    public String toString(){
        return "id: " + id + " Movie: " + movie.getTitle() + " genre: " + genre;
    }

    public void setGenre(Genre genre){
        this.genre = genre;
    }

    public Genre getGenre(){
        return genre;
    }

    public void setMovie(Movie movie){
        this.movie = movie;
    }
    public Movie getMovie(){
        return movie;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }




}
