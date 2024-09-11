package com.example.finalproject.View;

import com.example.finalproject.Model.JournalEntry;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.File;


public class UpdateJournalEntryView extends Pane {
    // Layout
    private VBox vBoxMain;
    private HBox hBoxBtn;
    private HBox hBoxImage;

    // Label
    private Label countryLabel;
    private Label cityLabel;
    private Label detailsLabel;

    // Text Fields
    private TextField countryTextField;
    private TextField cityTextField;
    private TextField detailsTextField;

    // Button
//    private Button imageUploadBtn;
    private Button updateBtn;
    private Button cancelBtn;
    private Button removeImageBtn;
    private Button addImageBtn;

    // Image view
    private ImageView imageView;
    private Image image; // used to create an image based on image path

    // Getters and Setters
    public TextField getCountryTextField(){ return countryTextField; }
    public TextField getCityTextField(){ return cityTextField; }
    public TextField getDetailsTextField(){ return detailsTextField; }
    public Button getUpdateBtn(){ return updateBtn; }
    public Button getCancelBtn(){ return cancelBtn; }
    public Button getRemoveImageBtn(){ return removeImageBtn; }
    public Button getAddImageBtn(){ return addImageBtn; }

    public void setImageView(String imagePath){
        if(imagePath != null){
            Image image = new Image(new File(imagePath).toURI().toString());
            imageView.setImage(image);
        }
        else{
            imageView.setImage(null);
        }
    }

    public UpdateJournalEntryView(JournalEntry journalEntry){

        // Label
        countryLabel = new Label("Country");
        countryLabel.setPrefSize(150,25);

        cityLabel = new Label("City");
        cityLabel.setPrefSize(150, 25);

        detailsLabel = new Label("Details");
        detailsLabel.setPrefSize(150,25);

        // Text Fields
        countryTextField = new TextField(journalEntry.getCountry());
        countryTextField.setPrefSize(300, 25);

        cityTextField = new TextField(journalEntry.getCity());
        cityTextField.setPrefSize(300,25);

        detailsTextField = new TextField(journalEntry.getDetails());
        detailsTextField.setPrefSize(350, 75);
        detailsTextField.setAlignment(Pos.TOP_LEFT); // align text to the top left

        // Image View
        String imagePath = journalEntry.getImagePath(); // get image path

        if(imagePath != null){
            image = new Image(new File(imagePath).toURI().toString());
            imageView = new ImageView(image);
            imageView.setFitWidth(300);
            imageView.setPreserveRatio(true);
        }

        updateBtn = new Button("Update");
        updateBtn.setPrefSize(100, 30);
        updateBtn.setStyle("-fx-background-color: #d8e2dc;-fx-border-radius: 3; -fx-border-width: 0.3px; -fx-border-color: #000000;");

        cancelBtn = new Button("Cancel");
        cancelBtn .setPrefSize(100, 30);
        cancelBtn .setStyle("-fx-background-color: #d7e3fc;-fx-border-radius: 3; -fx-border-width: 0.3px; -fx-border-color: #000000;");

        removeImageBtn = new Button("X");
        removeImageBtn.setPrefSize(30, 30);
        removeImageBtn.setStyle("-fx-background-color: #fec5bb;-fx-border-radius: 3; -fx-border-width: 0.3px; -fx-border-color: #000000;");

        addImageBtn = new Button("+");
        addImageBtn.setPrefSize(30, 30);
        addImageBtn.setStyle("-fx-background-color: #d8e2dc;-fx-border-radius: 3; -fx-border-width: 0.3px; -fx-border-color: #000000;");

        // if image was not added - disable delete btn - else disable add image btn
        if(journalEntry.getSelectedImage() == null){
            removeImageBtn.setDisable(true);
        }
        else{
            addImageBtn.setDisable(true);
        }

        // Layout - add all elements to VBox container
        hBoxBtn = new HBox(20, updateBtn, cancelBtn);
        hBoxBtn.setPadding(new Insets(10,0,10,0));

        if(imagePath != null){
            hBoxImage = new HBox(10, imageView, addImageBtn, removeImageBtn);
        }
        else{
            hBoxImage = new HBox(10, addImageBtn, removeImageBtn);
        }

        vBoxMain = new VBox(10, countryLabel, countryTextField, cityLabel, cityTextField, detailsLabel, detailsTextField, hBoxImage, hBoxBtn);
        vBoxMain.setPadding(new Insets(10, 20, 10, 20)); // set paddings

        getChildren().add(vBoxMain);
    }
}