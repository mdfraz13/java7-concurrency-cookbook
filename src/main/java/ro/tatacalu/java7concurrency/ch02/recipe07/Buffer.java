/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe07;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tatacalu
 * 
 */
public class Buffer {

    private LinkedList<String> buffer;
    private int                maxSize;
    private ReentrantLock      lock;
    private Condition          lines;
    private Condition          space;
    private boolean            pendingLines;

    public Buffer(int maxSize) {
        this.maxSize = maxSize;
        this.buffer = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.lines = lock.newCondition();
        this.space = lock.newCondition();
        this.pendingLines = true;
    }

    public void insert(String line) {
        this.lock.lock();

        try {
            while (this.buffer.size() == this.maxSize) {
                this.space.await();
            }
            this.buffer.offer(line);
            System.out.printf("%s: Inserted Line: %d\n", Thread.currentThread().getName(), this.buffer.size());
            this.lines.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    public String get() {
        String line = null;
        this.lock.lock();
        try {
            while ((this.buffer.size() == 0) && (this.hasPendingLines())) {
                this.lines.await();
            }
            if (this.hasPendingLines()) {
                line = this.buffer.poll();
                System.out.printf("%s: Line Readed: %d\n", Thread.currentThread().getName(), this.buffer.size());
                this.space.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
        return line;
    }

    public void setPendingLines(boolean pendingLines) {
        this.pendingLines = pendingLines;
    }

    public boolean hasPendingLines() {
        return this.pendingLines || this.buffer.size() > 0;
    }
}
