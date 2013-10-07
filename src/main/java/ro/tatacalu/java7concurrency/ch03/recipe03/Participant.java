/**
 * 
 */
package ro.tatacalu.java7concurrency.ch03.recipe03;

import java.util.concurrent.TimeUnit;

/**
 * @author tatacalu
 * 
 */
public class Participant implements Runnable {

    private Videoconference conference;
    private String          name;

    public Participant(Videoconference conference, String name) {
        this.conference = conference;
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        long duration = (long) (Math.random() * 10);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // the .arrive() method will trigger a decrement in the CountDownLatch
        this.conference.arrive(this.name);
    }

}
