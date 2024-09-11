package com.example.finalproject.Controller;

import com.example.finalproject.Model.TravelDiary;
import com.example.finalproject.Model.User;
import com.example.finalproject.Model.JournalEntry;
import com.example.finalproject.View.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TravelDiaryApp extends Application {
    private TravelDiary travelDiaryModel;
    private TravelDiaryDashboardView travelDiaryDashboardView;
    private AddJournalEntryView addJournalEntryView;
    private UpdateJournalEntryView updateJournalEntryView;

    private Map<String, User> users = new HashMap<>();

    private User userModel;
    private Stage primaryStage;

    private String imagePath;

    public String getImagePath(){ return imagePath; }
    public void setImagePath(String imagePath){ this.imagePath = imagePath;}


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;


        // initialize travel model
        travelDiaryModel = new TravelDiary();

        // read all the data from file and populate travel diary model
        loadDataFromFile("src/main/java/com/example/finalproject/data.txt");

        // Initialize login view
        LoginView loginView = new LoginView(this);
        loginView.show(new Stage());

        // write the objects to the file
        primaryStage.setOnCloseRequest(event -> saveDataToFile("src/main/java/com/example/finalproject/data.txt") );

    }

    // login handling
    public void handleSuccessfulLogin(User user) {
        // view updating with the logged-in user
        userModel = user;

        // Initialize TravelDiary if it's null
        if (travelDiaryModel == null) {
            travelDiaryModel = new TravelDiary();
        }

        // Ensure the user is added to the TravelDiary model
        if (!travelDiaryModel.userExists(userModel)) {
            travelDiaryModel.addUser(userModel);
        }

        travelDiaryDashboardView = new TravelDiaryDashboardView(travelDiaryModel, userModel);
        showDashboard();
    }

    // SHOW Dashboard
    public void showDashboard() {
        // create new instance of View - otherwise exception is thrown
        travelDiaryDashboardView = new TravelDiaryDashboardView(travelDiaryModel, userModel);

        primaryStage.setTitle("Travel Diary Application");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(travelDiaryDashboardView, 650, 400));
        primaryStage.show();

        travelDiaryDashboardView.getJournalEntriesList().setOnMouseClicked(event -> handleJournalEntreeSelection());
        travelDiaryDashboardView.getLogOutBtn().setOnMouseClicked(event -> {
            primaryStage.close();
            LoginView loginView = new LoginView(this);
            loginView.show(new Stage());
        });

        travelDiaryDashboardView.getAddBtn().setOnMouseClicked(event -> showAddJournalEntry());
        travelDiaryDashboardView.getViewBtn().setOnMouseClicked(event -> handleViewJournalEntryBtn());
        travelDiaryDashboardView.getUpdateBtn().setOnMouseClicked(event -> showUpdateJournalEntry());
        travelDiaryDashboardView.getDeleteBtn().setOnMouseClicked(event -> handleDeleteJournalEntryBtn());
    }

    // SHOW View JE
    public void showViewJournalEntry(String country, String city, String details, String imagePath) {
        System.out.println(imagePath);
        ViewJournalEntryView viewJournalEntryView = new ViewJournalEntryView(country, city, details, imagePath);

        // action for the Go Back button
        viewJournalEntryView.getGoBackBtn().setOnAction(event -> showDashboard());

        primaryStage.setTitle("View Journal Entry");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(viewJournalEntryView, 650, 530));
        primaryStage.show();
    }

    // SHOW Update JE
    public void showUpdateJournalEntry() {
        int selectedIndex = travelDiaryDashboardView.getJournalEntriesList().getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            // initialize update view with the selected journal entry by the user
            updateJournalEntryView = new UpdateJournalEntryView(travelDiaryModel.getCollection(userModel).get(selectedIndex));

            primaryStage.setTitle("Update Journal Entry");
            primaryStage.setResizable(true);
            primaryStage.setScene(new Scene(updateJournalEntryView, 650, 570));
            primaryStage.show();

            // event handlers
            updateJournalEntryView.getCancelBtn().setOnMouseClicked(event -> showDashboard());
            updateJournalEntryView.getUpdateBtn().setOnMouseClicked(event -> handleUpdateBtnForUpdateView(selectedIndex, userModel));
            updateJournalEntryView.getRemoveImageBtn().setOnMouseClicked(event -> handleRemoveImageForUpdateView());
            updateJournalEntryView.getAddImageBtn().setOnMouseClicked(event -> handleAddImageBtnForUpdateView());
        }
    }

    // SHOW Add JE
    public void showAddJournalEntry(){
        addJournalEntryView = new AddJournalEntryView();

        primaryStage.setTitle("Add Journal Entry");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(addJournalEntryView, 650, 430));
        primaryStage.show();

        // event handler for image upload
        addJournalEntryView.getImageUploadBtn().setOnMouseClicked(event -> {
            handleUploadImageBtnForAddView();
            addJournalEntryView.getImageSelectedText().setText("The image has been uploaded!");
        });

        // get all the journal entry details when SAVE button is clicked
        addJournalEntryView.getSaveBtn().setOnMouseClicked(event -> handleSaveJournalEntryBtn());
        addJournalEntryView.getCancleBtn().setOnMouseClicked(event -> showDashboard());
    }

    public void handleViewJournalEntryBtn() {
        int selectedIndex = travelDiaryDashboardView.getJournalEntriesList().getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            JournalEntry selectedEntry = travelDiaryModel.getCollection(userModel).get(selectedIndex);
            showViewJournalEntry(selectedEntry.getCountry(), selectedEntry.getCity(), selectedEntry.getDetails(), selectedEntry.getImagePath());
        }
    }

    public void handleAddImageBtnForUpdateView(){
        handleUploadImageBtnForAddView(); // get image and set image for the selected JE

        updateJournalEntryView.setImageView(getImagePath());;

        updateJournalEntryView.getRemoveImageBtn().setDisable(false);
        updateJournalEntryView.getAddImageBtn().setDisable(true);
    }

    public void handleRemoveImageForUpdateView(){
        Boolean removeImage = showAlertImageRemoval(); // prompt alert to user to confirm if they want to delete image

        if(removeImage){
            updateJournalEntryView.setImageView(null);
            setImagePath(null); // set general image path

            // disable remove btn and enable add image btn
            updateJournalEntryView.getRemoveImageBtn().setDisable(true);
            updateJournalEntryView.getAddImageBtn().setDisable(false);
        }
    }

    public void handleUpdateBtnForUpdateView(int selectedIndex, User user){
        // Get the updated values
        String country = updateJournalEntryView.getCountryTextField().getText();
        String city = updateJournalEntryView.getCityTextField().getText();
        String details = updateJournalEntryView.getDetailsTextField().getText();

        // Update the journal entry
        System.out.println(imagePath);
        travelDiaryModel.updateJournalEntry(user, selectedIndex, country, city, details, imagePath);
        // redirect to main
        showDashboard();
    }

    public void handleDeleteJournalEntryBtn() {
        int selectedIndex = travelDiaryDashboardView.getJournalEntriesList().getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            // Remove the selected journal entry
            travelDiaryModel.removeJournalEntry(userModel, selectedIndex);

            // Update the ListView
            ArrayList<String> countryCityList = new ArrayList<>();
            for (JournalEntry entry : travelDiaryModel.getCollection(userModel)) {
                countryCityList.add(entry.getCountry() + ", " + entry.getCity());
            }

            // Clear existing items in the ListView
            travelDiaryDashboardView.getJournalEntriesList().getItems().clear();

            // Add updated items to the ListView
            for (String entry : countryCityList) {
                travelDiaryDashboardView.getJournalEntriesList().getItems().add(entry);
            }
        }
    }

    // Handle upload image Button
    public void handleUploadImageBtnForAddView(){
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter allImagesFilter = new FileChooser.ExtensionFilter("Image Files", "*.png");

        chooser.getExtensionFilters().addAll(allImagesFilter); // add extensions

        // set the path from where the user can upload the images
        File directoryImages = new File("src/main/java/com/example/finalproject/");
        chooser.setInitialDirectory(directoryImages); // set the path for chooser

        chooser.setTitle("Upload Image");
        File f = chooser.showOpenDialog(primaryStage); // allow the user to choose an image

        if (f != null) {
            String path = directoryImages + "/" + f.getName(); // get the photo path
            setImagePath(path); // set general image path
        }
    }

    public void handleSaveJournalEntryBtn(){
        // get country
        String country = addJournalEntryView.getCountryTextField().getText();
        // get city
        String city = addJournalEntryView.getCityTextField().getText();
        // get details
        String details = addJournalEntryView.getDetailsTextField().getText();
        // get Image path
        String imagePath = getImagePath();

        travelDiaryModel.addJournalEntry(country, city, details, imagePath, userModel);

        setImagePath(null); // clear general image path

        showDashboard();
    }

    public void handleJournalEntreeSelection(){
        travelDiaryDashboardView.getViewBtn().setDisable(false);
        travelDiaryDashboardView.getViewBtn().setStyle("-fx-background-color: #d7e3fc;-fx-border-radius: 3; -fx-border-width: 0.3px; -fx-border-color: #000000;");


        travelDiaryDashboardView.getUpdateBtn().setDisable(false);
        travelDiaryDashboardView.getUpdateBtn().setStyle("-fx-background-color: #d7e3fc;-fx-border-radius: 3; -fx-border-width: 0.3px; -fx-border-color: #000000;");


        travelDiaryDashboardView.getAddBtn().setDisable(false);
        travelDiaryDashboardView.getAddBtn().setStyle("-fx-background-color: #d8e2dc;-fx-border-radius: 3; -fx-border-width: 0.3px; -fx-border-color: #000000;");

        travelDiaryDashboardView.getDeleteBtn().setDisable(false);
        travelDiaryDashboardView.getDeleteBtn().setStyle("-fx-background-color: #fec5bb;-fx-border-radius: 3; -fx-border-width: 0.3px; -fx-border-color: #000000;");
    }

    // get User obj based on username
    public User getUser(String username) {
        return users.get(username);
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public boolean register(String fname, String lname, String email, String username, String password) {
        if (username.isEmpty() || password.isEmpty() || fname.isEmpty() || lname.isEmpty() || email.isEmpty()) {
            return false;
        }

        if (users.containsKey(username)) {
            return false;
        }

        userModel = new User(username, password, fname, lname, email); // initialize user model

        travelDiaryModel.addUser(userModel); // add User to TravelDiary model

        users.put(username, userModel);
        return true;
    }

    public Boolean showAlertImageRemoval(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this image?");
        Optional<ButtonType> result = alert.showAndWait();

        // if user clicks ok - return true , else false
        if (result.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }
    }

    // write the objects to the file
    public void saveDataToFile(String filename) {
        File file = new File(filename);

        try {
            // create file if it does not exist
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }

            // Write the TravelDiary object to the file
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                out.writeObject(travelDiaryModel);
                System.out.println("Data saved to file.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // load data from file
    public void loadDataFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            travelDiaryModel = (TravelDiary) in.readObject();

            // get all users from travel diary and add to users hash map for log in purposes
            for(User user : travelDiaryModel.getAllUsers()){
                users.put(user.getUsername(), user);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}




