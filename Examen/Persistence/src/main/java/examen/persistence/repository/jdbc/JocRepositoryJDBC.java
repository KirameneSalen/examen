package examen.persistence.repository.jdbc;

import examen.model.Joc;
import examen.model.User;
import examen.persistence.IJocRepository;
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

public class JocRepositoryJDBC implements IJocRepository {
    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger(JocRepositoryJDBC.class);

    public JocRepositoryJDBC(Properties props) {
        logger.info("Initializing JocRepositoryJDBC with properties: {}", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Iterable<Joc> getAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Joc> jocuri = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from joc inner join user on joc.id_user = user.id")){
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id");
                    String startAt = result.getString("started_at");
                    int nrGhiciri = result.getInt("nr_ghiciri");
                    int terminat = result.getInt("terminat");
                    int idUser = result.getInt("id_user");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    User user = new User(idUser, username, password);
                    jocuri.add(new Joc(id, user, startAt, nrGhiciri, terminat));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return jocuri;
    }

    @Override
    public Joc add(Joc entity) throws ValidationException {
        logger.traceEntry("saving joc {}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from joc where id = ?")){
            preStmt.setInt(1, entity.getId() != null ? entity.getId() : 0);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    logger.traceExit("Did not save {}, joc already exists", entity);
                    return entity;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        try(PreparedStatement preStmt = con.prepareStatement("insert into joc (id_user, started_at, nr_ghiciri, terminat) values (?,?,?,?)")){
            preStmt.setInt(1, entity.getUser().getId());
            preStmt.setString(2, entity.getStartedAt());
            preStmt.setInt(3, entity.getNrGhiciri());
            preStmt.setInt(4, entity.getTerminat());
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
    public Joc update(Joc entity) throws ValidationException {
        logger.traceEntry("updating joc {}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update user set id_user=?, started_at=?, nr_ghiciri=?, terminat=? where id = ?")){
            preStmt.setInt(1, entity.getUser().getId());
            preStmt.setString(2, entity.getStartedAt());
            preStmt.setInt(3, entity.getNrGhiciri());
            preStmt.setInt(4, entity.getTerminat());
            preStmt.setInt(5, entity.getId());
            int result = preStmt.executeUpdate();
            logger.trace("Updated {} instances", result);
            return entity;
        } catch (SQLException e) {
            logger.error(e);
        }
        logger.traceExit();
        return null;
    }
}
