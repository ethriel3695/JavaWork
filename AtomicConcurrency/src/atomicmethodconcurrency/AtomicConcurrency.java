package atomicmethodconcurrency;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.ThreadFactory;
/*
 * @author Reuben Ellis
 * Date: 2016-08-11
 * This program demonstrates two threads invoking a method concurrently
 * for class PRG/421
 * Write a Java program (non-GUI preferred) that has a method named atomic().
 * Demonstrate in the program how two threads can, sometimes, invoke atomic() concurrently.
 * Create a second version of the program in which the two threads cannot invoke atomic concurrently.
 * Submit both programs using the Assignment Files tab above.
 */

/**
 *
 * @author ethri
 */
public class AtomicConcurrency {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Callable<Integer> streetStart1 = new TrafficIntersectionStartingPoint();
        Callable<Integer> streetStart2 = new TrafficIntersectionStartingPoint();
        Callable<Integer> streetStart3 = new TrafficIntersectionStartingPoint();
        Callable<Integer> streetStart4 = new TrafficIntersectionStartingPoint();
        
        Callable<Integer> car1 = new TrafficIntersectionNavigation();
        Callable<Integer> car2 = new TrafficIntersectionNavigation();
        Callable<Integer> car3 = new TrafficIntersectionNavigation();
        Callable<Integer> car4 = new TrafficIntersectionNavigation();
       
        ExecutorService 
                executeCarAction = Executors.newFixedThreadPool(4);
        ExecutorService
                streetLocation = Executors.newFixedThreadPool(4);
        
        Future<Integer> futureStreet1 = streetLocation.submit(streetStart1);
        Future<Integer> futureStreet2 = streetLocation.submit(streetStart2);
        Future<Integer> futureStreet3 = streetLocation.submit(streetStart3);
        Future<Integer> futureStreet4 = streetLocation.submit(streetStart4);
        Future<Integer> futureAction1 = executeCarAction.submit(car1);
        Future<Integer> futureAction2 = executeCarAction.submit(car2);
        Future<Integer> futureAction3 = executeCarAction.submit(car3);
        Future<Integer> futureAction4 = executeCarAction.submit(car4);
        try {
            
            Integer streetPosition1 = futureStreet1.get();
            Integer streetPosition2 = futureStreet2.get();
            Integer streetPosition3 = futureStreet3.get();
            Integer streetPosition4 = futureStreet4.get();
            
            Integer street1 = futureAction1.get();
            Integer street2 = futureAction2.get();
            Integer street3 = futureAction3.get();
            Integer street4 = futureAction4.get();
            
            
            System.out.println(car1.toString() + "starts on street: " +
                    streetPosition1  + " and turns on street: " + street1);
        }catch (InterruptedException | ExecutionException iex)
        {
            System.out.println("Failed");
        }
        executeCarAction.shutdown();
        
        try {
            boolean terminateStreetNavigation = executeCarAction.awaitTermination(2, TimeUnit.SECONDS);
            boolean terminateStreetStart = streetLocation.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException ex1)
        {
            System.out.println("Did not wait 2 seconds");
        } finally
        {
            if (!executeCarAction.isTerminated() || !streetLocation.isTerminated())
            {
                List<Runnable> unfinished = executeCarAction.shutdownNow();
                List<Runnable> streetAssignments = streetLocation.shutdownNow();
            }
        }
                
    }
    
}
