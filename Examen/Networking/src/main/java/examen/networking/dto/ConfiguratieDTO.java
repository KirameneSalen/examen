package examen.networking.dto;

import java.io.Serializable;

public class ConfiguratieDTO implements Serializable {
    private int id;
    private int pozitieLinie;
    private int pozitieColoana;
    private String animal;
    private String url;

    public ConfiguratieDTO(int id, int pozitieLinie, int pozitieColoana, String animal, String url) {
        this.id = id;
        this.pozitieLinie = pozitieLinie;
        this.pozitieColoana = pozitieColoana;
        this.animal = animal;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public String toString() {
        return "ConfiguratieDTO{" +
                "id=" + id +
                ", pozitieLinie=" + pozitieLinie +
                ", pozitieColoana=" + pozitieColoana +
                ", animal='" + animal + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
