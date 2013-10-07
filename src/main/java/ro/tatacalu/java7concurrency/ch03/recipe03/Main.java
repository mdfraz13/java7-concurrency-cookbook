/**
 * 
 */
package ro.tatacalu.java7concurrency.ch03.recipe03;

/**
 * @author tatacalu
 * 
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // Videoconference thread
        Videoconference conference = new Videoconference(10);
        Thread threadConference = new Thread(conference);
        threadConference.start();

        // Participant threads
        for (int i = 0; i < 10; i++) {
            Participant p = new Participant(conference, "Participant " + i);
            Thread t = new Thread(p);
            t.start();
        }

    }

}
