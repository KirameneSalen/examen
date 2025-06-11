package examen.networking.objectprotocols;

import examen.networking.dto.UserDTO;

public class LoggedInResponse implements Response {
    private UserDTO user;

    public LoggedInResponse(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "LoggedInResponse{" +
                "user=" + user +
                '}';
    }
}
