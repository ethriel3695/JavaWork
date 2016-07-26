package race;
import java.util.*;

public class Track {

    private static Race 
        laneChoice = Race.getInstance();
    
    private static Scanner 
            input = new Scanner(System.in);
    
    private static String 
        laneName = "",
        status = "";
    
    private static int
            runner = 0;
    private static ArrayList
            laneNumbers = new ArrayList();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    pickTrack();
       
    // Once all the track numbers are entered , the data is then
    // displayed for the user to review.
    System.out.println("The track positions are: \n ");

        for (int i = 0; i < 8; i++)
        {
            int lane = i + 1;
            System.out.println("Runner " + lane + " on track " + 
                    laneNumbers.get(i));
        }
    }
    
    private static void assigningLaneParticipant(String racer)
    {
        laneChoice.assignRacer(racer);
    }
    
    // This is where the user can assign the track lane to a runner.
    public static void pickTrack()
    {
    try{

        for (int i =1; i < 9; i++)
        {
            System.out.print("Enter the track number for runner " + i + ": ");
        do
        {
            runner = input.nextInt();
              
            laneName = "Lane" + Integer.toString(runner);
        
            if (laneChoice.availableRacerLanesList().contains(laneName))
            {
                status = "true";
                laneNumbers.add(runner); 
            }
            else
            {
                status = "false";
                if (runner > 0 && runner < 9)
                {
                    System.out.println("A participant already exists in " +
                        laneName + ". Please enter a different lane number.");
                }
            }
            if (runner > 0 && runner < 9)
            {
            assigningLaneParticipant(laneName); 
            }
            else
            {
                System.out.println("Invalid lane entry. Please enter a number"
                        + " between 1 and 8.");
            }            
        }while (runner < 1 || runner > 8 || status.equals("false"));
        }       
    }
catch(Exception e)
        {
            System.out.println(e);
            System.out.println("The user probably entered a letter and not a number");
        }
    }
}
