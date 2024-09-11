package com.example.finalproject.Model;

import java.io.Serializable;
import java.util.Objects;

// make the object Serializable to be able to write the whole object to the file
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String fname;
    private String lname;
    private String email;

    public User(String username, String password, String fname, String lname, String email) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername(){ return username;}

}

