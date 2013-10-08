/**
 * 
 */
package ro.tatacalu.java7concurrency.ch03.recipe04;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author tatacalu
 * 
 */
public class Searcher implements Runnable {

    private int                 firstRow;
    private int                 lastRow;

    private MatrixMock          mock;
    private Results             results;

    private int                 number;

    private final CyclicBarrier barrier;

    public Searcher(int firstRow, int lastRow, MatrixMock mock, Results results, int number, CyclicBarrier barrier) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.mock = mock;
        this.results = results;
        this.number = number;
        this.barrier = barrier;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        int counter;

        System.out.printf("%s: Processing lines from %d to %d.\n", Thread.currentThread().getName(), firstRow, lastRow);

        for (int i = this.firstRow; i < this.lastRow; i++) {
            int row[] = this.mock.getRow(i);
            counter = 0;
            for (int j = 0; j < row.length; j++) {
                if (row[j] == this.number) {
                    counter++;
                }
            }
            this.results.setData(i, counter);
        }

        System.out.printf("%s: Lines processed.\n", Thread.currentThread().getName());

        try {
            // put this thread to sleep until all the other threads at the barrier finished
            this.barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

}
