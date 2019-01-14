package main.java.com.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.java.com.App;
import main.java.com.model.User;
import main.java.com.model.Report;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;

import javax.xml.soap.Text;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.util.*;

public class ReviewDocController implements Initializable {
    private static App app;
    private static User user;
    private Path file;
    private Report report;
    private String[][] document;

    //history subcategory
    private List<String> hearingScreen = Arrays.asList("newborn hearing screen", "hearing screen",
            "initial hearing screen", "subsquent screen", "re-screen", "follow up screen", "nbhs");
    private List<String> hearingLoss = Arrays.asList("normal hearing range", "normal or abnormal hearing",
            "reduced auditory sensitivity", "hearing loss", "undetermined", "indeterminate hearing loss",
            "possible hearing loss", "loss in the right or left ear");
    private List<String> earlyIntervention = Arrays.asList("receive early intervention", "auditory verbal therapy",
            "physical therapy", "occupational therapy", "pt", "ot", "motor therapy", "speech therapy", "speech service",
            "language therapy", "speech language therapy", "speech language pathology service",
            "speech language pathology therapy", "communication therapy");
    private List<String> riskFactors = Arrays.asList("vestibular aqueduct", "pfeiffer syndrome", "charge syndrome",
            "down", "trisomy 21", "22q11.2 deletion syndrome");
    private List<String> hearingAid = Arrays.asList("wears", "uses", "using", "fit with", "hearing aid", "hearing aids",
            "baha");
    private List<List<String>> histories = Arrays.asList(hearingScreen, hearingLoss, earlyIntervention,
            riskFactors, hearingAid);

    //test subcategory
    private List<String> cochlearFunction = Arrays.asList("oae", "otoacoustic emission", "toae",
            "transient otoacoustic emission dpoae", "distortion product otoacoustic emission");
    private List<String> evokedPotential = Arrays.asList("automated auditory brainstem response", "aabr", "screening abr",
            "screen by abr", "auditory brainstem response screen", "auditory brainstem response",
            "brainstem auditory response", "brianstem auditory evoked potential", "abr", "aep", "assr",
            "auditory steady state response");
    private List<String> middleEarFunction = Arrays.asList("acoustic impedance", "tympanometry", "immittance", "immitance",
            "typmanogram", "226hz probe tone", "1000hz probe tone", "high frequency probe tone",
            "low frequency probe tone", "acoustic reflex");
    private List<String> behavourialAudiometry = Arrays.asList("pure tone audiometry", "pure tone air or bone conduction",
            "visual reinforcement audiometry", "vra", "sat", "speech awareness threshold",
            "srt speech reception threshold", "play audiometry", "conditioned play audiometry", "cpa");
    private List<List<String>> tests = Arrays.asList(cochlearFunction, evokedPotential, middleEarFunction,
            behavourialAudiometry);

    //report subcategory
    private List<String> generalResult = Arrays.asList("adequate for communication", "not adequate for communication",
            "acquisition of speech", "acquisition of language skills", "normal in at least one ear",
            "normal hearing range", "normal hearing", "abnormal hearing", "normal auditory function",
            "reduced auditory sensitivity", "can not rule out hearing loss", "possible hearing loss", "hearing loss",
            "undetermined/ indeterminate hearing loss", "type of hearing loss could not be determined",
            "unknown whether hearing loss is conductive, mix or sensorineural", "possible hearing loss",
            "possibility of hearing loss");
    private List<String> degree = Arrays.asList("minimal", "slight", "mild", "moderate", "moderately severe",
            "severe", "profound", "un/indetermined");
    private List<String> ear = Arrays.asList("left", "right", "as", "ad", "au", "bilateral", "bilaterally",
            "unilateral", "unilaterally", "both ears", "in one ear", "unrestricted hearing of contralateral ear");
    private List<String> type = Arrays.asList("conductive hearing loss", "sensorineural hearing loss",
            "senori-neural hearing loss", "mixed hearing loss", "sensory hearing loss", "neural hearing loss",
            "auditory neuropathy", "type of hearing loss could not be determined",
            "unknown whether hearing loss is conductive", "mix hearing loss", "sensorineural hearing loss",
            "central hearing loss", "auditory neuropathy");
    private List<List<String>> reports = Arrays.asList(generalResult, degree, ear, type);

    //recommendation: no subcategory
    private List<String> recommendation = Arrays.asList("return if there is concern", "return if hearing concerns arise",
            "back for", "retest", "complete", "repeat", "further testing", "audiologic re-evaluation", "hearing re-evaluation",
            "hearing re-evaluated (and when)", "monitor auditory status", "audiological monitoring", "hearing monitor",
            "sedated abr", "abr", "assr", "hearing rescreen", "hearing screen", "oae medical follow up", "follow up with",
            "ent", "ear nose throat", "otolaryngology", "speech language pathologist", "hearing aid", "amplification",
            "hearing aid consultation", "hearing aid fitting", "hearing aid evaluation", "trial with hearing amplification",
            "trial with hearing aid", "assistive listening devices", "baha", "cochlear implant", "medical clearance",
            "early intervention", "continue to participate in early intervention services",
            "children with special healthcare needs, head start", "speech langage therpay", "speech therapy",
            "auditory verbal therapy");


    @FXML private TextArea textArea;
    @FXML private TextField fileName;
    @FXML private TextField trainingModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        app = App.getInstance();
        user = app.getUser();
        file = Paths.get("out.txt");
        String f = null;
        report = new Report();

        try {
            //read the input text as a single string
            FileReader in = new FileReader("./out.txt");
            BufferedReader br = new BufferedReader(in);

            String line = br.readLine();
            f = line;
            while (line != null) {
                f += line;
                line = br.readLine();
            }

            //load sentence detector model
            InputStream modelIn = new FileInputStream("./models/da-sent.bin");
            SentenceModel model = new SentenceModel(modelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);

            //divide input file into sentences
            String sentences[] = sentenceDetector.sentDetect(f);

            //close sentence detector model
            if (modelIn != null) {
                try {
                    modelIn.close();
                }
                catch (IOException e) {
                }
            }

            // load tokenizer model
            InputStream inputStreamTokenizer = new
                    FileInputStream("./models/en-token.bin");
            TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer);
            TokenizerME tokenizer = new TokenizerME(tokenModel);

            //Tokenizing input file in to a string array
            document = new String[sentences.length][];
            for (int i = 0; i < sentences.length; i++) {
                document[i] = tokenizer.tokenize(sentences[i]);
            }

            //close tokenizer model
            if (inputStreamTokenizer != null) {
                try {
                    inputStreamTokenizer.close();
                }
                catch (IOException e) {
                }
            }

            //process general patient information
            String[] modelNames = new String[]{"person", "date", "location", "organization"};
            for (String modelName : modelNames) {
                //load model
                InputStream inputStreamNameFinder = new FileInputStream("./models/en"
                        + "-ner-" + modelName + ".bin");
                TokenNameFinderModel name_model = new TokenNameFinderModel(inputStreamNameFinder);
                NameFinderME nameFinder = new NameFinderME(name_model);

                List<String> results = new ArrayList<>();
                for (String[] sentence : document) {
                    Span nameSpans[] = nameFinder.find(sentence);
                    // save results
                    for (Span s : nameSpans) {
                        results.add(sentence[nameSpans[0].getStart()]);
                    }
                }
                nameFinder.clearAdaptiveData();
                if (!results.isEmpty()) {
                    if (modelName.equals("date")) {
                        report.info.add(results.get(0));
                        if (results.size() == 1) {
                            report.info.add(null);
                        } else {
                            report.info.add(results.get(1));
                        }
                    } else {
                        report.info.add(results.get(0));
                    }
                } else {
                    report.info.add(null);
                }

                //close name finder model
                if (inputStreamNameFinder != null) {
                    try {
                        inputStreamNameFinder.close();
                    }
                    catch (IOException e) {
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //string matching on medical terms
        String lowerCased = f.toLowerCase();

        //general info: sex
        if (lowerCased.indexOf("male") != -1) {
            report.info.add("male");
        } else if (lowerCased.indexOf("female") != -1) {
            report.info.add("female");
        } else {
            report.info.add(null);
        }

        //histories
        for (List<String> history : histories) {
            List<String> ls = new ArrayList<>();
            for (String s : history) {
                if (lowerCased.indexOf(s) != -1) {
                    ls.add(s);
                }
            }
            report.history.add(ls);
        }

        //tests
        for (List<String> test : tests) {
            List<String> ls = new ArrayList<>();
            for (String s : test) {
                if (lowerCased.indexOf(s) != -1) {
                    ls.add(s);
                }
            }
            report.test.add(ls);
        }

        //reports
        for (List<String> r : reports) {
            List<String> ls = new ArrayList<>();
            for (String s : r) {
                if (lowerCased.indexOf(s) != -1) {
                    ls.add(s);
                }
            }
            report.report.add(ls);
        }

        //recommendations
        for (String s : recommendation) {
            if (lowerCased.indexOf(s) != -1) {
                report.recommendation.add(s);
            }
        }

        String result = report.toString();
        textArea.setText(result);
    }

    @FXML
    protected void edit() {
        try {
            String modified = textArea.getText();
            String name = fileName.getText();

            Path dir = Paths.get("keyFeatures");
//            Path fileToCreatePath = dir.resolve(name + ".txt");
//            Path newFilePath = Files.createFile(fileToCreatePath);
//
//            Files.write(newFilePath, modified.getBytes());
            PrintWriter pw = new PrintWriter(new File("keyFeatures/" + name + ".csv"));
            pw.write(modified);
            pw.close();
            System.out.println("done");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("File Edited");
            alert.setContentText("File Successfully Edited And Saved");
            alert.showAndWait();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    protected void selfTrain() {
        try {
            String modelName = trainingModel.getText();

            InputStream inputStreamNameFinder = new FileInputStream("./models/en"
                    + "-ner-" + modelName + ".bin");
            TokenNameFinderModel name_model = new TokenNameFinderModel(inputStreamNameFinder);
            NameFinderME nameFinder = new NameFinderME(name_model);

            List<String> results = new ArrayList<>();
            for (String[] sentence : document) {
                Span nameSpans[] = nameFinder.find(sentence);
                // save results
                for (Span s : nameSpans) {
                    results.add(sentence[nameSpans[0].getStart()]);
                }
            }

            nameFinder.clearAdaptiveData();

            StringBuilder sb = new StringBuilder();
            Iterator<String> it = results.iterator();
            while (it.hasNext()) {
                sb.append(it.next() + ", ");
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                sb.deleteCharAt(sb.length() - 1);
                sb.append("\n");
            }

            textArea.setText(sb.toString());

            //close name finder model
            if (inputStreamNameFinder != null) {
                try {
                    inputStreamNameFinder.close();
                }
                catch (IOException e) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
