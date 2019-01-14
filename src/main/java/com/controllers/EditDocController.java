package main.java.com.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import main.java.com.App;
import main.java.com.model.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditDocController implements Initializable {
    private static App app;
    private static User user;
    private Path file;

    @FXML
    private TextArea textArea;
    @FXML
    private TextField txtName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        app = App.getInstance();
        user = app.getUser();
        file = Paths.get("out.txt");
        try {

//            Scanner scan = new Scanner(file).useDelimiter("\\s+");
//            while (scan.hasNextLine()) {
//                textArea.appendText(scan.nextLine() + "\n");
//            }
            String ocrtext = readFile(file);
            textArea.setText(ocrtext);

        } catch (IOException e) {
            System.out.println(e);
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
    protected void edit() {
        try {
            String modified = textArea.getText();
            Files.write(file, modified.getBytes());

            String name = txtName.getText();
            Path dir = Paths.get("scannedTexts");
            Path fileToCreatePath = dir.resolve(name + ".txt");
            Path newFilePath = Files.createFile(fileToCreatePath);
            Files.write(newFilePath, modified.getBytes());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("File Edited");

            // alert.setHeaderText("Results:");
            alert.setContentText("File Successfully Edited And Saved");
            alert.showAndWait();

        } catch(IOException e) {
            System.out.println(e);
        }

        try {
            app.replaceSceneContent("/main/resources/fxml/ReviewDoc.fxml", 700, 550);
        } catch (Exception e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    //helper method for reading files entirely into textarea
    static String readFile(Path f)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(f);
        return new String(encoded);
    }

}
