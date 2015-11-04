package sismovies.daoclasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class SQLUtils {


    public static String IS_STRING = ".,-*#iSs";

    public static String IS_SEARCH = ".,-*#ISS";

    public static String addStringIdentifier(String in){
        return in + IS_STRING;
    }

    public static boolean isSearchString(String in){
        return in.contains(IS_SEARCH);
    }

    public static String addSearchIdentifier(String in){
        return in + IS_SEARCH;
    }

    public static String removeSearchIdentifier(String in){
        if(in.contains(IS_SEARCH)) {
            String out = in.replace(IS_SEARCH, "");
            return out;
        }
        return in;
    }

    public static String removeStringIdentifier(String in){
        if(in.contains(IS_STRING)) {
            String out = in.replace(IS_STRING, "");
            return out;
        }
        return in;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }

        return true;
    }

    public static boolean isBoolean(String s){
        return (s.equals("false") || s.equals("true"));
    }


    public static boolean isDate(String s){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = s;
        try {

            Date date = formatter.parse(dateInString);
            return true;
        } catch (ParseException e) {
            return false;
        }

    }

    public static boolean stringToBool(String s){
        if(s.equals("true"))
            return true;
        else if(s.equals("false"))
            return false;
        throw new InputMismatchException();
    }

    public static String dateToString(Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static Date stringToDate(String s) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //yy-mm-dd
        String dateInString = s;
        Date date = formatter.parse(dateInString);
        return date;

    }

    public static int getGeneratedId(PreparedStatement p) throws SQLException{
        ResultSet r = p.getGeneratedKeys();
        if(r.next()) {
            System.out.println(r.getInt(1));
            return r.getInt(1);
        }
        return 0;
    }

    public static PreparedStatement setPreparedStatementValues(List<String> values, PreparedStatement p) throws SQLException{
        int i = 1;

        for(String value : values){

            if(isDate(value)){
                p.setString(i,value);

            }
            else if(isInteger(value)){
                p.setInt(i,  Integer.parseInt(value));

            }
            else if(isBoolean(value)){
                p.setBoolean(i, Boolean.parseBoolean(value));
            }
            else if(isSearchString(value)){
                p.setString(i, removeSearchIdentifier("%"+value+"%").toLowerCase());
            }
            else{
                //string

                p.setString(i,  removeStringIdentifier(value) );

            }
            i++;
        }
        return p;
    }


    /**
     * Recebe como parametro um conjunto de chaves e valores que representa (coluna, valor) de alguma tabela do banco de dados e retorna uma string SQL usada para busca de objetos que possuam chave = valor.
     * Se o searchMap estiver vazio, a string sql retornada indica uma listagem por todos os objetos da tabela.
     * @param searchMap
     * @return String SQL com chave1 like '%valor1%' and chave2 like '%valor2%' and chaveN like '%valorN%'
     */
    public static PreparedStatement buildPreparedStatement(HashMap<String, String> searchMap, String initialSQL ,Connection con) throws SQLException{
        String subSql = "";
        int i = 0;

        List<String> values = new ArrayList<>();

        for (String key : searchMap.keySet()) {
            values.add(searchMap.get(key));
            if (i == searchMap.keySet().size()-1) {

                //imprime sem o and
                if(isInteger(searchMap.get(key))){
                    subSql = subSql + " " + key + " = ? ;";
                }
                else if(isDate(searchMap.get(key))){
                    subSql = subSql + " " + key + " = '?' ;";
                }
                else if(isBoolean(searchMap.get(key))){
                    subSql = subSql + " " + key + " = ? ;";
                }
                else if(searchMap.get(key).isEmpty() || searchMap.get(key) == null){
                    subSql = subSql + " " + key + " is null ; ";
                }
                else if( isSearchString( searchMap.get(key) ) ){
                    subSql = subSql + " lower(" + key + ") like ? ";
                }
                else{
                    subSql = subSql + " " + key + " = ?; ";
                }
            }
            else{
                //imprime com o and
                if(isInteger(searchMap.get(key))){
                    subSql = subSql + " " + key + " = ? and ";
                }
                else if(isDate(searchMap.get(key))){
                    subSql = subSql + " " + key + " = '?' and ";
                }
                else if(isBoolean(searchMap.get(key))){
                    subSql = subSql + " " + key + " = ? and ";
                }
                else if(searchMap.get(key).isEmpty() || searchMap.get(key) == null){
                    subSql = subSql + " " + key + " is null and ";
                }
                else if( isSearchString( searchMap.get(key) ) ){
                    subSql = subSql + " lower(" + key + ") like ? and ";
                }
                else{
                    subSql = subSql + " " + key + " = ?  and ";
                }
            }
            i++;
        }
        String finalSQL = initialSQL + subSql;

        if(subSql.isEmpty()) return con.prepareStatement(initialSQL + " 1 = 1;");

        PreparedStatement p = con.prepareStatement(finalSQL);

        return setPreparedStatementValues(values, p);

    }

}
