package ro.tatacalu.java7concurrency.ch02.recipe03;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ro.tatacalu.java7concurrency.util.TCNumberUtils;

public class EventStorage {

    private static final int INT_DEFAULT_MAX_SIZE = 10;
    
    private int maxSize;
    private List<Date> storage;
    
    public EventStorage() {
        this.maxSize = INT_DEFAULT_MAX_SIZE;
        this.storage = new LinkedList<>();
    }
    
    /**
     * Stores a new event inside the storage.
     */
    public synchronized void set() {
        
        while (this.storage.size() == this.maxSize) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // add a new Date object via 
        ((LinkedList<Date>) this.storage).offer(new Date());
        System.out.printf("Set: Storage size: %d\n", this.storage.size());
        
        // wake up all threads that are waiting on this object's monitor
        this.notifyAll();
    }
    
    public synchronized void get() {
        
        while (this.storage.size() == TCNumberUtils.INT_ZERO) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // retrieve the head of the list
        Date date = ((LinkedList<Date>) this.storage).poll();
        
        System.out.printf("Get: Storage size: %d, element: %s\n", this.storage.size(), date);
        
        // wake up all threads that are waiting on this object's monitor
        this.notifyAll();
    }
}
