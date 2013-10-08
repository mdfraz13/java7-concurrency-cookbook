/**
 * 
 */
package ro.tatacalu.java7concurrency.ch03.recipe05;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @author tatacalu
 * 
 */
public class FileSearch implements Runnable {

    private String       initPath;

    /**
     * The extension searched for.
     */
    private String       end;

    private Phaser       phaser;

    private List<String> results;

    public FileSearch(String initPath, String end, Phaser phaser) {
        this.initPath = initPath;
        this.end = end;
        this.phaser = phaser;
        results = new ArrayList<>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        // The search won't begin until all the threads have been created
        // ---------- Arrive & Await Advance ----------
        this.phaser.arriveAndAwaitAdvance();

        System.out.printf("%s: Starting.\n", Thread.currentThread().getName());

        // Start processing the directory
        File file = new File(initPath);
        if (file.isDirectory()) {
            this.directoryProcess(file);
        }

        // If there are no results finish the execution of the thread
        if (!this.checkResults()) {
            return;
        }
        // ---------- Arrive & Await Advance ----------

        this.filterResults();
        // If there are no results finish the execution of the thread
        if (!this.checkResults()) {
            return;
        }
        // ---------- Arrive & Await Advance ----------

        this.showInfo();
        // ---------- Arrive & Await Advance ----------

        phaser.arriveAndDeregister();
        System.out.printf("%s: Work completed.\n", Thread.currentThread().getName());
    }

    private void directoryProcess(File file) {
        File list[] = file.listFiles();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    this.directoryProcess(list[i]);
                } else {
                    this.fileProcess(list[i]);
                }
            }
        }
    }

    private void fileProcess(File file) {
        if (file.getName().endsWith(end)) {
            this.results.add(file.getAbsolutePath());
        }
    }

    /**
     * Filter the files modified more than 24 hours ago.
     */
    private void filterResults() {
        List<String> newResults = new ArrayList<>();
        long actualDate = new Date().getTime();

        for (int i = 0; i < results.size(); i++) {
            File file = new File(results.get(i));
            long fileDate = file.lastModified();
            if (actualDate - fileDate < TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)) {
                newResults.add(results.get(i));
            }
        }
        results = newResults;
    }

    /**
     * This method will be called at the end of the first and the second phase
     * and it will check if the results list is empty or not.
     * 
     * @return true if the list is NOT empty or false otherwise
     */
    private boolean checkResults() {
        if (results.isEmpty()) {
            System.out.printf("%s: Phase %d: 0 results.\n", Thread.currentThread().getName(), phaser.getPhase());
            System.out.printf("%s: Phase %d: End.\n", Thread.currentThread().getName(), phaser.getPhase());
            phaser.arriveAndDeregister();
            return false;
        } else {
            System.out.printf("%s: Phase %d: %d results.\n", Thread.currentThread().getName(), phaser.getPhase(), results.size());
            phaser.arriveAndAwaitAdvance();
            return true;
        }
    }

    private void showInfo() {
        for (int i = 0; i < results.size(); i++) {
            File file = new File(results.get(i));
            System.out.printf("%s: %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
        }
        phaser.arriveAndAwaitAdvance();
    }
}
