package rrordonez.speechapp;

/**
 * Created by ReyDonez on 11/3/2015.
 */
public class Exercise {
    //table name
    public static final String TABLE = "Exercise";

    //table column names
    public static final String KEY_EXERCISE_ID = "exercise_ID";
    public static final String KEY_SESSION_ID = "session_ID";
    public static final String KEY_LETTER = "letter";
    public static final String KEY_POSITION = "position";
    public static final String KEY_SCORE = "score";

    //string for creating the table
    public static final String CREATE_TABLE_EXERSICE = "CREATE TABLE " + Exercise.TABLE + "("
            + Exercise.KEY_EXERCISE_ID + " INTEGER PRIMARY KEY, "
            + Exercise.KEY_SESSION_ID + " INTEGER, "
            + Exercise.KEY_LETTER + " TEXT, "
            + Exercise.KEY_POSITION + " TEXT, "
            + Exercise.KEY_SCORE + " INTEGER)";

    //properties
    public int exercise_ID;
    public long session_ID;
    public String letter;
    public String position;
    public int score;
}
