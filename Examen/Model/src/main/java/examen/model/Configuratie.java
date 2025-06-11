package examen.model;

import java.util.Objects;

public class Configuratie implements Entity<Integer> {
    private Integer id;
    private int pozitieLinie, pozitieColoana;
    private String animal, url;

    public Configuratie(Integer id, int pozitieLinie, int pozitieColoana, String animal, String url) {
        this.id = id;
        this.pozitieLinie = pozitieLinie;
        this.pozitieColoana = pozitieColoana;
        this.animal = animal;
        this.url = url;
    }

    public Configuratie() {
    }

    @Override
    public void setId(Integer integer) {
        this.id = integer;
    }

    @Override
    public Integer getId() {
        return this.id;
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

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Configuratie that = (Configuratie) o;
        return pozitieLinie == that.pozitieLinie && pozitieColoana == that.pozitieColoana && Objects.equals(id, that.id) && Objects.equals(animal, that.animal) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pozitieLinie, pozitieColoana, animal, url);
    }

    @Override
    public String toString() {
        return "Configuratie{" +
                "id=" + id +
                ", pozitieLinie=" + pozitieLinie +
                ", pozitieColoana=" + pozitieColoana +
                ", animal='" + animal + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
