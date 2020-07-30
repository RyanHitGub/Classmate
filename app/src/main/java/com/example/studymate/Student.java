package com.example.studymate;

public class Student extends User {
    //use AccountManager for userName and password
    private String username;
    private String password;
    //institution will become a class in future iterations of the app
    private String institution;
    private String emailAddress;
    //course may become a class in future iterations of the app
    private String[] course;

    public Student(String fName, String lName, String institution, String emailAddress,
                   String username, String password) {
        super(fName, lName);
        this.username = username;
        this.password = password;
        this.institution = institution;
        this.emailAddress = emailAddress;
    }

    //getter
    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getInstitution() {
        return institution;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String[] getCourse() {
        return course;
    }

    //setter
    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setCourse(String[] course) {
        this.course = course;
    }
}
