package sismovies.entities;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by marcusviniv on 14/10/2015.
 */
public enum Genre {

    ACTION(1, "Ação"), ADVENTURE(2, "Aventura"), COMEDY(3, "Comédia"), CRIME(4, "Crime"), FANTASY(5, "Fantasia"), HISTORICAL(6, "Histórico"), HISTORICAL_FICTION(7, "Ficção Histórica"), HORROR(8, "Terror"), MYSTERY(9, "Mistério"), ROMANCE(10, "Romance"), POLITICAL(11, "Político"), SCIENCE_FICTION(12, "Ficção Científica"), DOCUMENTARY(13, "Documentário");


    private int key;
    private String description;

    Genre(int key, String description){
        this.key = key;
        this.description = description;
    }

    public String getDescription(){
        return description;

    }
    public int getKey(){
        return key;
    }

    public static Genre getEnumByKey(int key){

        switch(key){
            case 1:
                return ACTION;
            case 2:
                return ADVENTURE;
            case 3:
                return COMEDY;
            case 4:
                return CRIME;
            case 5:
                return FANTASY;
            case 6:
                return HISTORICAL;
            case 7:
                return HISTORICAL_FICTION;
            case 8:
                return HORROR;
            case 9:
                return MYSTERY;
            case 10:
                return ROMANCE;
            case 11:
                return POLITICAL;
            case 12:
                return SCIENCE_FICTION;
            case 13:
                return DOCUMENTARY;
            default:
                return null;

        }
    }

}
