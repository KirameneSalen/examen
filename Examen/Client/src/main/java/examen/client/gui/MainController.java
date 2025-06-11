package examen.client.gui;

import examen.model.Joc;
import examen.model.User;
import examen.persistence.ValidationException;
import examen.services.IObserver;
import examen.services.IServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, IObserver {
    @FXML
    private Label loggedUserLabel;
//    private ObservableList<Zbor> zborObservableList = FXCollections.observableArrayList();
//
    private IServices server;
    private User user;

    private static final Logger logger = LogManager.getLogger(MainController.class);

    public MainController() {
        logger.debug("Constructor MainController");
    }

    public MainController(IServices server) {
        this.server = server;
        logger.debug("Constructor MainController cu server param");
    }

    public void setServer(IServices server) {
        this.server = server;
//        populateTableView(null, null);
    }

//    private void populateTableView(String destinatie, String dataPlecarii) {
//        zborObservableList.clear();
//        zborObservableList.addAll(this.server.getZboruri(destinatie, dataPlecarii));
//        zborTable.setItems(zborObservableList);
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        aeroportColumn.setCellValueFactory(new PropertyValueFactory<>("aeroport"));
//        destinatieColumn.setCellValueFactory(new PropertyValueFactory<>("destinatie"));
//        dataPlecariiColumn.setCellValueFactory(new PropertyValueFactory<>("dataPlecarii"));
//        oraPlecariiColumn.setCellValueFactory(new PropertyValueFactory<>("oraPlecarii"));
//        nrLocuriDisponibileColumn.setCellValueFactory(new PropertyValueFactory<>("nrLocuriDisponibile"));
        logger.debug("END INIT!!!!!!!!!");
    }

    public void logout() {
        try {
//            server.logout(user, this);
            loggedUserLabel.setText("");
        } catch (ValidationException e) {
            logger.error("Logout error {}", String.valueOf(e));
        }
        logger.debug("logout");
    }

    public void setUser(User crtUser) {
        user = crtUser;
        loggedUserLabel.setText(user.getUsername());
    }

    @Override
    public void jocTerminat(Joc[] jocuri) throws ValidationException {

    }

    public void handleJoc(ActionEvent actionEvent) {
        server.incepeJoc(user);
    }
}
