package main.java.com;

import java.awt.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.com.model.User;
import main.java.com.security.Auth;

public class App extends Application {
    private Stage stage;
    private User loggedUser;

    private static App instance;
    private static FileChooser fc = new FileChooser();

    private Desktop desktop = Desktop.getDesktop();

    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            gotoLogin();
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User getUser() {
        return loggedUser;
    }

    public boolean userLogin(String userId, String password){
        if (Auth.validate(userId, password)) {
            loggedUser = User.of(userId);
            gotoMain();
            return true;
        } else {
            return false;
        }
    }

    public void userLogout(){
        loggedUser = null;
        gotoLogin();
    }

    private void gotoMain() {
        try {
            replaceSceneContent("/main/resources/fxml/Main.fxml", 700, 550);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void gotoLogin() {
        try {
            replaceSceneContent("/main/resources/fxml/Login.fxml", 700, 550);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /* TODO: Return something else instead of a String
     * Also do something else in the file != null
     */
    public String loadFile() {
        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter(
                        "PDF pages", "*.pdf");

        fc.getExtensionFilters().add(fileExtensions);
        File file = fc.showOpenDialog(stage);

        if (file != null) {
            return file.toString();
        }
        return "";
    }

    public Parent replaceSceneContent(String fxml, int width, int height) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxml));
        Parent page = loader.load();

        Scene scene = new Scene(page, width, height);
        stage.setScene(scene);

//        Scene scene = stage.getScene();
//        if (scene == null) {
//            System.out.println("" + width + " " + height);
//            scene = new Scene(page, width, height);
//            stage.setScene(scene);
//        } else {
//            stage.getScene().setRoot(page);
//        }

        stage.sizeToScene();
        return page;
    }
}