package com.example.studymate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Subclass of SQLiteOpenHelper
public class DbHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "userDB";

    //User
    private static final String TABLE_Users = "userDetails";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_LAST_NAME = "lastName";
    private static final String KEY_USERNAME_NUMBER = "usernameNumber";
    private static final String KEY_INSTITUTION = "institution";
    private static final String KEY_EMAIL_ADDRESS = "emailAddress";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    //Enrolled course
    private static final String TABLE_Enrolled_Courses = "enrolledCourses";
    private static final String KEY_ENROLLED_ID = "enrolledId";
    private static final String KEY_ENROLLED_USER = "enrolledUser";
    private static final String KEY_ENROLLED_SCHOOL = "enrolledSchool";
    private static final String KEY_ENROLLED_PROGRAMME = "enrolledProgramme";
    private static final String KEY_ENROLLED_YEAR = "enrolledYear";
    private static final String KEY_ENROLLED_COURSE = "enrolledCourse";

    //Course BIT Year 2
    private static final String TABLE_Courses = "courses";
    private static final String KEY_COURSE_ID = "courseId";
    private static final String KEY_COURSE_NAME = "courseName";

    //School
    private static final String TABLE_Schools = "schools";
    private static final String KEY_SCHOOL_ID = "schoolId";
    private static final String KEY_SCHOOL_NAME = "schoolName";

    //Programme IT
    private static final String TABLE_IT_Programmes = "programmesIT";
    private static final String KEY_IT_PROGRAMME_ID = "programmesITId";
    private static final String KEY_IT_PROGRAMME_NAME = "programmesITName";

    //Years
    private static final String TABLE_Years = "years";
    private static final String KEY_YEAR_ID = "yearId";
    private static final String KEY_YEAR_NAME = "yearName";



    //constructor
    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //create tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        //user table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_Users +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_FIRST_NAME + " TEXT," +
                KEY_LAST_NAME + " TEXT," +
                KEY_USERNAME_NUMBER + " INTEGER," +
                KEY_INSTITUTION + " TEXT," +
                KEY_EMAIL_ADDRESS + " TEXT," +
                KEY_USERNAME + " TEXT," +
                KEY_PASSWORD + " TEXT" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);

        //course table BIT year 2
        String CREATE_COURSES_TABLE = "CREATE TABLE " + TABLE_Courses +
                "(" +
                KEY_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_COURSE_NAME + " TEXT" +
                ")";
        db.execSQL(CREATE_COURSES_TABLE);

        //school table weltec
        String CREATE_SCHOOLS_TABLE = "CREATE TABLE " + TABLE_Schools +
                "(" +
                KEY_SCHOOL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_SCHOOL_NAME + " TEXT" +
                ")";
        db.execSQL(CREATE_SCHOOLS_TABLE);

        //programme table IT
        String CREATE_IT_PROGRAMMES_TABLE = "CREATE TABLE " + TABLE_IT_Programmes +
                "(" +
                KEY_IT_PROGRAMME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_IT_PROGRAMME_NAME + " TEXT" +
                ")";
        db.execSQL(CREATE_IT_PROGRAMMES_TABLE);

        //year table
        String CREATE_YEARS_TABLE = "CREATE TABLE " + TABLE_Years +
                "(" +
                KEY_YEAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_YEAR_NAME + " TEXT" +
                ")";
        db.execSQL(CREATE_YEARS_TABLE);

        //enrolled course table
        String CREATE_ENROLLED_COURSES_TABLE = "CREATE TABLE " + TABLE_Enrolled_Courses +
                "(" +
                KEY_ENROLLED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_ENROLLED_USER + " INTEGER," +
                KEY_ENROLLED_SCHOOL + " INTEGER," +
                KEY_ENROLLED_PROGRAMME + " INTEGER," +
                KEY_ENROLLED_YEAR + " INTEGER," +
                KEY_ENROLLED_COURSE + " INTEGER," +
                "FOREIGN KEY (" + KEY_ENROLLED_USER + ") REFERENCES " + TABLE_Users + "(" + KEY_ID + ")," +
                "FOREIGN KEY (" + KEY_ENROLLED_SCHOOL + ") REFERENCES " + TABLE_Schools + "(" + KEY_ENROLLED_ID + ")," +
                "FOREIGN KEY (" + KEY_ENROLLED_PROGRAMME + ") REFERENCES " + TABLE_IT_Programmes + "(" + KEY_IT_PROGRAMME_ID + ")," +
                "FOREIGN KEY (" + KEY_ENROLLED_YEAR + ") REFERENCES " + TABLE_Years + "(" + KEY_YEAR_ID + ")," +
                "FOREIGN KEY (" + KEY_ENROLLED_COURSE + ") REFERENCES " + TABLE_Courses + "(" + KEY_COURSE_ID + ")" +
                ")";
        db.execSQL(CREATE_ENROLLED_COURSES_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Schools);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IT_Programmes);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Years);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Courses);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Enrolled_Courses);
        //create tables again
        onCreate(db);
    }

    //check to populate tables
    public void checkTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_Schools;
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int i = cursor.getInt(0);
        if(i>0){}
        else{
            populateTables();
        }
    }

    private void populateTables(){

        //schools
        String[] schoolList = {"Information Technology School", "Engineering School", "Business School"};
        for (String school : schoolList) {
            insertSchoolDetails(school);
        }

        //programmes IT
        String[] itProgrammeList = {"Bachelor of Information Technology (Level 7)",
                "Graduate Diploma in Information Assurance and Security (Level 7)",
                "Graduate Diploma in Information Technology (Level 7)",
                "NZ Certificate in Information Technology Essentials (Level 4)",
                "NZ Diploma in IT Technical Support (Level 5)",
                "Postgraduate Certificate in Information Technology",
                "Postgraduate Diploma in Information Technology"};
        for (String programme : itProgrammeList){
            insertProgrammeITDetails(programme);
        }

        //years
        String[] yearList = {"Year 1", "Year 2", "Year 3"};
        for (String year : yearList){
            insertYearDetails(year);
        }

        //courses BIT year 2
        String[] itCourseList = {"CS6501 Information Security 2",
                "CS6502 Project Management",
                "IT6501 Systems Analysis and Design",
                "IT6502 Project Management",
                "IT6504 Embedded Systems 1",
                "IT6507 Wireless Networking Technology",
                "NI6501 Networking 2 LAN",
                "SD6501 Mobile Application Development",
                "SD6502 Programming 2"};
        for (String course : itCourseList) {
            insertCourseDetails(course);
        }

        //Add administrator
        insertUserDetails("Administrator", "Special", 1, "Weltec",
                "administrator@classmate.com", "admin", "Administrator1!");

        //Add default user
        insertUserDetails("ryan", "smith", 1, "Weltec",
                "ryan.smith001@student.weltec.ac.nz", "ryan.smith001", "Password1!");
    }

    //create new user
    public void insertUserDetails(String firstName, String lastName, int usernameNumber, String institution,
                           String emailAddress, String username, String password){

        //database in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        //create a new map of values, where column names are the keys
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_FIRST_NAME, firstName);
        contentValues.put(KEY_LAST_NAME, lastName);
        contentValues.put(KEY_USERNAME_NUMBER, usernameNumber);
        contentValues.put(KEY_INSTITUTION, institution);
        contentValues.put(KEY_EMAIL_ADDRESS, emailAddress);
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_PASSWORD, password);

        long newRowId = db.insert(TABLE_Users, null, contentValues);
        db.close();
    }

    //create new school
    public void insertSchoolDetails(String schoolName){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_SCHOOL_NAME, schoolName);

        long newRowId = db.insert(TABLE_Schools, null, contentValues);
        db.close();
    }

    //create new IT programme
    public void insertProgrammeITDetails(String programmeName){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_IT_PROGRAMME_NAME, programmeName);

        long newRowId = db.insert(TABLE_IT_Programmes, null, contentValues);
        db.close();
    }

    //create new year
    public void insertYearDetails(String yearName){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_YEAR_NAME, yearName);

        long newRowId = db.insert(TABLE_Years, null, contentValues);
        db.close();
    }

    //create new course
    public void insertCourseDetails(String courseName){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_COURSE_NAME, courseName);

        long newRowId = db.insert(TABLE_Courses, null, contentValues);
        db.close();
    }

    //create new enrolled course
    public void insertEnrolledCourseDetails(int enrolledUser, int enrolledSchool, int enrolledProgramme,
                                            int enrolledYear, int enrolledCourse){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ENROLLED_USER, enrolledUser);
        contentValues.put(KEY_ENROLLED_SCHOOL, enrolledSchool);
        contentValues.put(KEY_ENROLLED_PROGRAMME, enrolledProgramme);
        contentValues.put(KEY_ENROLLED_YEAR, enrolledYear);
        contentValues.put(KEY_ENROLLED_COURSE, enrolledCourse);

        long newRowId = db.insert(TABLE_Enrolled_Courses, null, contentValues);
        db.close();
    }


    //return all users from Table Users
    public ArrayList<HashMap<String, String>> GetUsers(){

        SQLiteDatabase db = this.getWritableDatabase();

        //array list with a hash map
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();

        //query to retrieve all information
        String query = "SELECT id, firstName, lastName, institution, emailAddress, username, password FROM "+ TABLE_Users;

        //pass the rawQuery result to a Cursor instance
        Cursor cursor = db.rawQuery(query,null);

        //write a loop that will collect the requested information and save it to a hashmap
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("id",cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put("firstName",cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
            user.put("lastName",cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME)));
            user.put("institution",cursor.getString(cursor.getColumnIndex(KEY_INSTITUTION)));
            user.put("emailAddress",cursor.getString(cursor.getColumnIndex(KEY_EMAIL_ADDRESS)));
            user.put("username",cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
            user.put("password",cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
            //add the hashmap to the array list
            userList.add(user);
        }
        return  userList;
    }

    //return a specific user from Table Users
    public ArrayList<HashMap<String, String>> GetCurrentUser(String userID){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();

        String query = "SELECT id, firstName, lastName, institution, emailAddress, username, password " +
                "FROM " + TABLE_Users +
                " WHERE id = '" + userID + "'";

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("id",cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put("firstName",cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
            user.put("lastName",cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME)));
            user.put("institution",cursor.getString(cursor.getColumnIndex(KEY_INSTITUTION)));
            user.put("emailAddress",cursor.getString(cursor.getColumnIndex(KEY_EMAIL_ADDRESS)));
            user.put("username",cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
            user.put("password",cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
            userList.add(user);
        }
        return  userList;
    }

    //get the user's enrolled courses
    public ArrayList<HashMap<String, String>> GetCurrentUserEnrolledCourses(String userID){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> enrolledCourseList = new ArrayList<>();

        String query = "SELECT firstName, schoolName, programmesITName, yearName, courseName " +
                "FROM " + TABLE_Enrolled_Courses +
                " INNER JOIN " + TABLE_Users + " ON " + TABLE_Enrolled_Courses + ".enrolledUser = " + TABLE_Users + ".id" +
                " INNER JOIN " + TABLE_Schools + " ON " + TABLE_Enrolled_Courses + ".enrolledSchool = " + TABLE_Schools + ".schoolId" +
                " INNER JOIN " + TABLE_IT_Programmes + " ON " + TABLE_Enrolled_Courses + ".enrolledProgramme = " + TABLE_IT_Programmes + ".programmesITId" +
                " INNER JOIN " + TABLE_Years + " ON " + TABLE_Enrolled_Courses + ".enrolledYear = " + TABLE_Years + ".yearId" +
                " INNER JOIN " + TABLE_Courses + " ON " + TABLE_Enrolled_Courses + ".enrolledCourse = " + TABLE_Courses + ".courseId" +
                " WHERE enrolledUser = '" + userID + "' " ;

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> enrolledCourse = new HashMap<>();
            enrolledCourse.put("firstName",cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
            enrolledCourse.put("schoolName",cursor.getString(cursor.getColumnIndex(KEY_SCHOOL_NAME)));
            enrolledCourse.put("programmesITName",cursor.getString(cursor.getColumnIndex(KEY_IT_PROGRAMME_NAME)));
            enrolledCourse.put("yearName",cursor.getString(cursor.getColumnIndex(KEY_YEAR_NAME)));
            enrolledCourse.put("courseName",cursor.getString(cursor.getColumnIndex(KEY_COURSE_NAME)));
            enrolledCourseList.add(enrolledCourse);
        }
        return  enrolledCourseList;
    }

    //return schools
    public ArrayList<HashMap<String, String>> GetSchools(){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> schoolList = new ArrayList<>();

        String query = "SELECT schoolName FROM "+ TABLE_Schools;

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> school = new HashMap<>();
            school.put("schoolName",cursor.getString(cursor.getColumnIndex(KEY_SCHOOL_NAME)));
            schoolList.add(school);
        }
        return schoolList;
    }

    //return IT programmes
    public ArrayList<HashMap<String, String>> GetITProgrammes(){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> programmeList = new ArrayList<>();

        String query = "SELECT programmesITName FROM "+ TABLE_IT_Programmes;

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> programme = new HashMap<>();
            programme.put("programmesITName",cursor.getString(cursor.getColumnIndex(KEY_IT_PROGRAMME_NAME)));
            programmeList.add(programme);
        }
        return programmeList;
    }

    //return years
    public ArrayList<HashMap<String, String>> GetYears(){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> yearList = new ArrayList<>();

        String query = "SELECT yearName FROM "+ TABLE_Years;

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> year = new HashMap<>();
            year.put("yearName",cursor.getString(cursor.getColumnIndex(KEY_YEAR_NAME)));
            yearList.add(year);
        }
        return yearList;
    }

    //return courses
    public ArrayList<HashMap<String, String>> GetITCourses(){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> courseList = new ArrayList<>();

        String query = "SELECT courseName FROM "+ TABLE_Courses;

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> course = new HashMap<>();
            course.put("courseName",cursor.getString(cursor.getColumnIndex(KEY_COURSE_NAME)));
            courseList.add(course);
        }
        return courseList;
    }


    //return single id from array list hash map when there is only one id
    private int getSingleID(ArrayList<HashMap<String, String>> list, String column){

        String id = "";
        for (Map<String, String> singleItem : list){
            id = singleItem.get(column);
        }
        return Integer.parseInt(id);
    }

    //use names to retrieve the primary keys
    //Get current user ID
    public int GetCurrentUserID(String username){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();

        String query = "SELECT id" +
                " FROM " + TABLE_Users +
                " WHERE username = '" + username + "'";

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("id",cursor.getString(cursor.getColumnIndex(KEY_ID)));
            userList.add(user);
        }
        return getSingleID(userList, "id");
    }

    //Get current school ID
    public int GetCurrentSchoolID(String schoolName){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> schoolList = new ArrayList<>();

        String query = "SELECT schoolId" +
                " FROM " + TABLE_Schools +
                " WHERE schoolName = '" + schoolName + "'";

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> school = new HashMap<>();
            school.put("schoolId",cursor.getString(cursor.getColumnIndex(KEY_SCHOOL_ID)));
            schoolList.add(school);
        }
        return getSingleID(schoolList, "schoolId");
    }

    //Get current programme ID
    public int GetCurrentProgrammeID(String programmeName){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> programmeList = new ArrayList<>();

        String query = "SELECT programmesITId" +
                " FROM " + TABLE_IT_Programmes +
                " WHERE programmesITName = '" + programmeName + "'";

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> programme = new HashMap<>();
            programme.put("programmesITId",cursor.getString(cursor.getColumnIndex(KEY_IT_PROGRAMME_ID)));
            programmeList.add(programme);
        }
        return getSingleID(programmeList, "programmesITId");
    }

    //Get current year ID
    public int GetCurrentYearID(String yearName){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> yearList = new ArrayList<>();

        String query = "SELECT yearId" +
                " FROM " + TABLE_Years +
                " WHERE yearName = '" + yearName + "'";

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> year = new HashMap<>();
            year.put("yearId",cursor.getString(cursor.getColumnIndex(KEY_YEAR_ID)));
            yearList.add(year);
        }
        return getSingleID(yearList, "yearId");
    }

    //Get current BIT year 2 course ID
    public int GetCurrentBITYear2CourseID(String courseName){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> courseList = new ArrayList<>();

        String query = "SELECT courseId FROM "+
                TABLE_Courses + " WHERE courseName = '" + courseName + "'";

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> course = new HashMap<>();
            course.put("courseId",cursor.getString(cursor.getColumnIndex(KEY_COURSE_ID)));
            courseList.add(course);
        }
        return getSingleID(courseList, "courseId");
    }


    //update user details in TABLE_Users
    public int UpdateUserDetails(String firstName, String lastName, String institution, String emailAddress,
                                  String username, String password, int id){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_FIRST_NAME, firstName);
        contentValues.put(KEY_LAST_NAME, lastName);
        contentValues.put(KEY_INSTITUTION, institution);
        contentValues.put(KEY_EMAIL_ADDRESS, emailAddress);
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_PASSWORD, password);
        int count = db.update(TABLE_Users, contentValues, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});

        db.close();
        return count;
    }

    //delete a user from TABLE_Users
    public void DeleteUser(int userId){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users, KEY_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close();
    }

    //delete an enrolled course from TABLE_Enrolled_Courses
    public void DeleteEnrolledCourse(int courseID){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Enrolled_Courses, KEY_ENROLLED_ID + " = ?", new String[]{String.valueOf(courseID)});
        db.close();
    }

    //check user's inputs when they attempt to login
    public boolean CheckLogin(String username, String password){

        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String> usernameList = new ArrayList<>();
        ArrayList<String> passwordList = new ArrayList<>();

        //get all the usernames
        String usernameQuery = "SELECT username FROM " + TABLE_Users;
        Cursor usernameCursor = db.rawQuery(usernameQuery, null);
        while (usernameCursor.moveToNext()){
            usernameList.add(usernameCursor.getString(usernameCursor.getColumnIndex(KEY_USERNAME)));
        }

        //get all the passwords
        String passwordQuery = "SELECT password FROM " + TABLE_Users;
        Cursor passwordCursor = db.rawQuery(passwordQuery, null);
        while (passwordCursor.moveToNext()){
            passwordList.add(passwordCursor.getString(passwordCursor.getColumnIndex(KEY_PASSWORD)));
        }

        //check them
        for (int i = 0; i < usernameList.size(); i++){
            if (usernameList.get(i).equals(username) && passwordList.get(i).equals(password)){
                return true;
            }
        }
        return false;
    }

    //check first and last names to determine what the student's username number will be
    //i.e. ryan.smith006
    public String CheckUsernameExists(String firstName, String lastName){

        int temp = 1;

        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String> firstNameList = new ArrayList<>();
        ArrayList<String> lastNameList = new ArrayList<>();

        //get all the first names
        String firstNameQuery = "SELECT firstName FROM " + TABLE_Users;
        Cursor firstNameCursor = db.rawQuery(firstNameQuery, null);
        while (firstNameCursor.moveToNext()){
            firstNameList.add(firstNameCursor.getString(firstNameCursor.getColumnIndex(KEY_FIRST_NAME)));
        }

        //get all the last Names
        String lastNameQuery = "SELECT lastName FROM " + TABLE_Users;
        Cursor lastNameCursor = db.rawQuery(lastNameQuery, null);
        while (lastNameCursor.moveToNext()){
            lastNameList.add(lastNameCursor.getString(lastNameCursor.getColumnIndex(KEY_LAST_NAME)));
        }

        //check them
        for (int i = 0; i < firstNameList.size(); i++){
            if (firstNameList.get(i).equals(firstName) && lastNameList.get(i).equals(lastName)){
                temp = GetUsernameNumber(firstName, lastName) + 1;
            }
        }
        if (temp < 10){
            return "00" + temp;
        }
        else if (temp < 100){
            return "0" + temp;
        }
        else
            return String.valueOf(temp);
    }

    //get username number
    private int GetUsernameNumber(String firstName, String lastName){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> nameList = new ArrayList<>();

        String query = "SELECT usernameNumber" +
                " FROM " + TABLE_Users +
                " WHERE firstName = '" + firstName + "' AND lastName = '" + lastName + "'";

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> year = new HashMap<>();
            year.put("usernameNumber",cursor.getString(cursor.getColumnIndex(KEY_USERNAME_NUMBER)));
            nameList.add(year);
        }
        return getSingleID(nameList, "usernameNumber");
    }

    //Get a single course from a user's enrolled courses
    public ArrayList<HashMap<String, String>> GetCurrentUserEnrolledCourse(String userID, String courseID){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> enrolledCourseList = new ArrayList<>();

        String query = "SELECT firstName, schoolName, programmesITName, yearName, courseName " +
                "FROM " + TABLE_Enrolled_Courses +
                " INNER JOIN " + TABLE_Users + " ON " + TABLE_Enrolled_Courses + ".enrolledUser = " + TABLE_Users + ".id" +
                " INNER JOIN " + TABLE_Schools + " ON " + TABLE_Enrolled_Courses + ".enrolledSchool = " + TABLE_Schools + ".schoolId" +
                " INNER JOIN " + TABLE_IT_Programmes + " ON " + TABLE_Enrolled_Courses + ".enrolledProgramme = " + TABLE_IT_Programmes + ".programmesITId" +
                " INNER JOIN " + TABLE_Years + " ON " + TABLE_Enrolled_Courses + ".enrolledYear = " + TABLE_Years + ".yearId" +
                " INNER JOIN " + TABLE_Courses + " ON " + TABLE_Enrolled_Courses + ".enrolledCourse = " + TABLE_Courses + ".courseId" +
                " WHERE enrolledUser = '" + userID + "' AND enrolledId = '" + courseID + "'";

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> enrolledCourse = new HashMap<>();
            enrolledCourse.put("firstName",cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
            enrolledCourse.put("schoolName",cursor.getString(cursor.getColumnIndex(KEY_SCHOOL_NAME)));
            enrolledCourse.put("programmesITName",cursor.getString(cursor.getColumnIndex(KEY_IT_PROGRAMME_NAME)));
            enrolledCourse.put("yearName",cursor.getString(cursor.getColumnIndex(KEY_YEAR_NAME)));
            enrolledCourse.put("courseName",cursor.getString(cursor.getColumnIndex(KEY_COURSE_NAME)));
            enrolledCourseList.add(enrolledCourse);
        }
        return  enrolledCourseList;
    }

    //Get a single course id
    public int GetCurrentCourseID(String userID, String courseName){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> courseList = new ArrayList<>();

        String query = "SELECT enrolledId" +
                " FROM " + TABLE_Enrolled_Courses +
                " INNER JOIN " + TABLE_Courses + " ON " + TABLE_Enrolled_Courses + ".enrolledCourse = " + TABLE_Courses + ".courseId" +
                " WHERE enrolledUser = '" + userID + "' AND courseName = '" + courseName + "'";

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> course = new HashMap<>();
            course.put("enrolledId",cursor.getString(cursor.getColumnIndex(KEY_ENROLLED_ID)));
            courseList.add(course);
        }
        return getSingleID(courseList, "enrolledId");
    }

    //Get current user username
    public String GetCurrentUserUsername(String userID){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();

        String query = "SELECT username" +
                " FROM " + TABLE_Users +
                " WHERE id = '" + userID + "'";

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("username",cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
            userList.add(user);
        }
        return getSingleUsername(userList, "username");
    }

    //return single username
    private String getSingleUsername(ArrayList<HashMap<String, String>> list, String column){

        String username = "";
        for (Map<String, String> singleItem : list){
            username = singleItem.get(column);
        }
        return username;
    }

}
