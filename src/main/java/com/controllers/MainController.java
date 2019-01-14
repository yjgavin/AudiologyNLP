package main.java.com.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.java.com.App;
import main.java.com.model.User;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainController implements Initializable {
    private static App app;
    private static User user;

    @Override public void initialize(URL location, ResourceBundle resources) {
        app = App.getInstance();
        user = app.getUser();
    }

    @FXML protected void processLogout() {
        app.userLogout();
    }

    @FXML protected void processAddReport() {
        try {
            app.replaceSceneContent("/main/resources/fxml/NewDoc.fxml", 700, 550);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    protected void processTrainModel() {
        try {
            app.replaceSceneContent("/main/resources/fxml/TrainModel.fxml", 700, 550);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}