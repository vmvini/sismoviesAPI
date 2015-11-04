package sismovies.entities;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class MiddleRate {

    private int mrate;
    private Movie movie;

    public MiddleRate(){}

    public MiddleRate(Movie movie, int mrate){
        this.movie = movie;
        this.mrate = mrate;
    }

    public void setMrate(int mrate){
        this.mrate = mrate;
    }

    public int getMrate(){
        return mrate;
    }

    public void setMovie(Movie movie){
        this.movie = movie;
    }
    public Movie getMovie(){
        return movie;
    }




}
