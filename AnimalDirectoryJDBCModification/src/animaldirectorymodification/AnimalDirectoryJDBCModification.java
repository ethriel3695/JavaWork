package animaldirectorymodification;

import databaseinteraction.DatabaseCredentials;
import java.io.*;
import static java.lang.System.out;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.swing.filechooser.FileSystemView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * @author Reuben Ellis
 * Date: 2016-08-06
 * Read Animal Objects from a file after animals are created and values 
 * retrieved from a database for PRG/421

 * The org.json.simple jar file was downloaded from: 
 * https://code.google.com/archive/p/json-simple/downloads and used with
 * import statements

 * The Animal Directory program creates a JSON file with Animal Objects
 * The JSON objects are unordered and have a key and value pair with the key
 * being the resource for finding the values within the file
 * The JSON arrays contain all the attributes for each animal
 * The directory variable hold the dynamic home directory for whichever machine
 * uses the application
 
 * The write method adds objects by using the Map put method and a key value
 * pair while the JSONArray's use the add method
 * The file name is AnimalDirectory.json and the File class is used to 
 * create the file and a message is printed for the user if the file creation
 * is successful and complete with the file path where the file is located
 * A try-catch method is in place for the file creation in the event the file
 * cannot be written to the home directory due to permission issues
 * Lastly, at the end of the method, the JSON is put in a JSON string format
 * and the FileWriter is flushed and closed and the read method is called

 * The readAnimalDirectory method parse's the JSON objects using FileReader and
 * creates a JSONObject with the parsed data
 * The main animal directory main object is obtained and printed out
 * The animal names and attribute titles are added to ArrayLists and sorted and
 * a foreach loop iterates through the animal names to obtain the attributes
 * The iteration is possible using Iterator and a while loop to iterate through
 * the attributes until no more attributes exist for the current animal
 * The method contains a try-catch statement to catch if a file is not found, 
 * the file cannot be read due to permission denied, or if the parse fails
 */

public class AnimalDirectoryJDBCModification {
    
    private static JSONObject 
            animalList = new JSONObject();
    
    private static JSONArray 
            animalAttributes = new JSONArray();
    
    private static ArrayList<String> 
            animalNames = new ArrayList<String>();
    
    private static String
            directory = System.getProperty("user.home");
    
    private static ArrayList<String>
            attributes = new ArrayList<String>();
    
    private static DatabaseCredentials
        dbCredentials = new DatabaseCredentials();
    
    private static final String 
            mySQLDriver = "com.mysql.cj.jdbc.Driver";
    
    private static String
            userSelection = "",
            input = "",
            view = "view",
            add = "add",
            url = dbCredentials.urlValue(),
            user = dbCredentials.userName(),
            pwd = dbCredentials.userPass(),
            name = "",
            color = "",
            uniqueTrait = "",
            legs = "";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
                writeAnimalDirectory();
            } catch (SQLException se) {
                out.println(se.getMessage());
            }
    }
    
    private static void writeAnimalDirectory() throws SQLException
    {
        Connection
            mySQLConnection = null;
        PreparedStatement
                secureStatement = null;
        String animalQuery = "SELECT animalName, "
                + "animalColor, numberOfLegs, animalUniqueClass FROM animal";
        
        animalList.put("Name", "Wildlife Reserve Repository");
        
        try {
            mySQLConnection = mySQLDatabaseConnection();

            secureStatement = mySQLConnection.prepareStatement(animalQuery);
            
            ResultSet
                    rs = secureStatement.executeQuery();
            if (!rs.first())
            {
                out.println("Database is Empty!");
            }
            else
            {
                rs.next();
                do {
                    name = rs.getString("animalName");
                    color = rs.getString("animalColor");
                    legs = rs.getString("numberOfLegs");
                    uniqueTrait = rs.getString("animalUniqueClass");

                    animalAttributes = new JSONArray();

                    animalAttributes.add(color);
                    animalAttributes.add(legs);
                    animalAttributes.add(uniqueTrait);

                    animalList.put(name, animalAttributes);

                    animalNames.add(name);
                }while (rs.next());
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
        
        try{
            //Write the JSON file to the user's home directory
            File file = new File(directory, "AnimalDirectory.json");
            file.createNewFile();
            FileWriter animalFile = new FileWriter(file);
            System.out.println("JSON file sucessfully created in: " + file);
            
            animalFile.write(animalList.toJSONString());
            animalFile.flush();
            animalFile.close();
            
            readAnimalDirectory();
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void readAnimalDirectory()
    {
        JSONParser parseAnimalObject = new JSONParser();
        
        try{
            String fullFilePath = directory + "\\AnimalDirectory.json";
            Object animalDirectory = parseAnimalObject.parse
                (new FileReader(fullFilePath));
            
            JSONObject animalObject = (JSONObject) animalDirectory;
            
            String directoryName = (String)animalObject.get("Name");
            System.out.println("\nAnimal Directory: " + directoryName);
            
            Collections.sort(animalNames);
            
            attributes.add("Animal Color: ");
            attributes.add("Class: ");
            attributes.add("Unique Trait: ");
            
            for (String animal : animalNames)
            {
                JSONArray allAnimals = (JSONArray)animalObject.get(animal);
                
                Iterator<String> animalIterator = allAnimals.iterator();
                Iterator<String> titleIterator = attributes.iterator();
                
                //The (char)27 and "[34m" changes the text to blue using
                //ANSCI escape codes
                System.out.println("\n" + (char)27 + 
                        "[34m" + animal + ": " + (char)27 + "[0m");
                
                while (animalIterator.hasNext()){
                    System.out.println("\t" + titleIterator.next()
                            + animalIterator.next());
                }
            }  
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
    
    private static Connection mySQLDatabaseConnection()
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
}
