package examen.networking.dto;

import java.io.Serializable;

public class RundaDTO implements Serializable {
    private int id;
    private int id_joc;
    private int pozitieLinie;
    private int pozitieColoana;

    public RundaDTO(int id, int id_joc, int pozitieLinie, int pozitieColoana) {
        this.id = id;
        this.id_joc = id_joc;
        this.pozitieLinie = pozitieLinie;
        this.pozitieColoana = pozitieColoana;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_joc() {
        return id_joc;
    }

    public void setId_joc(int id_joc) {
        this.id_joc = id_joc;
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
    public String toString() {
        return "RundaDTO{" +
                "id=" + id +
                ", id_joc=" + id_joc +
                ", pozitieLinie=" + pozitieLinie +
                ", pozitieColoana=" + pozitieColoana +
                '}';
    }
}
