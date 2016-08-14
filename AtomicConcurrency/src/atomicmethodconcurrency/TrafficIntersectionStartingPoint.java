/*
 * @author Reuben Ellis
 * Date: 2016-08-13
 * This class implements Callable and has a method named atomic
 * The ArrayList contains names of the current thread so only four threads
 * can call the atomic method
 * The atomic method is set up where it is called concurrently unless the 
 * thread name already exists and then the thread which exists cannot invoke
 * atomic concurrently and gets blocked
 * The atomic method allows all four threads to access the method, but blocks 
 * a thread if it has already accessed the method and the method returns each
 * of the four Thread names to the call method
 * The call method which is an Override as a result of implementing Callable, 
 * returns the name of the thread as an integer for use in the main class
 * The name of the thread is passed as an int because the method substring is 
 * used with a value of 14 to only get the numeric value of the current thread
 */
package atomicmethodconcurrency;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TrafficIntersectionStartingPoint implements Callable<Integer> {
    
    private ArrayList<String>
            threadContainer = new ArrayList<String>();

    public String atomic()
    {
        String
                threadInstance = Thread.currentThread().getName().substring(14);
        
        if (!threadContainer.contains(String.format("Car %1s", threadInstance)))
        {
            threadContainer.add(String.format("Car %1s", threadInstance));
            return threadInstance;
        }
        else
        {
        }
        return threadInstance;
    }
    
    @Override
    public Integer call()
    {
        String
                threadNumber = "";
        threadNumber = atomic();
        return Integer.parseInt(threadNumber);
    }
}
