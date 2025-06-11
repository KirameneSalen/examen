package examen.networking.objectprotocols;

import examen.networking.dto.JocDTO;

public class ClasamentResponse implements Response{
    private JocDTO[] jocDTOS;

    public ClasamentResponse(JocDTO[] jocDTOS) {
        this.jocDTOS = jocDTOS;
    }

    public JocDTO[] getJocDTOS() {
        return jocDTOS;
    }
}
