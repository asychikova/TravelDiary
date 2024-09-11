package com.example.finalproject.View;

import com.example.finalproject.Controller.TravelDiaryApp;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class LoginView {
    private TravelDiaryApp app;

    public LoginView(TravelDiaryApp app) {
        this.app = app;
    }

    public void show(Stage primaryStage) {
        primaryStage.setTitle("Login");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        HBox usernameBox = new HBox(15.0, usernameLabel, usernameField);
        usernameBox.setPadding(new Insets(60, 10, 10, 60));

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        HBox passwordBox = new HBox(15.0, passwordLabel, passwordField);
        passwordBox.setPadding(new Insets(0, 10, 10, 60));

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        HBox buttonsBox = new HBox(15.0, loginButton, registerButton);
        buttonsBox.setPadding(new Insets(0, 10, 10, 130));

        VBox vbox = new VBox(5.0, usernameBox, passwordBox, buttonsBox);
        loginButton.setOnAction((e) -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                showErrorDialog("Empty Fields", "Please enter both username and password.");
            } else {
                if (this.app.login(username, password)) {
//                    Boolean exist = this.app.getUserModel().login(username, password);

                    System.out.println("Login successful.");
                    app.handleSuccessfulLogin(app.getUser(username));
                    primaryStage.close(); // close login window
                } else {
                    showErrorDialog("Login Failed", "Invalid username or password.");
                }
            }
        });
        registerButton.setOnAction((e) -> {
            RegisterView registerView = new RegisterView(this.app);
            Stage registerStage = new Stage();
            registerView.show(registerStage);
        });

        Scene scene = new Scene(vbox, 400.0, 250.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("ERROR");
        alert.setContentText(message);
        alert.showAndWait();
    }
}