package sismovies.daoclasses;

import sismovies.entities.MiddleRate;
import sismovies.entities.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class MiddleRateDAO implements GenericDAO<MiddleRate> {

    private Connection con;

    public MiddleRateDAO(Connection con){
        this.con = con;
    }

    private List<MiddleRate> buildMiddleRateList(ResultSet r)throws SQLException{
        List<MiddleRate> mrs = new ArrayList<>();
        MiddleRate mr;
        while(r.next()){
            mr = new MiddleRate();

            mr.setMrate(r.getInt("mrate"));

            //finding movie and setting it
            HashMap<String, String> search = new HashMap<>();
            search.put("id", String.valueOf( r.getInt("movie") ) );
            List<Movie> movies = new MovieDAO(con).search(search);
            mr.setMovie(movies.get(0));

            mrs.add(mr);
        }
        return mrs;
    }

    public MiddleRate save(MiddleRate object) throws SQLException, UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }


    public MiddleRate update(MiddleRate object) throws SQLException, UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    public boolean remove(MiddleRate object) throws SQLException{
        throw new UnsupportedOperationException();
    }

    public List<MiddleRate> list() throws SQLException{
        String sql = "select * from middlerate order by mrate desc";
        Statement s = con.createStatement();
        ResultSet r = s.executeQuery(sql);
        return buildMiddleRateList(r);
    }

    public List<MiddleRate> search(HashMap<String, String> searchParams) throws SQLException{
        String sql = "select * from middlerate where ";
        PreparedStatement p = SQLUtils.buildPreparedStatement(searchParams, sql, con);
        ResultSet r = p.executeQuery();
        return buildMiddleRateList(r);
    }

}
