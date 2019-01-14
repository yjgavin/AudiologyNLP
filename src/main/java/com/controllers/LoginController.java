package main.java.com.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.App;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private TextField userId;
    @FXML private PasswordField password;
    @FXML private Label errorMessage;

    @FXML protected void processLogin() {
        if(!App.getInstance().userLogin(userId.getText(), password.getText())){
            errorMessage.setText("Username/password combination is invalid.");
        }
    }

    @Override public void initialize(URL url, ResourceBundle rb) {
        userId.setPromptText("UserID");
        password.setPromptText("Password");
    }
}