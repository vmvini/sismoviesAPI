package sismovies.controllers;

import sismovies.daoclasses.GenericDAO;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 14/10/2015.
 */
public class GenericController<T> {

    protected GenericDAO dao;
    protected ModelBehavior saveBehavior;
    protected ModelBehavior removeBehavior;
    protected ModelBehavior listBehavior;


    public GenericController(ModelBehavior saveBehavior, ModelBehavior removeBehavior, ModelBehavior listBehavior, GenericDAO dao){
        this.dao = dao;
        this.saveBehavior = saveBehavior;
        this.removeBehavior = removeBehavior;
        this.listBehavior = listBehavior;
    }

    public T save(T object) throws UnsupportedOperationException, SQLException{
        saveBehavior.behavior();
        return (T)dao.save(object);
    }

    public T update(T object) throws UnsupportedOperationException, SQLException{
        saveBehavior.behavior();
        return (T)dao.update(object);
    }

    public boolean remove(T object) throws UnsupportedOperationException, SQLException{
        removeBehavior.behavior();
        return dao.remove(object);
    }

    public List<T> list()throws UnsupportedOperationException, SQLException{
        listBehavior.behavior();
        return dao.list();
    }

    public List<T> search(HashMap<String, String> searchParams) throws UnsupportedOperationException, SQLException{
        listBehavior.behavior();
        return dao.search(searchParams);
    }


}
