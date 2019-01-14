package main.java.com.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import main.java.com.App;
import main.java.com.model.User;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewDocController implements Initializable {
    private static App app;
    private static User user;
    private File selectedFile;

    @FXML
    private TextField fileName;
    @Override public void initialize(URL location, ResourceBundle resources) {
        app = App.getInstance();
        user = app.getUser();
    }
    
    @FXML
    protected void processLoadFile() {
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            System.out.println("File selected: " + selectedFile.getName());
            fileName.setText(selectedFile.getName());
        } else {
            System.out.println("File not selected");
        }
    }

    @FXML
    protected void processHome() {
        try {
            app.replaceSceneContent("/main/resources/fxml/Main.fxml", 700, 550);
        } catch (Exception e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    protected void processAddPatient() {
        try {
            app.replaceSceneContent("/main/resources/fxml/NewDoc.fxml", 700, 550);
        } catch (Exception e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    protected void processScan() {
        if (selectedFile != null) {
            try {

                Process p = Runtime.getRuntime().exec("tesseract " + selectedFile.getName() + " out");
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        p.getInputStream()));
                String readline;
                while ((readline = reader.readLine()) != null) {
                    System.out.println(readline);
                }
                System.out.println("out.txt created");
            } catch (IOException e) {
                System.out.println(e);
            }

            try {
                app.replaceSceneContent("/main/resources/fxml/EditDoc.fxml", 700, 550);
            } catch (Exception e) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No file chosen!");

            // alert.setHeaderText("Results:");
            alert.setContentText("Please select a file before scan");

            alert.showAndWait();
        }
    }
}