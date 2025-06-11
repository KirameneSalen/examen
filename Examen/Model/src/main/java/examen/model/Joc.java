package examen.model;

import java.util.Objects;

public class Joc implements Entity<Integer>{
    private Integer id;
    private User user;
    private String startedAt;
    private int nrGhiciri;
    private int terminat;

    public Joc(Integer id, User user, String startedAt, int nrGhiciri, int terminat) {
        this.id = id;
        this.user = user;
        this.startedAt = startedAt;
        this.nrGhiciri = nrGhiciri;
        this.terminat = terminat;
    }

    public Joc(int terminat) {
        this.terminat = terminat;
    }

    @Override
    public void setId(Integer integer) {
        this.id = integer;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Joc joc = (Joc) o;
        return nrGhiciri == joc.nrGhiciri && terminat == joc.terminat && Objects.equals(id, joc.id) && Objects.equals(user, joc.user) && Objects.equals(startedAt, joc.startedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, startedAt, nrGhiciri, terminat);
    }

    @Override
    public String toString() {
        return "Joc{" +
                "id=" + id +
                ", user=" + user +
                ", startedAt='" + startedAt + '\'' +
                ", nrGhiciri=" + nrGhiciri +
                ", terminat=" + terminat +
                '}';
    }
}
