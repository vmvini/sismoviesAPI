package sismovies.controllers;

import sismovies.entities.Group;
import sismovies.entities.GroupUser;
import sismovies.entities.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcusviniv on 19/10/2015.
 */
public class GroupFacade {

    private CentralFacade central;

    public GroupFacade(CentralFacade central){
        this.central = central;
    }

    public boolean isOwner(String meID, String groupID)throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("owner", meID);
        search.put("id", groupID);
        List<Group> groups = central.getGroupController().search(search);
        if(groups.size() == 0)
            return false;
        return true;
    }

    public List<Group> getGroupsCreatedByUser(String userID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("owner", userID);
        List<Group> groups = central.getGroupController().search(search);
        return groups;

    }

    public boolean isMember(String meID, String groupID)throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("sisuser", meID);
        search.put("sisgroup", groupID);
        search.put("accepted", "true");
        List<GroupUser> gus = central.getGroupUserController().search(search);
        if(gus.size() == 0)
            return false;
        return true;
    }

    //grupos
    public void signOnGroup(User loggedUser, String groupID)throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("id", groupID);
        List<Group> groups = central.getGroupController().search(search);
        Group group = groups.get(0);

        GroupUser gu = new GroupUser();
        gu.setUser(loggedUser);
        gu.setGroup(group);
        gu.setRequestDate(new Date());
        gu.setAccepted(false);

        central.getGroupUserController().save(gu);
    }

    public void getOutOfGroup(String groupID, String meID)throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("sisuser", meID);
        search.put("sisgroup", groupID);
        List<GroupUser> gus = central.getGroupUserController().search(search);
        GroupUser gu = gus.get(0);
        central.getGroupUserController().remove(gu);
    }

    public List<Group> showGroups(String name) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("name", name);
        return central.getGroupController().search(search);
    }

    public List<Group> showUserGroups(String meID)throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("sisuser",meID);
        search.put("accepted", "true");
        List<GroupUser> gus = central.getGroupUserController().search(search);
        List<Group> groups = new ArrayList<>();
        for(GroupUser gu : gus ){
            groups.add(gu.getGroup());
        }
        return groups;
    }


    public void acceptGroupInvitation(String groupUserID)throws SQLException, UnsupportedOperationException{
        //usuario dono do grupo aceita um pedido de entrada feito por outro usuario
        HashMap<String, String> search = new HashMap<>();
        search.put("id", groupUserID);
        List<GroupUser> gus = central.getGroupUserController().search(search);
        GroupUser gu = gus.get(0);

        gu.setAccepted(true);
        gu.setResponseDate(new Date());
        gu.setViewedByOwner(true);
        central.getGroupUserController().update(gu);
    }

    public void refuseGroupInvitation(String groupUserID) throws SQLException, UnsupportedOperationException{
        //usuario dono do grupo recusa um pedido de entrada feito por outro usuario
        HashMap<String, String> search = new HashMap<>();
        search.put("id", groupUserID);
        List<GroupUser> gus = central.getGroupUserController().search(search);
        GroupUser gu = gus.get(0);

        gu.setAccepted(false);
        gu.setResponseDate(new Date());
        gu.setViewedByOwner(true);

        central.getGroupUserController().update(gu);
    }

    public List<GroupUser> getGroupSolicitations(String groupID) throws SQLException, UnsupportedOperationException{
        //dono do grupo usa esse metodo para saber quem deseja entrar nos seus grupos
        HashMap<String, String> search = new HashMap<>();
        search.put("sisgroup", groupID);
        search.put("accepted", "false");
        search.put("viewedByOwner", "false");
        List<GroupUser> gus = central.getGroupUserController().search(search);
        return gus;

    }

    public List<GroupUser> viewGroupsInvitationResponse(String meID) throws SQLException, UnsupportedOperationException{
        //usuario usa esse metodo para saber quais grupos o aceitaram ou recusaram
        HashMap<String, String> search = new HashMap<>();
        search.put("sisuser", meID );
        search.put("viewedByOwner", "true"); //dono do grupo ja marcou como aceito ou recusado
        search.put("viewed", "false"); //usuario ainda nao viu que foi aceito ou recusado
        return central.getGroupUserController().search(search);
    }

    public void markGroupResponseAsRead(String  groupUserID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("id", groupUserID);
        List<GroupUser> gus = central.getGroupUserController().search(search);
        GroupUser gu = gus.get(0);
        gu.setViewed(true);
        central.getGroupUserController().update(gu);

    }

    public Group createGroup(String name, String description, String ownerID) throws SQLException, UnsupportedOperationException{
        Group g = new Group();
        HashMap<String, String> search = new HashMap<>();
        search.put("id", ownerID);
        List<User> users = central.getUserController().search(search);

        g.setOwner(users.get(0));
        g.setName(name);
        g.setDescription(description);
        g.setCreationDate(new Date());
        g = (Group)central.getGroupController().save(g);
        return g;
    }

    public void removeGroup(String groupID) throws SQLException, UnsupportedOperationException{
        HashMap<String, String> search = new HashMap<>();
        search.put("id", groupID);
        List<Group> gs = central.getGroupController().search(search);
        central.getGroupController().remove(gs.get(0));
    }


}
