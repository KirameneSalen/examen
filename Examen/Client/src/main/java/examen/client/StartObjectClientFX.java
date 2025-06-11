package examen.client;

import examen.client.gui.LoginController;
import examen.client.gui.MainController;
import examen.networking.objectprotocols.ServerObjectProxy;
import examen.services.IServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class StartObjectClientFX extends Application {

    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    private static Logger logger = LogManager.getLogger(StartObjectClientFX.class);

    public void start(Stage primaryStage) throws Exception {
        logger.debug("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartObjectClientFX.class.getResourceAsStream("/client.properties"));
            logger.info("Client properties set {} ", clientProps);
            // clientProps.list(System.out);
        } catch (IOException e) {
            logger.error("Cannot find client.properties " + e);
            logger.debug("Looking into folder {}", (new File(".")).getAbsolutePath());
            return;
        }
        String serverIP = clientProps.getProperty("mpp.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("mpp.server.port"));
            logger.debug("Server port: {}", serverPort);
        } catch (NumberFormatException ex) {
            logger.error("Wrong port number " + ex.getMessage());
            logger.debug("Using default port: " + defaultChatPort);
        }
        logger.info("Using server IP " + serverIP);
        logger.info("Using server port " + serverPort);


        IServices server = new ServerObjectProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("LoginW.fxml"));
        Parent root=loader.load();
        LoginController ctrl =
                loader.<LoginController>getController();
        ctrl.setServer(server);

        FXMLLoader mloader = new FXMLLoader(
                getClass().getClassLoader().getResource("MainController.fxml"));
        Parent mroot=mloader.load();
        MainController mainController =
                mloader.<MainController>getController();
        mainController.setServer(server);

        ctrl.setMainController(mainController);
        ctrl.setParent(mroot);

        primaryStage.setTitle("MPP companie zbor");
        primaryStage.setScene(new Scene(root, 300, 130));
        primaryStage.show();
    }
}
