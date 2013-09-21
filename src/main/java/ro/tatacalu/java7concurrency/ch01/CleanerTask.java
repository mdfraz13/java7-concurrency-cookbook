/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

import java.util.Date;
import java.util.Deque;

import ro.tatacalu.java7concurrency.util.TCNumberUtils;

/**
 * @author Matei
 * 
 */
public class CleanerTask extends Thread {

    private static final long   LONG_DIFFERENCE_THRESHOLD        = 10_000L;
    private static final String STRING_CLEANER_REMOVE_FORMAT     = "Cleaner: %s\n";
    private static final String STRING_CLEANER_DEQUE_SIZE_FORMAT = "Cleaner: Size of the queue: %d\n";

    private Deque<Event>        deque;

    public CleanerTask(Deque<Event> deque) {
        this.deque = deque;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            this.clean(new Date());
        }
    }

    private void clean(Date date) {
        long difference;
        boolean delete;

        if (this.deque.size() == TCNumberUtils.INT_ZERO) {
            return;
        }

        delete = false;

        do {
            Event event = this.deque.getLast();
            difference = date.getTime() - event.getDate().getTime();

            if (difference > LONG_DIFFERENCE_THRESHOLD) {
                System.out.printf(STRING_CLEANER_REMOVE_FORMAT,
                        event.getDescription());
                this.deque.removeLast();
                delete = true;
            }

        } while (difference > LONG_DIFFERENCE_THRESHOLD);

        if (delete) {
            System.out.printf(STRING_CLEANER_DEQUE_SIZE_FORMAT,
                    this.deque.size());
        }
    }
}
