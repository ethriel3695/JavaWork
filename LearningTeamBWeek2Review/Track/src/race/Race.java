/*
 * @author Johnathon McNeil
 * @updated by Reuben Ellis and Jason Garrick
 * July 25, 2016
 * Singleton Racer assigned to Lanes
 */
package race;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Race {
    
    private static final Race RACEINSTANCE = new Race();

    private Set<String> Lanes;
    
    //A constructor which assigns a list of races to a HashSet list
    private Race()
    {
        Lanes = new HashSet<>();
        Lanes.add("Lane1");
        Lanes.add("Lane2");
        Lanes.add("Lane3");
        Lanes.add("Lane4");
        Lanes.add("Lane5");
        Lanes.add("Lane6");
        Lanes.add("Lane7");
        Lanes.add("Lane8");
    }

    public synchronized static Race getInstance()
    {
    return RACEINSTANCE;

    }
    
    //A boolean method which removes the lane from the list of
    //available lanes if the lane has already been assigned
    public boolean assignRacer (String race)
    {
        return Lanes.remove(race);
    }
    
    public Set<String> availableRacerLanesList()
    {
        return Lanes;
    }
}
