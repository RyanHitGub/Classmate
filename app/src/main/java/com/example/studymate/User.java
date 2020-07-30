package com.example.studymate;

public class User {

    private String firstName;
    private String lastName;

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String name){
        this.firstName = name;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String name){
        this.lastName = name;
    }

    public User(String fName, String lName){
        this.firstName = fName;
        this.lastName = lName;
    }
}
