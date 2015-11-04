package sismovies.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by marcusviniv on 14/10/2015.
 */
public class Movie {

    private int id;
    private String title;
    private int year;
    private String plot;
    private String picture;
    private String director;

    public Movie(){


    }

    public String toString(){
        return "id: " + id + " title: " + title + " year: " + year + " plot: " + plot;
    }

    public Movie(String title, int year, String plot){
        this.title = title;
        this.year = year;
        this.plot = plot;
        id = 0;


    }

    public void setDirector(String director){
        this.director = director;
    }

    public String getDirector(){
        return director;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
    public void setPicture(String picture){
        this.picture = picture;
    }

    public String getPicture(){
        return picture.replaceAll("\\\\", "/");
    }


    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setYear(int year){
        this.year = year;
    }
    public int getYear(){
        return year;
    }

    public void setPlot(String plot){
        this.plot = plot;
    }
    public String getPlot(){
        return plot;
    }


}
