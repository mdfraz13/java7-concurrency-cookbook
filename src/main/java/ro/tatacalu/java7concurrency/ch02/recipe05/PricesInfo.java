/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe05;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Matei
 *
 */
public class PricesInfo {
    
    private static final double DOUBLE_INITIAL_PRICE1 = 1.0D;
    private static final double DOUBLE_INITIAL_PRICE2 = 2.0D;
    
    private double price1;
    private double price2;
    
    private ReadWriteLock readWriteLock;
    
    public PricesInfo() {
        this.price1 = DOUBLE_INITIAL_PRICE1;
        this.price2 = DOUBLE_INITIAL_PRICE2;
        
        this.readWriteLock = new ReentrantReadWriteLock();
    }
    
    public double getPrice1() {
        
        this.readWriteLock.readLock().lock();
        double value = this.price1;
        this.readWriteLock.readLock().unlock();
        
        return value;
    }
    
    public double getPrice2() {
        
        this.readWriteLock.readLock().lock();
        double value = this.price2;
        this.readWriteLock.readLock().unlock();
        
        return value;
    }
    
    public void setPrices(double price1, double price2) {
        
        this.readWriteLock.writeLock().lock();
        
        this.price1 = price1;
        this.price2 = price2;
        
        this.readWriteLock.writeLock().unlock();
    }
}
