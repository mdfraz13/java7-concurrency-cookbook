/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import ro.tatacalu.java7concurrency.util.TCNumberUtils;
import ro.tatacalu.java7concurrency.util.TCStringUtils;

/**
 * @author Matei
 * 
 */
public class MyThreadFactory implements ThreadFactory {

    private static final String STRING_THREAD_NAME_MIDDLE = "-Thread_";
    private static final String STRING_STAT_FORMAT        = "Created thread %d with name %s on %s";

    private int                 counter;
    private String              name;
    private List<String>        statistics;

    public MyThreadFactory(String name) {
        this.counter = TCNumberUtils.INT_ZERO;
        this.name = name;
        this.statistics = new ArrayList<>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
     */
    @Override
    public Thread newThread(Runnable runnable) {

        String threadName = this.name + STRING_THREAD_NAME_MIDDLE + this.counter;
        Thread thread = new Thread(runnable, threadName);

        this.counter++;

        String currentStat = String.format(STRING_STAT_FORMAT, thread.getId(), thread.getName(), new Date());

        this.statistics.add(currentStat);

        return thread;
    }

    public String getStatistics() {
        StringBuilder builder = new StringBuilder();
        Iterator<String> it = this.statistics.iterator();

        while (it.hasNext()) {
            builder.append(it.next()).append(TCStringUtils.NL);
        }

        return builder.toString();
    }
}
