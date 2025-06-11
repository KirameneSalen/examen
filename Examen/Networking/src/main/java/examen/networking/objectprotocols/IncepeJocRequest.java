package examen.networking.objectprotocols;

import examen.networking.dto.UserDTO;

public class IncepeJocRequest implements Request{
    private UserDTO user;

    public UserDTO getUser() {
        return user;
    }

    public IncepeJocRequest(UserDTO user) {
        this.user = user;
    }
}
