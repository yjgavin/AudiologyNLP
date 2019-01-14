package main.java.com.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import main.java.com.App;
import main.java.com.model.Report;
import main.java.com.model.User;
import opennlp.tools.namefind.*;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrainModelController implements Initializable {
    private static App app;
    private static User user;
    private File selectedFile;

    @FXML private TextArea textArea;
    @FXML private TextField modelName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        app = App.getInstance();
        user = app.getUser();
    }

    @FXML
    protected void processLoadFile() {
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            System.out.println("File selected: " + selectedFile.getName());
            trainHelper(selectedFile, modelName.getText());
        } else {
            System.out.println("File not selected");
        }
    }

    @FXML
    protected void train() {
        try {
            //save current training file
            String modified = textArea.getText();
            String name = modelName.getText();
            String fName = "en-ner-" + name + ".txt";

            Path dir = Paths.get("trainingFiles");
            Path fileToCreatePath = dir.resolve(fName);
            Path newFilePath = Files.createFile(fileToCreatePath);

            Files.write(newFilePath, modified.getBytes());

            File in = newFilePath.toFile();

            trainHelper(in, name);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //train model using file in
    private void trainHelper(File in, String name) {
        try {
            Charset charset = Charset.forName("UTF-8");
            InputStreamFactory inStream = new MarkableFileInputStreamFactory(in);
            ObjectStream<String> lineStream =
                    new PlainTextByLineStream(inStream, charset);
            ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);

            TokenNameFinderModel model;

            try {
                model = NameFinderME.train("en", name, sampleStream, TrainingParameters.defaultParams(),
                        new TokenNameFinderFactory());
            }
            finally {
                sampleStream.close();
            }

            OutputStream modelOut = null;
            try {
                modelOut = new BufferedOutputStream(new FileOutputStream("./models/en-ner-" + name + ".bin"));
                model.serialize(modelOut);
            } finally {
                if (modelOut != null) {
                    modelOut.close();
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(name + "Model Trained");
                alert.setContentText(name + "Model Successfully Trained And Saved");
                alert.showAndWait();
            }
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
}
