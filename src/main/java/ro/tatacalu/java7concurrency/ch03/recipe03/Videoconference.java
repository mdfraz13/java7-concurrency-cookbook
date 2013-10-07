/**
 * 
 */
package ro.tatacalu.java7concurrency.ch03.recipe03;

import java.util.concurrent.CountDownLatch;

/**
 * @author tatacalu
 * 
 */
public class Videoconference implements Runnable {

    private final CountDownLatch controller;

    public Videoconference(int number) {
        this.controller = new CountDownLatch(number);
    }

    public void arrive(String name) {
        System.out.printf("%s has arrived.", name);

        this.controller.countDown();

        System.out.printf("VideoConference: Waiting for %d participants.\n", controller.getCount());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        System.out.printf("VideoConference: Initialization: %d participants.\n", controller.getCount());

        try {
            // put the thread to sleep until the countdown has reached zero
            this.controller.await();

            // At this point the thread has woke up - the counter has reached 0
            System.out.printf("VideoConference: All the participants have come\n");
            System.out.printf("VideoConference: Let's start...\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
