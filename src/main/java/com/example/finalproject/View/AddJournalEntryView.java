package com.example.finalproject.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class AddJournalEntryView extends Pane {
    // Layout
    private VBox vBoxMain;
    private VBox vBoxBtn;
    private HBox hBoxBtn;

    // Label
    private Label countryLabel;
    private Label cityLabel;
    private Label detailsLabel;
    private Label imageSelectedText;

    // Text Fields
    private TextField countryTextField;
    private TextField cityTextField;
    private TextField detailsTextField;

    // Button
    private Button imageUploadBtn;
    private Button saveBtn;
    private Button cancleBtn;


    // Getters and Setters
    public TextField getCountryTextField(){ return countryTextField; }
    public TextField getCityTextField(){ return cityTextField; }
    public TextField getDetailsTextField(){ return detailsTextField; }
    public Button getSaveBtn(){ return saveBtn; }
    public Button getImageUploadBtn(){ return imageUploadBtn; }
    public Label getImageSelectedText(){ return imageSelectedText; }
    public Button getCancleBtn(){ return cancleBtn; }

    public AddJournalEntryView(){

        // Label
        countryLabel = new Label("Country");
        countryLabel.setPrefSize(150,25);

        cityLabel = new Label("City");
        cityLabel.setPrefSize(150, 25);

        detailsLabel = new Label("Details");
        detailsLabel.setPrefSize(150,25);

        imageSelectedText = new Label("");

        // Text Fields
        countryTextField = new TextField();
        countryTextField.setPrefSize(300, 25);

        cityTextField = new TextField();
        cityTextField.setPrefSize(300,25);

        detailsTextField = new TextField();
        detailsTextField.setPrefSize(350, 75);
        detailsTextField.setAlignment(Pos.TOP_LEFT); // align text to the top left

        // Button
        imageUploadBtn = new Button("Upload Image");
        imageUploadBtn.setPrefSize(100, 30);
        imageUploadBtn.setStyle("-fx-background-color: #d7e3fc;-fx-border-radius: 3; -fx-border-width: 0.3px; -fx-border-color: #000000;");

        saveBtn = new Button("Save");
        saveBtn.setPrefSize(100, 30);
        saveBtn.setStyle("-fx-background-color: #d8e2dc;-fx-border-radius: 3; -fx-border-width: 0.3px; -fx-border-color: #000000;");

        cancleBtn = new Button("Cancel");
        cancleBtn.setPrefSize(100, 30);
        cancleBtn.setStyle("-fx-background-color: #d7e3fc;-fx-border-radius: 3; -fx-border-width: 0.3px; -fx-border-color: #000000;");

        // Layout - add all elements to VBox container
        hBoxBtn = new HBox(20,saveBtn, cancleBtn);

        vBoxBtn = new VBox(20,imageUploadBtn, imageSelectedText, hBoxBtn );
        vBoxBtn.setPadding(new Insets(10,0,10,0));

        vBoxMain = new VBox(10, countryLabel, countryTextField, cityLabel, cityTextField, detailsLabel, detailsTextField, vBoxBtn);
        vBoxMain.setPadding(new Insets(10, 20, 10, 20)); // set paddings

        getChildren().add(vBoxMain);
    }
}
