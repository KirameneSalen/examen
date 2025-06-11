package examen.networking.objectprotocols;

import examen.networking.dto.JocDTO;

public class IncepeJocResponse implements Response{
    JocDTO joc;

    public JocDTO getJoc() {
        return joc;
    }

    public IncepeJocResponse(JocDTO joc) {
        this.joc = joc;
    }
}
