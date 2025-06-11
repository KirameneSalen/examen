import examen.networking.utils.AbstractServer;
import examen.networking.utils.ObjectConcurrentServer;
import examen.networking.utils.ServerException;
import examen.persistence.IConfiguratieRepository;
import examen.persistence.IJocRepository;
import examen.persistence.IRundaRepository;
import examen.persistence.IUserRepository;
import examen.persistence.repository.jdbc.ConfiguratieRepositoryJDBC;
import examen.persistence.repository.jdbc.JocRepositoryJDBC;
import examen.persistence.repository.jdbc.RundaRepositoryJDBC;
import examen.persistence.repository.jdbc.UserRepositoryJDBC;
import examen.server.ServerImpl;
import examen.services.IServices;

import java.io.IOException;
import java.util.Properties;

public class StartObjectServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartObjectServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find companieserver.properties "+e);
            return;
        }
        IUserRepository userRepo=new UserRepositoryJDBC(serverProps);
        IJocRepository jocRepository=new JocRepositoryJDBC(serverProps);
        IRundaRepository rundaRepository = new RundaRepositoryJDBC(serverProps);
        IConfiguratieRepository configuratieRepository = new ConfiguratieRepositoryJDBC(serverProps);
        IServices chatServerImpl=new ServerImpl(userRepo, jocRepository, configuratieRepository, rundaRepository);
        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("companie.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new ObjectConcurrentServer(chatServerPort, chatServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
    }
}
