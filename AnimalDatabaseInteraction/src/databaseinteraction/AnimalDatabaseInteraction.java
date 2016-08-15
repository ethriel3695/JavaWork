package databaseinteraction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.lang.System.*;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/*
 * @author Reuben Ellis
 * Date: 2016-08-05
 * Database JDBC Animal Interaction for PRG/421

 * I used several bits and pieces of user entry and validation from my 
 * AnimalInteraction application which was written in Week 2 of PRG 421.  Most
 * of the code however is new especially the code specifically for the 
 * database requirements of week 4.

 * The mySQL database is called animaldirectory and the table is called animal.
 * The five columns are all marked as not null (nn) and the names and types are'
 * as follows:
 * animalID: INT(11) Auto Increment
 * animalName: VARCHAR(100)
 * animalColor: VARCHAR(100)
 * numberOfLegs: VARCHAR(50)
 * animalUniqueClass: VARCHAR(100)

 * The AnimalDatabaseInteraction class provides a scanner property to get user
 * inputs and assign the values respectively.  
 * The DatabaseCredentials class is instantiated so the user does not have
 * direct access to the database url, username, and password
 * A variable also exists for the mySQL driver name required for the use of the
 * mySQL database connection

 * The main method calls the welcomePrompt method which welcomes the user to 
 * the application and asks the user to enter either view or add to view 
 * animal information within the wildlife reserve or add an animal to the
 * database and wildlife reserve.
 * The main method then calls the addOrView method which checks if a user 
 * enters add or view and will not move on until the user has made a correct 
 * choice.  The user response is assigned to a variable.
 * The last step of the main method is to check whether the variable contains
 * the value view or add and calls the correct method.  The methods are within
 * a try catch block which catches SQLException if either of the methods fail
 * with a SQLException.  Also, a do while loop contains all of the code and 
 * the user can enter quit to leave the program.

 * The viewAnimalFromDatabase method creates a Connection and PreparedStatement
 * variable.  The method also creates a select sql string which gets the animal
 * information based on the value the user enters for the animal name.  The code
 * is contained within a do while loop and the user enters done to exit or can 
 * press any key to continue.  The method then goes into a try-catch-finally
 * and the first step is for the connection to call the mySQLDatabaseConnection
 * method which returns the connection to the database if successful.
 * The prepared statement executes the query and sets the value of the animal 
 * name to the value the user inputs.  A ResultSet is created from the results
 * of the prepared statement executequery method.  If the result is empty, the
 * user is informed with a message stating the animal does not exist and if the
 * result contains a value then a do while loop is initiated which assigns all
 * the value to variables and then the values are printed to the console. A 
 * finally block checks to see if the prepared statement contains a value and 
 * closes it and then does the same thing for the Connection.

 * The addAnimalToDatabase method creates a Connection and PreparedStatement
 * variable.  The method also creates an Insert Into sql string which 
 * which names the table and the four attributes which can be changed and 
 * leaves a Values(?, ?, ?, ?) which will fill the values with user inputs.
 * The method then prompts the user to enter the animal name, color, unique
 * trait, and number of legs.  Only the unique value has a check because the
 * value must be one of three types.  The method then goes into a 
 * try-catch-finally and the first step is for the connection to call the 
 * mySQLDatabaseConnection method which returns the connection to the database 
 * if successful.
 * The prepared statement executes the query and sets the string values from 
 * the user input to the respective columns within the database.  The
 * PreparedStatement executeUpdate method is then called to complete insert
 * and update the database and the user receives a message stating the animal
 * has been successfully added to the database. The finally method then closes
 * the PreparedStatement and the Connection to the database.  The user has the
 * option to enter done and return to the main menu or hit any key to go back
 * to the beginning of the method and enter another animal.

 * The mySQLDatabaseConnection method contains two try-catch methods.  The first
 * one checks for the mySQL driver and passes if the driver exists.  The second
 * try-catch checks to see if the connection to the databse is established and
 * if the connection passes then the connection is made and the connection 
 * is passed back to the variable which holds the connection from the method
 * call.
 */

public class AnimalDatabaseInteraction {

    private static Scanner 
        userInput = new Scanner(System.in);
    
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
    
    private static int
            animalID = 0;
    
    private static ArrayList<String>
            currentAnimals = new ArrayList<String>();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        do
        {
            welcomePrompt();

            userSelection = addOrView();
            
            try {
                if (userSelection.equalsIgnoreCase("view"))
                {
                    ExecutorService
                        animalNamesList = Executors.newFixedThreadPool(4);
                                        
                    Callable<String> 
                        animal1 = new animalDirectoryViewing();
                    Callable<String> 
                        animal2 = new animalDirectoryViewing();
                    Callable<String> 
                        animal3 = new animalDirectoryViewing();
                    Callable<String> 
                        animal4 = new animalDirectoryViewing();
        
                    animalNamesList.submit(animal1);
                    animalNamesList.submit(animal2);
                    animalNamesList.submit(animal3);
                    animalNamesList.submit(animal4);
                    
                    try {
                        boolean terminateAnimalViews = 
                                animalNamesList.awaitTermination(2, 
                                        TimeUnit.SECONDS);
                    } catch (InterruptedException ex1)
                    {
                        System.out.println("Did not wait 2 seconds");
                    } finally
                    {
                        if (!animalNamesList.isTerminated())
                        {
                            List<Runnable> streetAssignments = 
                                    animalNamesList.shutdownNow();
                        }
                    }
            }
            else if (userSelection.equalsIgnoreCase("add"))
            {
                addAnimalToDatabase();
            }
            } catch (SQLException se) {
                out.println(se.getMessage());
            }

            Thread.sleep(6000);
            System.out.println("\nPlease enter quit to end the program "
                + "\nor anything else to continue: ");
            input = userInput.nextLine();
        }while (!input.equalsIgnoreCase("quit"));

    }
    
    private static void welcomePrompt()
    {
        String
                welcomeMessage = "Welcome to the Animal Interaction Zone!",
                optionsMessage = "\nPlease enter one of the following options: "
                + "view, or add";
            
        System.out.println(welcomeMessage);
        System.out.println(optionsMessage);
    }
    
    private static String addOrView()
    {
        input = userInput.nextLine();
        while(!input.equalsIgnoreCase(view) && !input.equalsIgnoreCase(add))
        {  
            System.out.println("Please enter either: view, or add");
            input = userInput.nextLine();
        }
        if (input.equalsIgnoreCase(view))
        {
            input = view;
        }
        else if (input.equalsIgnoreCase(add))
        {
            input = add;
        }
        return input;
    }
    
    private static void addAnimalToDatabase() throws SQLException
    {
        Connection
                mySQLConnection = null;
        PreparedStatement
                secureStatement = null;
        String animalQuery = "INSERT INTO animal"
            + "(animalName, animalColor, numberOfLegs, animalUniqueClass)"
                + "VALUES(?, ?, ?, ?)";
        
        do
        {
            System.out.println("Enter the following information to add an "
                + "animal to the wildlife reserve: ");
                System.out.println("Animal Name: ");
                input = userInput.nextLine();
                name = input;
                System.out.println("Animal Color: ");
                input = userInput.nextLine();
                color = input;
                System.out.println("Enter either 'hair' for a mammal, "
                    + "'feathers' for a bird, or 'coldBlood' for a reptile: ");
    
                input = userInput.nextLine();

                uniqueTrait = input;

                while (!input.equalsIgnoreCase("hair") && 
                        !input.equalsIgnoreCase("feathers") && 
                        !input.equalsIgnoreCase("coldBlood"))
                {
                    System.out.println("\nPlease enter either hair, feathers, "
                            + "or coldBlood: ");
                    input = userInput.nextLine();
                }
                System.out.println("Number of Legs: ");
                input = userInput.nextLine();
                legs = input;
        try {
            mySQLConnection = mySQLDatabaseConnection();

            secureStatement = mySQLConnection.prepareStatement(animalQuery);

            secureStatement.setString(1, name);
            secureStatement.setString(2, color);
            secureStatement.setString(3, legs);
            secureStatement.setString(4, uniqueTrait);
            
            secureStatement.executeUpdate();
            
            out.println(name + " has been inserted into the animal database!");
            
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
        
        System.out.println("\nPlease enter done to return to the main page "
            + "\nor anything to continue: ");
        input = userInput.nextLine();
        
        }while(!input.equalsIgnoreCase("done"));
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
