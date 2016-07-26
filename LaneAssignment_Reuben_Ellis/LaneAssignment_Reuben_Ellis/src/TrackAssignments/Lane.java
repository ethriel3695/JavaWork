/*
 * @author Reuben Ellis
 * Date: 2016-07-17
 * Singleton Assign Lanes on a Track Project for PRG/421
 * This class is for the Lane Instance so users
 * can assign a participant to a lane
 */
package TrackAssignments;

import java.util.*;

public class Lane {
    //A static and final Instance of the Class so only one person can
    //be assigned to a lane
    private static final Lane LANEINSTANCE = new Lane();
    
    //A list of available Track Lanes
    private Set<String> availableLanes;
    
    //A public method to allow users to access the Class Instance
    public static Lane specificLane()
    {
        return LANEINSTANCE;
    }
    
    //Private Class constructor so the Lane properties cannot be altered
    private Lane()
    {
        availableLanes = new HashSet<>();
        availableLanes.add("Lane1");
        availableLanes.add("Lane2");
        availableLanes.add("Lane3");
        availableLanes.add("Lane4");
        availableLanes.add("Lane5");
        availableLanes.add("Lane6");
        availableLanes.add("Lane7");
        availableLanes.add("Lane8");
    }
    
    //A boolean method which removes the lane from the list of
    //available lanes if the lane has already been assigned
    public boolean assignLane (String lane)
    {
        return availableLanes.remove(lane);
    }
    
    public Set<String> availableLanesList()
    {
        return availableLanes;
    }
}
