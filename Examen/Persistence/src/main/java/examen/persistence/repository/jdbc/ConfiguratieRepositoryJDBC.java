package examen.persistence.repository.jdbc;

import examen.model.Configuratie;
import examen.model.User;
import examen.persistence.IConfiguratieRepository;
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

public class ConfiguratieRepositoryJDBC implements IConfiguratieRepository {
    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger(ConfiguratieRepositoryJDBC.class);

    public ConfiguratieRepositoryJDBC(Properties props) {
        logger.info("Initializing ConfiguratieRepositoryJDBC with properties: {}", props);
        dbUtils = new JdbcUtils(props);

    }

    @Override
    public Iterable<Configuratie> getAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Configuratie> configuratieList = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from configuratie;")){
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id");
                    int pozitieLinie = result.getInt("pozitie_linie");
                    int pozitieColoana = result.getInt("pozitie_coloana");
                    String animal = result.getString("animal");
                    String url = result.getString("url");
                    Configuratie configuratie = new Configuratie(id, pozitieLinie, pozitieColoana, animal, url);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return configuratieList;
    }

    @Override
    public Configuratie add(Configuratie entity) throws ValidationException {
        return null;
    }

    @Override
    public Configuratie update(Configuratie entity) throws ValidationException {
        logger.traceEntry("updating configuratie {}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update configuratie set pozitie_linie=?, pozitie_coloana=?, animal=?, url=? where id = ?")){
            preStmt.setInt(1, entity.getPozitieLinie());
            preStmt.setInt(2, entity.getPozitieColoana());
            preStmt.setString(3, entity.getAnimal());
            preStmt.setString(4, entity.getUrl());
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
