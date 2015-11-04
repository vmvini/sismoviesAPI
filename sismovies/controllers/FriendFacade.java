package sismovies.controllers;

import sismovies.entities.Friendship;
import sismovies.entities.Movie;
import sismovies.entities.RecommendedMovie;
import sismovies.entities.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class FriendFacade {

    private CentralFacade central;

    public FriendFacade(CentralFacade central){
        this.central = central;
    }

    //amizades

    /***
     * retorna as solicitacoes de amizade que o usuario ainda nao visualizou
     * @param meID
     * @return
     * @throws SQLException
     * @throws UnsupportedOperationException
     */
    public List<Friendship> viewFriendInvitations(String meID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("receiver", meID);
        search.put("viewedByReceiver", "false");
        return central.getFriendshipController().search(search);
    }

    /***
     * retorna todas as respostas de amizde q o usuario ainda nao visualizou
     * @param meID
     * @return
     * @throws SQLException
     * @throws UnsupportedOperationException
     */
    public List<Friendship> viewFriendInvitationsResponse(String meID) throws SQLException, UnsupportedOperationException{
        //pegar todas resposta de amizade que logado ainda nao viu
        HashMap<String, String> search = new HashMap<>();
        search.put("sender", meID);
        search.put("viewed", "false");
        search.put("viewedByReceiver", "true");
        return central.getFriendshipController().search(search);
    }

    public void markFriendResponseAsRead(String friendshipID) throws SQLException, UnsupportedOperationException {
        HashMap<String, String> search = new HashMap<>();
        search.put("id", friendshipID);
        List<Friendship> fss = central.getFriendshipController().search(search);
        Friendship fs = fss.get(0);
        fs.setViewed(true);
        central.getFriendshipController().update(fs);
    }

    public List<User> viewAllFriends(User me)throws SQLException, UnsupportedOperationException{
        String meID = String.valueOf(me.getId());
        HashMap<String, String> search = new HashMap<>();
        List<User> friends = new ArrayList<>();

        //pegar amigos que me enviaram solicita�ao
        search.put("receiver", meID);
        search.put("accepted", "true");
        List<Friendship> friendship = central.getFriendshipController().search(search);
        for(Friendship fs : friendship){
            friends.add(fs.getSender());
        }

        //pegar amigos aos quais enviei solicitacao
        search.clear();
        search.put("sender", meID);
        search.put("accepted", "true");
        friendship = central.getFriendshipController().search(search);
        for(Friendship fs: friendship){
            friends.add(fs.getReceiver());
        }

        return friends;
    }

    public void acceptFriendInvitation(String friendshipID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("id", friendshipID );
        List<Friendship> fss = central.getFriendshipController().search(search);
        Friendship fs = fss.get(0);
        fs.setAccepted(true);
        fs.setResponseDate(new Date());
        fs.setViewedByReceiver(true);
        central.getFriendshipController().update(fs);
    }

    public void refuseFriendInvitation(String friendshipID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("id", friendshipID );
        List<Friendship> fss = central.getFriendshipController().search(search);
        Friendship fs = fss.get(0);
        fs.setAccepted(false);
        fs.setResponseDate(new Date());
        fs.setViewedByReceiver(true);
        central.getFriendshipController().update(fs);

    }

    public void addFriend(String otherUserID, User me) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("id", otherUserID);
        List<User> users = central.getUserController().search(search);
        User otherUser = users.get(0);

        Friendship fs = new Friendship();
        fs.setAccepted(false);
        fs.setReceiver(otherUser);
        fs.setSender(me);
        fs.setRequestDate(new Date());

        central.getFriendshipController().save(fs);

    }




    public boolean isFriendOrWantToBe(String otherUserID, String meID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("sender", meID);
        search.put("receiver", otherUserID );
        //search.put("viewedByReceiver", "false");
        List<Friendship> fss = central.getFriendshipController().search(search);

        int i = 0;
        

        search.clear();
        search.put("sender", otherUserID);
        search.put("receiver", meID);
        //search.put("viewedByReceiver", "false");
        fss.addAll(central.getFriendshipController().search(search));

        if(fss.size() > 0 || isFriend(otherUserID, meID) == true )
            return true;
        return false;


    }


    public boolean isFriend(String otherUserID, String meID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("sender", otherUserID);
        search.put("receiver", meID );
        search.put("accepted", "true");
        List<Friendship> fss = central.getFriendshipController().search(search);
        if(fss.size() == 0){
            search.clear();
            search.put("sender", meID);
            search.put("receiver", otherUserID );
            search.put("accepted", "true");
            fss = central.getFriendshipController().search(search);
            if(fss.size() == 0)
                return false;
        }
        return true;
    }

    public void undoFriendship(String otherUserID, User me) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        String meID = String.valueOf(me.getId());
        search.put("sender", otherUserID);
        search.put("receiver", meID );
        List<Friendship> fss = central.getFriendshipController().search(search);
        if(fss.size() == 0){
            search.clear();
            search.put("sender", meID);
            search.put("receiver", otherUserID );
            fss = central.getFriendshipController().search(search);
        }

        Friendship fs = fss.get(0);
        central.getFriendshipController().remove(fs);

    }

    public List<User> searchFriendsByName(String friendName, User me) throws SQLException, UnsupportedOperationException{
        List<User> friends = viewAllFriends(me);
        List<User> match = new ArrayList<>();
        for(User u : friends){
            if( friendName.toLowerCase().trim().equals(u.getName().toLowerCase().trim()) ){
                match.add(u);
            }
            else if( friendName.toLowerCase().trim().contains( u.getName().toLowerCase().trim()) )
                match.add(u);
        }
        return match;
    }

    public List<RecommendedMovie> getRecommendedMovies(String meID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("targetUser", meID);
        List<RecommendedMovie> rms = central.getRecommendedMovieController().search(search);
        return rms;
    }

    /**
     * retorna os filmes recomendados para o usuario meID
     * @param meID
     * @return
     * @throws SQLException
     * @throws UnsupportedOperationException
     */
    public List<Movie> getMoviesRecommended(String meID) throws SQLException, UnsupportedOperationException{
        List<RecommendedMovie> rms = getRecommendedMovies(meID);
        List<Movie> movies = new ArrayList<>();
        for(RecommendedMovie rm : rms){
            movies.add(rm.getMovie());
        }
        return movies;
    }

    /**
     * Retorna os filmes que um usuario recomendou
     * @param meID
     * @return
     * @throws SQLException
     * @throws UnsupportedOperationException
     */
    public List<Movie> getMoviesThatUserRecommend(String meID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("senderUser", meID);
        List<RecommendedMovie> rms = central.getRecommendedMovieController().search(search);
        List<Movie> movies = new ArrayList<>();
        for(RecommendedMovie rm : rms){
            if(!containsMovie(movies, rm.getMovie()))
                movies.add(rm.getMovie());
        }
        return movies;
    }

    private boolean containsMovie(List<Movie> movies, Movie movie){

        for(Movie m : movies){
            if(m.getId() == movie.getId()){
                return true;
            }
        }
        return false;
    }

    public List<RecommendedMovie> getNotViewedRecommendedMovies(String meID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("targetUser", meID);
        search.put("viewed", "false");
        List<RecommendedMovie> rms = central.getRecommendedMovieController().search(search);
        return rms;
    }

    public void recommendMovieToFriends(String[] friendsIDs, User me, String movieID) throws SQLException, UnsupportedOperationException{
        String meID = String.valueOf(me.getId());
        for(String fID : friendsIDs){
            recommedMovieToFriend(movieID, meID, fID);
        }
    }

    public void markRecommendedMovieAsRead(String rmID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("id", rmID);
        List<RecommendedMovie> rms = central.getRecommendedMovieController().search(search);
        RecommendedMovie rm = rms.get(0);
        rm.setViewed(true);
        central.getRecommendedMovieController().update(rm);
    }






    public void recommedMovieToFriend(String movieID, String meID, String friendID) throws SQLException, UnsupportedOperationException{
        //criar objeto movie
        HashMap<String, String> search = new HashMap<>();
        search.put("id", movieID);
        List<Movie> movies = central.getMovieController().search(search);
        Movie movie = movies.get(0);

        //criar objeto usuario
        search.clear();
        search.put("id", meID);
        List<User> users = central.getUserController().search(search);
        User me = users.get(0);

        //criar objeto friend user
        search.clear();
        search.put("id", friendID);
        users = central.getUserController().search(search);
        User friend = users.get(0);

        RecommendedMovie rm = new RecommendedMovie();
        rm.setTargetUser(friend);
        rm.setSenderUser(me);
        rm.setMovie(movie);
        rm.setDate(new Date());

        central.getRecommendedMovieController().save(rm);


    }


}
