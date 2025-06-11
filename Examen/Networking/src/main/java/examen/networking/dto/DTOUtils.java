package examen.networking.dto;

import examen.model.Joc;
import examen.model.User;

public class DTOUtils {
    public static User getFromDTO(UserDTO usdto){
        int id = usdto.getId();
        String username=usdto.getUsername();
        String pass=usdto.getPasswd();
        return new User(id, username, pass);

    }
    public static UserDTO getDTO(User user){
        int id=user.getId();
        String username=user.getUsername();
        String pass=user.getPassword();
        return new UserDTO(id, username, pass);
    }
    public static JocDTO getDTO(Joc joc){
        int id=joc.getId();
        int userId=joc.getUser().getId();
        String startedAt =joc.getStartedAt();
        int nrGhiciri=joc.getNrGhiciri();
        int terminat=joc.getTerminat();
        String username=joc.getUser().getUsername();
        String pass=joc.getUser().getPassword();
        return new JocDTO(id, userId, startedAt, nrGhiciri, terminat, username, pass);
    }
    public static Joc getFromDTO(JocDTO jocdto){
        int id=jocdto.getId();
        int userId= jocdto.getIdUser();
        String startedAt =jocdto.getStartedAt();
        int nrGhiciri=jocdto.getNrGhiciri();
        int terminat=jocdto.getTerminat();
        String username=jocdto.getUsername();
        String pass=jocdto.getPassword();
        User user = new User(userId, username, pass);
        return new Joc(id, user, startedAt, nrGhiciri, terminat);
    }
    public static JocDTO[] getDTO(Joc[] jocuri){
        JocDTO[] jocDTOs=new JocDTO[jocuri.length];
        for(int i=0;i<jocuri.length;i++){
            if(jocuri[i]!=null){
                jocDTOs[i]=getDTO(jocuri[i]);
            }
        }
        return jocDTOs;
    }
    public static Joc[] getFromDTO(JocDTO[] jocDTOs){
        Joc[] jocuri =new Joc[jocDTOs.length];
        for(int i = 0; i< jocDTOs.length; i++){
            if(jocDTOs[i]!=null){
                jocuri[i]=getFromDTO(jocDTOs[i]);
            }
        }
        return jocuri;
    }
}
