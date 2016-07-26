/*
 * @author Reuben Ellis
 * Date: 2016-07-17
 * Singleton Assign Lanes on a Track Project for PRG/421
 * The TrackLanes class provides functionality to assign Lanes based
 * on the Lane class, global variables, methods to check for data validation
 * and error checks as well as an iteration to assign all the lanes
 * The application only needs a  number entered for the Lane and then a
 * a first and last name for the participant
 */

package TrackAssignments;

import java.util.*;

public class TrackLanes {

    private static String 
        laneName = "",
        laneParticipantFirstName = "",
        laneParticipantLastName = "",
        laneStatus = "";
    
    private static int
        laneInput = 0;
    
    private static Scanner 
        inputValue = new Scanner(System.in);
    
    private static Lane 
        laneChoice = Lane.specificLane();
    
    /**
     * The main method begins the program
     * The for loop iterates through all 8 lanes on the track,
     * verifies the lane, first name, last name and prints out a message
     * indicating the participant has been assigned to a lane number
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
        for (int i =1; i < 9; i++)
        {
            laneInputVerification();
            nameInputVerification("first name");
            nameInputVerification("last name");

            System.out.println(laneParticipantFirstName + " " + 
                laneParticipantLastName + " assigned " + "to " + laneName);
        }

        System.out.println("All eight lanes have participants assigned!"
            + "\nGoodbye!"); 
    }
    
    //Assigns a participant to a lane using the Lane Class method
    private static void assigningLaneParticipant(String lane)
    {
        laneChoice.assignLane(lane);
    }
    
    //Recieves the value from the user for the lane and validates
    //based on if the value is between 1 and 8 or if the lane has already
    //been assigned
    private static void laneInputVerification()
    {
        System.out.println("Please enter a lane number between 1 "
                    + "and 8.");
        do
        {
            while (!inputValue.hasNextInt()) 
            {
                System.out.println("Please enter a number!");
                inputValue.next();
            }
            laneInput = inputValue.nextInt();
            laneName = "Lane" + Integer.toString(laneInput);
            
            if (laneChoice.availableLanesList().contains(laneName))
            {
                laneStatus = "true";
            }
            else
            {
                laneStatus = "false";
                if (laneInput > 0 && laneInput < 9)
                {
                    System.out.println("A participant already exists in " +
                        laneName + ". Please enter a different lane number.");
                }
            }
                        
            if (laneInput > 0 && laneInput < 9)
            {
            assigningLaneParticipant(laneName); 
            }
            else
            {
                System.out.println("Invalid lane entry. Please enter a number"
                        + " between 1 and 8.");
            }            
        } while (laneInput < 1 || laneInput > 8 || laneStatus.equals("false"));
    }
    
    //Gets the name of the participant and assigns the first and last names
    //to variables used for printing the name of the participant in the 
    //main method of this program
    private static void nameInputVerification(String name)
    {
        System.out.println("Please Enter the participant " + name
            + " for the Lane chosen.");
        
        if (name.equals("first name"))
        {
            laneParticipantFirstName = inputValue.next();
        }
        else if (name.equals("last name"))
        {
            laneParticipantLastName = inputValue.next();
        }
    }
    
}
