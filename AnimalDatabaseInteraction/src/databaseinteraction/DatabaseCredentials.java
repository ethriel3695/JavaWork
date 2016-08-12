/*
 * This class contains the url for the mySQL database, the username and the
 * password to create a connection to the mySQL database named animaldirectory
 * The values only have a get method and cannot be edited by the user in any
 * way.  The only way to edit the values is for the developer to go in and 
 * physically change the values.
 */
package databaseinteraction;

/**
 *
 * @author ethri
 */
public class DatabaseCredentials {
    private String
            mySQLUrl = "jdbc:mysql://localhost:3306/animaldirectory?useSSL=false",
            mySQLUser = "root",
            mySQLPwd = "P97dd5!P";
    
    public String urlValue()
    {
        return mySQLUrl;
    }
    
    public String userName()
    {
        return mySQLUser;
    }
        
    public String userPass()
    {
        return mySQLPwd;
    }
}
