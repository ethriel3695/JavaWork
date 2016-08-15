
package databaseinteraction;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class animalDirectoryViewing implements Callable<String> {
    
    private ArrayList<String>
            animalDirectory = new ArrayList<String>();
    
    private DatabaseCredentials
            dbCredentials = new DatabaseCredentials();
    
    private final String 
            mySQLDriver = "com.mysql.cj.jdbc.Driver";
    
    private String
            url = dbCredentials.urlValue(),
            user = dbCredentials.userName(),
            pwd = dbCredentials.userPass(),
            name = "",
            color = "",
            uniqueTrait = "",
            legs = "",
            animalName = "";
    
    private int
        animalID = 0;

    private synchronized void viewAnimalFromDatabase(String animal) throws SQLException
    {
        Connection
                mySQLConnection = null;
        PreparedStatement
                secureStatement = null;
        String animalQuery = "SELECT animalID, animalName, "
                + "animalColor, numberOfLegs, animalUniqueClass FROM animal"
                + " WHERE animalName = ?";
        
        try {
            mySQLConnection = mySQLDatabaseConnection();

            secureStatement = mySQLConnection.prepareStatement(animalQuery);

            secureStatement.setString(1, animal);
            
            ResultSet
                    rs = secureStatement.executeQuery();
            
            if (!rs.first())
            {
                out.println("Animal does not exist in the wildlife reserve!");
            }
            else
            {
                animalID = rs.getInt("animalID");
                name = rs.getString("animalName");
                color = rs.getString("animalColor");
                legs = rs.getString("numberOfLegs");
                uniqueTrait = rs.getString("animalUniqueClass");

                out.println(String.format("\nAnimal ID: %1s"
                        + "\nAnimal Name: %2s \nAnimal Color: %3s"
                        + "\nNumber of Legs:%4s \nUnique Class: %5s",
                        animalID, name, color, legs, uniqueTrait));
            }

            
        }catch (SQLException se){
            out.println(se.getMessage());
        } finally {
            if (secureStatement != null) {
                secureStatement.close();
            }
            
            if (mySQLConnection != null) {
                mySQLConnection.close();
            }
        }
    }
    
    private Connection mySQLDatabaseConnection()
    {
        Connection mySQLConnection = null;
        
        try {
            Class.forName(mySQLDriver);
        }catch (ClassNotFoundException e){
            out.println(e.getMessage());
        }
        
        try {
            mySQLConnection = DriverManager.getConnection(url, user, pwd);
            return mySQLConnection;
        }catch (SQLException se) {
            out.println(se.getMessage());
        }
        return mySQLConnection;
    }
    
    @Override
    public String call() throws SQLException, InterruptedException
    {
        String
            threadValue = Thread.currentThread().getName().substring(14);
        if (threadValue.equals("1"))
        {
            animalName = "Giraffe";
        }
        else if (threadValue.equals("2"))
        {
            animalName = "Penguin";
        }
        else if (threadValue.equals("3"))
        {
            animalName = "Lizard";
        }
        else if (threadValue.equals("4"))
        {
            animalName = "Eagle";
        }

        viewAnimalFromDatabase(animalName);
        
        return animalName;
    }
}
