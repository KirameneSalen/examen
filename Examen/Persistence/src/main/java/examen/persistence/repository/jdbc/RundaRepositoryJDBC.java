package examen.persistence.repository.jdbc;

import examen.model.Joc;
import examen.model.Runda;
import examen.model.User;
import examen.persistence.IRundaRepository;
import examen.persistence.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RundaRepositoryJDBC implements IRundaRepository {
    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger(RundaRepositoryJDBC.class);

    public RundaRepositoryJDBC(Properties props) {
        logger.info("Initializing RundaRepositoryJDBC with properties: {}", props);
        dbUtils = new JdbcUtils(props);

    }

    @Override
    public Iterable<Runda> getAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Runda> runde = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from runda inner join joc on runda.id_joc = joc.id inner join user on user.id = joc.id_user")){
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id");
                    int idJoc = result.getInt("id_joc");
                    String startedAt = result.getString("started_at");
                    int nrGhiciri = result.getInt("nr_ghiciri");
                    int pozitieLinie = result.getInt("pozitie_linie");
                    int pozitieColoana = result.getInt("pozitie_coloana");
                    int idUser = result.getInt("id_user");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    int terminat = result.getInt("terminat");
                    User user = new User(idUser, username, password);
                    Joc joc = new Joc(idJoc, user, startedAt, nrGhiciri, terminat);
                    runde.add(new Runda(id, joc, pozitieLinie, pozitieColoana));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return runde;
    }

    @Override
    public Runda add(Runda entity) throws ValidationException {
        logger.traceEntry("saving runda {}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from runda where id = ?")){
            preStmt.setInt(1, entity.getId() != null ? entity.getId() : 0);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    logger.traceExit("Did not save {}, runda already exists", entity);
                    return entity;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        try(PreparedStatement preStmt = con.prepareStatement("insert into runda (id_joc, pozitie_linie, pozitie_coloana) values (?,?,?)")){
            preStmt.setInt(1, entity.getJoc().getId());
            preStmt.setInt(2, entity.getPozitieLinie());
            preStmt.setInt(3, entity.getPozitieColoana());
            int result = preStmt.executeUpdate();
            ResultSet generatedKeys = preStmt.getGeneratedKeys();
            if(generatedKeys.next()){
                entity.setId(generatedKeys.getInt(1));
            }
            logger.trace("Saved {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
        return entity;
    }

    @Override
    public Runda update(Runda entity) throws ValidationException {
        return null;
    }
}
