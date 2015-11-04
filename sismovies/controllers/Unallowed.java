package sismovies.controllers;

/**
 * Created by marcusviniv on 20/10/2015.
 */
public class Unallowed implements ModelBehavior {

    private String message;
    public Unallowed(String message){
        this.message = message;
    }

    public void behavior()throws UnsupportedOperationException{
        throw new UnsupportedOperationException(message);
    }
}
