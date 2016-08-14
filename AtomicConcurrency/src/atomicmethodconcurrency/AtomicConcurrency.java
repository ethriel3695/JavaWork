/*
 * @author Reuben Ellis
 * Date: 2016-08-11
 * This program demonstrates two threads invoking a method concurrently
 * for class PRG/421
 * An ArrayList of type Integer is created to hold the random starting streets
 * for a car
 
 * In the main method, four instances are created for the 
 * TrafficIntersectionStartingPoint class which is a Callable thread of type 
 * Integer
 * Future is then used to initiate each thread and submit a call to the class
 * which returns the thread name
 * A try-catch statement is then initiated to catch and Exception that may
 * happen if there is an error with executing the threads or if the thread is
 * interrupted
 * An ArrayList of type Integer then adds each thread name to the list for use
 * with accessing the ConcurrentHashMap for starting street values
 * The next step within the main method is to initiate the randomStreet method

 * The randomStreet method contains a for loop which iterates four times, one
 * for each thread
 * The streetChoice int variable equals a ThreadLocalRandom value between 
 * 1 and 4
 * A check is in place to determine if the threadIndex ArrayList contains one
 * of the random values already to ensure each thread gets a unique street to 
 * start on using an if statement and then a while loop which calls the random
 * action until a unique value is found
 * Once the statement is successful, the user receives a message indicating
 * which intersection each car begins at and the thread name and intersection 
 * number are placed within a ConcurrentHashMap for use in the main method

 * Back in the main method, a variable receives the starting intersection for 
 * each thread and another random value is initiated
 * The random value is checked against the current intersection for the current
 * thread and a while loop is in place to continue getting a new value as
 * long as the random value matches the current intersection value
 * Once the random value is different than the intersection value, a check is in
 * place for each thread besides thread 1
 * If the thread is thread 1, a message is displayed to the user indicating
 * which intersection the thread is driving towards
 * For thread 2, 3, and 4, a check is in place to indicate if the previous
 * thread is moving from a conflicting intersection
 * For example, if car 1 is in intersection 2 and heading to intersection 1
 * and car 2 is intersection 3 and heading to intersection 1, then car 2 must
 * wait with the use of a Thread.sleep(6000) before driving to the intersection
 * Each thread besides thread one is checked in this manner and if cars are
 * travelling parallel such as car 1 is in intersection 1 and going to 3 and
 * car 2 is in intersection 3 and going to 1 then the cars can go with no issues
 * but if cars must turn then the current car must wait for the previous car 
 * to finish crossing the intersection
 * Once all four threads are complete, the shutdownThreads method is called

 * In the shutdownThreads method, a try-catch-finally block is in place to make
 * sure the thread is not interrupted if the shutdown process occurs
 * In the finally portion of the block, if the thread is not terminated, then 
 * the shutdownNow method of the the ExecutorService is called to shut each
 * thread down immediately
 */

package atomicmethodconcurrency;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.ThreadFactory;

public class AtomicConcurrency {

        
    private static ArrayList<Integer>
            threadIndex = new ArrayList<Integer>();
    
    private static int
            streetChoice = 0,
            keyValue = 0;
    
    private static ArrayList<Integer>
            streetPosition = new ArrayList<Integer>();
    
    private static ConcurrentHashMap
            carInfo = new ConcurrentHashMap();
    
    private static ExecutorService
            streetLocation = Executors.newFixedThreadPool(4);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws TimeoutException {
        
        Callable<Integer> streetStart1 = new TrafficIntersectionStartingPoint();
        Callable<Integer> streetStart2 = new TrafficIntersectionStartingPoint();
        Callable<Integer> streetStart3 = new TrafficIntersectionStartingPoint();
        Callable<Integer> streetStart4 = new TrafficIntersectionStartingPoint();
        
        Future<Integer> futureStreet1 = streetLocation.submit(streetStart1);
        Future<Integer> futureStreet2 = streetLocation.submit(streetStart2);
        Future<Integer> futureStreet3 = streetLocation.submit(streetStart3);
        Future<Integer> futureStreet4 = streetLocation.submit(streetStart4);

        try {
            
            streetPosition.add(futureStreet1.get());
            streetPosition.add(futureStreet2.get());
            streetPosition.add(futureStreet3.get());
            streetPosition.add(futureStreet4.get());
            
            randomStreet();
            
            for (int i = 0; i < 4; i += 1)
            {
                int
                    key1 = (int) carInfo.get(streetPosition.get(0)),
                    key2 = (int) carInfo.get(streetPosition.get(1)),
                    key3 = (int) carInfo.get(streetPosition.get(2)),
                    key4 = (int) carInfo.get(streetPosition.get(3)),
                    currentKey = 0,
                    carNumber = 0;
                
                streetChoice  = ThreadLocalRandom.current().nextInt(1, 5);
                
                keyValue = (int) carInfo.get(streetPosition.get(i));
            
                if (keyValue == streetChoice)
                {
                    while (keyValue == streetChoice)
                    {
                        streetChoice = ThreadLocalRandom.current().nextInt(1, 5);
                    }
                }
                else
                {
                }
                if (i != 0)
                {
                    if (i == 1)
                    {
                        currentKey = key1;
                        carNumber = 0;
                    }
                    else if (i == 2)
                    {
                        currentKey = key2;
                        carNumber = 1;
                    }
                    else if (i == 3)
                    {
                        currentKey = key3;
                        carNumber = 2;
                    }
                    
                    if ((currentKey == 1 || currentKey == 3) && 
                            (streetChoice == 3 || streetChoice == 1))
                    {
                        System.out.println(String.format("Car %1s drives to"
                            + " intersection%2s", streetPosition.get(i),
                            streetChoice));
                    }
                    else if ((currentKey == 1 || currentKey == 3) && 
                            (streetChoice == 2 || streetChoice == 4))
                    {
                        System.out.println(String.format("Car %1s must"
                        + " wait for Car%2s", streetPosition.get(i),
                        streetPosition.get(carNumber)));
                        
                        Thread.sleep(6000);
                        
                        System.out.println(String.format("Car %1s drives to"
                            + " intersection%2s", streetPosition.get(i),
                            streetChoice));
                    }
                    else if ((currentKey == 2 || currentKey == 4) && 
                            (streetChoice == 2 || streetChoice == 4))
                    {
                        System.out.println(String.format("Car %1s drives to"
                            + " intersection%2s", streetPosition.get(i),
                            streetChoice));
                    }
                    else if ((currentKey == 2 || currentKey == 4) && 
                        (streetChoice == 1 || streetChoice == 3))
                    {
                        System.out.println(String.format("Car %1s must"
                        + " wait for Car%2s", streetPosition.get(i),
                        streetPosition.get(carNumber)));
                        
                        Thread.sleep(6000);
                        
                        System.out.println(String.format("Car %1s drives to"
                            + " intersection%2s", streetPosition.get(i),
                            streetChoice));
                    }
                }
                else
                {
                    System.out.println(String.format("\nCar %1s drives to"
                        + " intersection%2s", streetPosition.get(i),
                        streetChoice));
                }

            }
            
        }catch (InterruptedException | ExecutionException iex)
        {
            System.out.println("Failed");
        }
        shutdownThreads();
    }
    
        
    private static void randomStreet()
    {
        for (int i = 0; i < 4; i += 1)
        {
            streetChoice = ThreadLocalRandom.current().nextInt(1, 5);

            if (threadIndex.contains(streetChoice))
            {
                while (threadIndex.contains(streetChoice))
                {
                    streetChoice = ThreadLocalRandom.current().nextInt(1, 5);
                }
            }
            else
            {
                threadIndex.add(streetChoice); 
            }
                System.out.println(String.format("Car %1s begins at"
                    + " intersection%2s", streetPosition.get(i),
                    streetChoice));

                carInfo.put(streetPosition.get(i), streetChoice);
        }
    }
    
    private static void shutdownThreads()
    {
        try {
            boolean terminateStreetStart = 
                    streetLocation.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException ex1)
        {
            System.out.println("Did not wait 2 seconds");
        } finally
        {
            if (!streetLocation.isTerminated())
            {
                List<Runnable> streetAssignments = streetLocation.shutdownNow();
            }
        }
    }
}
