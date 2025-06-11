package examen.server;

import examen.model.Configuratie;
import examen.model.Joc;
import examen.model.Runda;
import examen.model.User;
import examen.persistence.*;
import examen.services.IObserver;
import examen.services.IServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl implements IServices {
    private IUserRepository userRepository;
    private IJocRepository jocRepository;
    private IConfiguratieRepository configuratieRepository;
    private IRundaRepository rundaRepository;
    private Map<Integer, IObserver> loggedClients;

    private static final Logger logger = LogManager.getLogger(ServerImpl.class);

    public ServerImpl(IUserRepository userRepository, IJocRepository jocRepository, IConfiguratieRepository configuratieRepository, IRundaRepository rundaRepository) {
        this.userRepository = userRepository;
        this.jocRepository = jocRepository;
        this.configuratieRepository = configuratieRepository;
        this.rundaRepository = rundaRepository;
        loggedClients=new ConcurrentHashMap<>();
    }

    @Override
    public synchronized User login(User user, IObserver client) throws ValidationException {
        logger.traceEntry();
        User user1 = userRepository.findByUsername(user.getUsername());
        if (user1 == null) {
            throw new ValidationException("Username not found");
        }
        else if (!user.getPassword().equals(user1.getPassword())) {
            throw new ValidationException("Wrong password");
        } else {
            logger.trace("Logged in");
            loggedClients.put(user1.getId(), client);
            logger.trace("Added client to logged clients");
        }
        logger.traceExit();
        return user1;
    }

    @Override
    public Joc incepeJoc(User user) throws ValidationException {
        return jocRepository.add(new Joc(0, user, LocalDateTime.now().toString(), 3, 0));
    }

    @Override
    public String ghicestePozitie(Joc joc, int pozitieLinie, int pozitieColoana) throws ValidationException {
        Configuratie configuratie = configuratieRepository.getAll().iterator().next();
        Runda runda = new Runda(0, joc, pozitieLinie, pozitieColoana);
        runda = rundaRepository.add(runda);
        jocRepository.update(new Joc(joc.getId(), joc.getUser(), joc.getStartedAt(), joc.getNrGhiciri()-1, joc.getTerminat()));
        if(pozitieLinie == configuratie.getPozitieLinie() && pozitieColoana == configuratie.getPozitieColoana()){
            //gata joc
        } else {
            int indicatieLinie = configuratie.getPozitieLinie() - pozitieLinie;
            int indicatieColoana = configuratie.getPozitieColoana() - pozitieColoana;
            String indicatie = "";
            if(indicatieColoana > 0){
                indicatie += "Nord";
            } else if (indicatieColoana < 0){
                indicatie += "Sud";
            }
            if(indicatieLinie > 0){
                indicatie+="Vest";
            }
            else if(indicatieLinie < 0) {
                indicatie+="Est";
            }
            return indicatie;
        }
        return null;
    }

    @Override
    public void terminaJoc(Joc joc, IObserver client) throws ValidationException {

    }

    @Override
    public Joc[] clasament() throws ValidationException {
        ArrayList<Joc> jocuri = new ArrayList<>();
        jocRepository.getAll().iterator().forEachRemaining(jocuri::add);
        Joc[] joc = new Joc[jocuri.size()];
        joc = jocuri.toArray(joc);
        return joc;
    }

    @Override
    public Joc[] jocuriPierdute(User user) throws ValidationException {
        return new Joc[0];
    }

    @Override
    public Configuratie schimbaConfiguratie(int id, int pozitieLinie, int pozitieColoana, String animal, String url) throws ValidationException {
        return null;
    }
}
