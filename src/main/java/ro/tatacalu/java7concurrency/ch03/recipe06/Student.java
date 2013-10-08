/**
 * 
 */
package ro.tatacalu.java7concurrency.ch03.recipe06;

import java.util.Date;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @author tatacalu
 * 
 */
public class Student implements Runnable {

    private Phaser phaser;

    public Student(Phaser phaser) {
        this.phaser = phaser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        System.out.printf("%s: Has arrived to do the exam. %s\n", Thread.currentThread().getName(), new Date());
        this.phaser.arriveAndAwaitAdvance();
        // ---------- Arrive & Await Advance ----------

        System.out.printf("%s: Is going to do the first exercise. %s\n", Thread.currentThread().getName(), new Date());
        this.doExercise1();
        System.out.printf("%s: Has done the first exercise. %s\n", Thread.currentThread().getName(), new Date());
        phaser.arriveAndAwaitAdvance();
        // ---------- Arrive & Await Advance ----------

        System.out.printf("%s: Is going to do the second exercise.%s\n", Thread.currentThread().getName(), new Date());
        this.doExercise2();
        System.out.printf("%s: Has done the second exercise. %s\n", Thread.currentThread().getName(), new Date());
        phaser.arriveAndAwaitAdvance();
        // ---------- Arrive & Await Advance ----------

        System.out.printf("%s: Is going to do the third exercise. %s\n", Thread.currentThread().getName(), new Date());
        this.doExercise3();
        System.out.printf("%s: Has finished the exam. %s\n", Thread.currentThread().getName(), new Date());
        phaser.arriveAndAwaitAdvance();
        // ---------- Arrive & Await Advance ----------
    }

    private void doExercise1() {
        try {
            long duration = (long) (Math.random() * 10);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doExercise2() {
        try {
            long duration = (long) (Math.random() * 10);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doExercise3() {
        try {
            long duration = (long) (Math.random() * 10);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
