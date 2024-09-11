package com.example.finalproject.Model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;

// make the object Serializable to be able to write the whole object to the file
public class TravelDiary implements Serializable{
    private static final long serialVersionUID = 1L;

    // Contains User - Collection of Journal Entries for each User
    private Map<User, ArrayList<JournalEntry>> userCollection;

    public TravelDiary(){
        this.userCollection = new HashMap<>();
    }

    public ArrayList<JournalEntry> getCollection(User user){
        return userCollection.get(user);
    }

    public boolean userExists(User user) {
        return userCollection.containsKey(user);
    }

    // create new journal entry and add to collection
    public void addJournalEntry(String country, String city, String details, String imagePath, User user){
        JournalEntry journalEntry = new JournalEntry(country, city, details, imagePath);
        userCollection.get(user).add(journalEntry); // add journal entry to user's collection
    }

    // Update existing journal entry at a specific index
    public void updateJournalEntry(User user, int index, String country, String city, String details, String imagePath) {
        if (userExists(user) && index >= 0 && index < userCollection.get(user).size()) {
            JournalEntry journalEntry = userCollection.get(user).get(index);
            journalEntry.setCountry(country);
            journalEntry.setCity(city);
            journalEntry.setDetails(details);
            journalEntry.setSelectedImage(imagePath);
        }
    }

    public void addUser(User user){
        // initialize the user's collection
        if (!userCollection.containsKey(user)) {
            userCollection.put(user, new ArrayList<>());
        }
    }

    public void removeJournalEntry(User user, int index) {
        if (userExists(user) && index >= 0 && index < userCollection.get(user).size()) {
            userCollection.get(user).remove(index);
        }
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userCollection.keySet());
    }

}
