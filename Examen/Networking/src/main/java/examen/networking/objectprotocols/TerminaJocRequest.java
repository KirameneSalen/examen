package examen.networking.objectprotocols;

import examen.networking.dto.JocDTO;

public class TerminaJocRequest implements Request{
    private JocDTO jocDTO;

    public TerminaJocRequest(JocDTO jocDTO) {
        this.jocDTO = jocDTO;
    }

    public JocDTO getJocDTO() {
        return jocDTO;
    }
}
