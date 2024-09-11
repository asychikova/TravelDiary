package com.example.finalproject.View;

import com.example.finalproject.Model.JournalEntry;
import com.example.finalproject.Model.TravelDiary;
import com.example.finalproject.Model.User;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TravelDiaryDashboardView extends Pane {
    // private JournalEntry journalEntryModel;
    private TravelDiary travelDiaryModel;
    private User userModel;

    // Layout
    private GridPane gridPane;
    private VBox upperRightVBox;
    private VBox upperLeftVBox;

    // Buttons
    private Button viewBtn;
    private Button updateBtn;
    private Button addBtn;
    private Button deleteBtn;
    private Button logOutBtn;

    // Labels
    private Label journalEntreesLabel;

    // List
    private ListView<String> journalEntriesList;

    // Getter and Setters
    public  Button getViewBtn(){ return viewBtn; }
    public Button getUpdateBtn(){ return updateBtn; }
    public Button getAddBtn(){ return addBtn; }
    public Button getDeleteBtn(){ return deleteBtn; }
    public Button getLogOutBtn(){ return logOutBtn; }
    public ListView<String> getJournalEntriesList(){ return journalEntriesList; }



    public TravelDiaryDashboardView(TravelDiary travelDiary, User user){
        travelDiaryModel = travelDiary; // initialize model
        userModel = user;

        // initialize gridPane - will be main container
        gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(15,15,15,15));

        // Label
        journalEntreesLabel = new Label("Journal Entries");
        journalEntreesLabel.setPrefSize(150,25);

        // Buttons
        viewBtn = new Button("View");
        viewBtn.setPrefSize(100, 25);
        viewBtn.setDisable(true);

        updateBtn = new Button("Update");
        updateBtn.setPrefSize(100, 25);
        updateBtn.setDisable(true);

        addBtn = new Button("Add");
        addBtn.setPrefSize(100, 25);
        addBtn.setStyle("-fx-background-color: #d8e2dc;-fx-border-radius: 3; -fx-border-width: 0.3px; -fx-border-color: #000000;");

        deleteBtn = new Button("Delete");
        deleteBtn.setPrefSize(100, 25);
        deleteBtn.setDisable(true);

        logOutBtn = new Button("Log Out");
        logOutBtn.setPrefSize(100, 25);
        logOutBtn.setStyle("-fx-background-color: #edede9; -fx-border-radius: 3; -fx-border-width: 0.3px; -fx-border-color: #000000;");

        // List
        journalEntriesList = new ListView<>(); // will be used to observe the below list
        journalEntriesList.setPrefWidth(400);
        ArrayList<String> countryCityList = new ArrayList<>(); // will hold a country, city string for the list to be displayed to the user

        // get the string consisting of country, city abd add to countryCityList
        for(JournalEntry entry: travelDiaryModel.getCollection(user)){
            countryCityList.add(entry.getCountry() + ", " + entry.getCity());
        }

        journalEntriesList.setItems(FXCollections.observableArrayList(countryCityList));
        journalEntriesList.setStyle("-fx-background-color: #f8edeb;");


        // Update the ListView to handle selection and enable the View button
        journalEntriesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                viewBtn.setDisable(false);
            } else {
                viewBtn.setDisable(true);
            }
        });

        // VBox - container for buttons
        upperRightVBox = new VBox(10, viewBtn, updateBtn, addBtn, deleteBtn);
        upperRightVBox.setPadding(new Insets(26, 0, 0, 0));
        VBox.setMargin(addBtn, new Insets(173, 0, 0, 0)); // move add and delete btns lower

        // VBox - container for the list
        upperLeftVBox = new VBox(10, journalEntreesLabel, journalEntriesList);

        // Locate all the components in our main container
        gridPane.add(upperLeftVBox, 0,0);      // upper left corner
        gridPane.add(logOutBtn, 0,1);          // lover left corner
        gridPane.add(upperRightVBox, 1,0);     // upper right corner

        // bind flow pane size to the pane size
        gridPane.prefWidthProperty().bind(widthProperty());
        gridPane.prefHeightProperty().bind(heightProperty());

        getChildren().add(gridPane);
    }
}
