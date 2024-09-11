package com.example.finalproject.View;

import com.example.finalproject.Controller.TravelDiaryApp;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterView {
    private TravelDiaryApp app;

    public RegisterView(TravelDiaryApp app) {
        this.app = app;
    }

    public void show(Stage primaryStage) {
        primaryStage.setTitle("Register");

        Label fnameLabel = new Label("First Name:");
        TextField fnameField = new TextField();
        HBox fnameBox = new HBox(10, fnameLabel, fnameField);
        fnameBox.setPadding(new Insets(60, 10, 10, 60));

        Label lnameLabel = new Label("Last Name:");
        TextField lnameField = new TextField();
        HBox lnameBox = new HBox(10, lnameLabel, lnameField);
        lnameBox.setPadding(new Insets(0, 10, 10, 60));

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        HBox emailBox = new HBox(10, emailLabel, emailField);
        emailBox.setPadding(new Insets(10, 10, 10, 88));

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        HBox usernameBox = new HBox(10, usernameLabel, usernameField);
        usernameBox.setPadding(new Insets(0, 10, 10, 60));

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        HBox passwordBox = new HBox(10, passwordLabel, passwordField);
        passwordBox.setPadding(new Insets(0, 10, 10, 60));

        Button registerButton = new Button("Register");
        HBox registerB = new HBox(registerButton);
        registerB.setPadding(new Insets(0, 0, 10, 160));

        VBox vbox = new VBox(5, fnameBox, lnameBox, emailBox, usernameBox, passwordBox, registerB);

        registerButton.setOnAction(e -> {
            String fname = fnameField.getText();
            String lname = lnameField.getText();
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                showErrorDialog("Empty Fields", "Please enter all fields.");
            } else {
                if (this.app.register(fname, lname, email, username, password)) {
                    System.out.println("Registration successful!");
                    primaryStage.close();
                } else {
                    showErrorDialog("Registration Failed", "Username already exists.");
                }
            }
        });

        Scene scene = new Scene(vbox, 400, 350);
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
