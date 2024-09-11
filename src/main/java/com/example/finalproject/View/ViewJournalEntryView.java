package com.example.finalproject.View;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class ViewJournalEntryView extends Pane {
    // Layout
    private VBox vBoxMain;
    private Button goBackBtn;

    // Labels
    private Label countryLabel;
    private Label cityLabel;
    private Label detailsLabel;
    private Label countryValue;
    private Label cityValue;
    private Label detailsValue;

    // Image view
    private ImageView imageView;

    // Getter for goBackBtn
    public Button getGoBackBtn() {
        return goBackBtn;
    }

    public ViewJournalEntryView(String country, String city, String details, String imagePath) {
        // Initialize Labels
        countryLabel = new Label("Country:");
        countryLabel.setStyle("-fx-font-weight: bold;");
        countryLabel.setPrefSize(150, 25);
        countryValue = new Label(country);
        countryValue.setPrefSize(300, 25);

        cityLabel = new Label("City:");
        cityLabel.setStyle("-fx-font-weight: bold;");
        cityLabel.setPrefSize(150, 25);
        cityValue = new Label(city);
        cityValue.setPrefSize(300, 25);

        detailsLabel = new Label("Details:");
        detailsLabel.setStyle("-fx-font-weight: bold;");
        detailsLabel.setPrefSize(150, 25);
        detailsValue = new Label(details);
        detailsValue.setPrefSize(350, 25);

        // create image View to show the image to the user
        if(imagePath != null){
            Image image = new Image(new File(imagePath).toURI().toString());
            imageView = new ImageView(image);
            imageView.setFitWidth(300);
            imageView.setPreserveRatio(true);
        }

        // Initialize Button
        goBackBtn = new Button("Go Back");
        goBackBtn.setPrefSize(100, 30);
        goBackBtn.setStyle("-fx-background-color: #d7e3fc;-fx-border-radius: 3; -fx-border-width: 0.3px; -fx-border-color: #000000;");

        // Layout - Add all elements to VBox container
        if(imagePath != null){
            vBoxMain = new VBox(10, countryLabel, countryValue, cityLabel, cityValue, detailsLabel, detailsValue, imageView, goBackBtn);
        }
        else{
            vBoxMain = new VBox(10, countryLabel, countryValue, cityLabel, cityValue, detailsLabel, detailsValue, goBackBtn);
        }

        vBoxMain.setPadding(new Insets(10, 20, 10, 20)); // Set paddings

        getChildren().add(vBoxMain);
    }
}
