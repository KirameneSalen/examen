package examen.model;

import java.util.Objects;

public class Runda implements Entity<Integer>{
    private Integer id;
    private Joc joc;
    private int pozitieLinie, pozitieColoana;

    public Runda(Integer id, Joc joc, int pozitieLinie, int pozitieColoana) {
        this.id = id;
        this.joc = joc;
        this.pozitieLinie = pozitieLinie;
        this.pozitieColoana = pozitieColoana;
    }

    public Runda() {
    }

    @Override
    public void setId(Integer integer) {
        this.id = integer;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public Joc getJoc() {
        return joc;
    }

    public void setJoc(Joc joc) {
        this.joc = joc;
    }

    public int getPozitieLinie() {
        return pozitieLinie;
    }

    public void setPozitieLinie(int pozitieLinie) {
        this.pozitieLinie = pozitieLinie;
    }

    public int getPozitieColoana() {
        return pozitieColoana;
    }

    public void setPozitieColoana(int pozitieColoana) {
        this.pozitieColoana = pozitieColoana;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Runda runda = (Runda) o;
        return pozitieLinie == runda.pozitieLinie && pozitieColoana == runda.pozitieColoana && Objects.equals(id, runda.id) && Objects.equals(joc, runda.joc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, joc, pozitieLinie, pozitieColoana);
    }

    @Override
    public String toString() {
        return "Runda{" +
                "id=" + id +
                ", joc=" + joc +
                ", pozitieLinie=" + pozitieLinie +
                ", pozitieColoana=" + pozitieColoana +
                '}';
    }
}
