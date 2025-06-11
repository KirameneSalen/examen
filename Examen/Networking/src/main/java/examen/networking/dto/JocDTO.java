package examen.networking.dto;

import java.io.Serializable;

public class JocDTO implements Serializable {
    private int id;
    private int idUser;
    private String startedAt;
    private int nrGhiciri;
    private int terminat;
    private String username;
    private String password;

    public JocDTO(int id, int idUser, String startedAt, int nrGhiciri, int terminat, String username, String password) {
        this.id = id;
        this.idUser = idUser;
        this.startedAt = startedAt;
        this.nrGhiciri = nrGhiciri;
        this.terminat = terminat;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public int getNrGhiciri() {
        return nrGhiciri;
    }

    public void setNrGhiciri(int nrGhiciri) {
        this.nrGhiciri = nrGhiciri;
    }

    public int getTerminat() {
        return terminat;
    }

    public void setTerminat(int terminat) {
        this.terminat = terminat;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "JocDTO{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", startedAt='" + startedAt + '\'' +
                ", nrGhiciri=" + nrGhiciri +
                ", terminat=" + terminat +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
