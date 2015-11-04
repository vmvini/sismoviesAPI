package sismovies.entities;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by marcusviniv on 14/10/2015.
 */
public enum Genre {

    ACTION(1, "A��o"), ADVENTURE(2, "Aventura"), COMEDY(3, "Com�dia"), CRIME(4, "Crime"), FANTASY(5, "Fantasia"), HISTORICAL(6, "Hist�rico"), HISTORICAL_FICTION(7, "Fic��o Hist�rica"), HORROR(8, "Terror"), MYSTERY(9, "Mist�rio"), ROMANCE(10, "Romance"), POLITICAL(11, "Pol�tico"), SCIENCE_FICTION(12, "Fic��o Cient�fica"), DOCUMENTARY(13, "Document�rio");


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
