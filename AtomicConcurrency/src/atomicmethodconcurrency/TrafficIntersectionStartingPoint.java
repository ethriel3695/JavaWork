/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atomicmethodconcurrency;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
/**
 *
 * @author ethri
 */
public class TrafficIntersectionStartingPoint implements Callable<Integer> {
    
    public int atomic()
    {
        int streetChoice = ThreadLocalRandom.current().nextInt(1, 5);
        
        for (int i = 1; i <= streetChoice; i++)
        {
            System.out.println("Car " + 
                Thread.currentThread().getName().substring(14) + 
                " begins at lane: " + i);
        }
        return streetChoice;
    }
    
    @Override
    public Integer call()
    {
        atomic();
        return atomic();
    }
}
