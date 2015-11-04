package sismovies.daoclasses;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 14/10/2015.
 */
public interface GenericDAO<T> {

    T save(T object) throws SQLException;

    T update(T object) throws SQLException;

    boolean remove(T object) throws SQLException;

    List<T> list() throws SQLException;

    List<T> search(HashMap<String, String> searchParams) throws SQLException;

}
