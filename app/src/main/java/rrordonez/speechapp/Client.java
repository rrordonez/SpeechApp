package rrordonez.speechapp;

/**
 * Created by ReyDonez on 10/22/2015.
 */
public class Client {
    //table name
    public static final String TABLE = "Clients";

    //table column names
    public static final String KEY_ID = "_Id";
    public static final String KEY_CLIENT_ID = "client_id";
    public static final String KEY_AGE = "age";

    //create table string
    public static final String CREATE_TABLE_CLIENT = "CREATE TABLE " + Client.TABLE + "("
            + Client.KEY_ID + " INTEGER PRIMARY KEY ,"
            + Client.KEY_CLIENT_ID + " INTEGER, "
            + Client.KEY_AGE + " INTEGER)";

    //properties
    public int _ID;
    public int client_ID;
    public int age;

}
