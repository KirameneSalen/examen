package examen.networking.objectprotocols;

import examen.networking.dto.JocDTO;

public class GhicestePozitieRequest implements Request{
    private JocDTO jocDTO;
    private int pozitieLinie;
    private int pozitieColoana;

    public GhicestePozitieRequest(JocDTO jocDTO, int pozitieLinie, int pozitieColoana) {
        this.jocDTO = jocDTO;
        this.pozitieLinie = pozitieLinie;
        this.pozitieColoana = pozitieColoana;
    }

    public JocDTO getJocDTO() {
        return jocDTO;
    }

    public int getPozitieLinie() {
        return pozitieLinie;
    }

    public int getPozitieColoana() {
        return pozitieColoana;
    }
}
