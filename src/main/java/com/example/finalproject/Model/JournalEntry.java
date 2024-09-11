package com.example.finalproject.Model;
import javafx.scene.image.Image;
import java.io.File;
import java.io.Serializable;

// make the object Serializable to be able to write the whole object to the file
public class JournalEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private String country;
    private String city;
    private String details;
    // transient - do not write image to the file - make it not serializable
    private transient Image selectedImage; // for adding an image
    private String imagePath; // to store the path to image location

    public JournalEntry(String country, String city, String details, String imagePath){
        // File selectedFile - will be used to initialize image
        this.country = country;
        this.city = city;
        this.details = details;
        this.imagePath = imagePath;

        // create image
        if(imagePath != null){
            this.selectedImage = new Image(new File(imagePath).toURI().toString());
        }
        else{
            this.selectedImage = null;
        }
    }

    public String getCountry(){ return this.country; }

    public String getCity(){ return this.city; }

    public String getDetails(){ return this.details; }

    public Image getSelectedImage(){ return selectedImage; }

    public String getImagePath(){ return imagePath; }

    public void setCountry(String country){ this.country = country; }

    public void setCity(String city){ this.city = city; }

    public void setDetails(String details){ this.details = details; }

    public void setSelectedImage(String imagePath){
        if(imagePath != null){
            this.selectedImage = new Image(new File(imagePath).toURI().toString());
        }
        else{
            this.selectedImage = null;
        }
        this.imagePath = imagePath;
    }
}
