package ro.tatacalu.java7concurrency.ch01;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.State;

public class MainThreadDetails {

    private static final int I_START = 0;
    private static final int I_END = 10;
    private static final int INT_ZERO = 0;
    
    private static final String STRING_THREAD_PREFIX = "Thread ";
    private static final String STRING_FILE_PATH = "log-mainthreaddetails.txt";
    
    public static void main(String[] args) {
        
        Thread threads[] = new Thread[10];
        State status[] = new State[10];
        
        for (int i = I_START; i < I_END; i++) {
            threads[i] = new Thread(new Calculator(i));
            
            if (i % 2 == INT_ZERO) {
                threads[i].setPriority(Thread.MAX_PRIORITY);
            } else {
                threads[i].setPriority(Thread.MIN_PRIORITY);
            }
            
            threads[i].setName(STRING_THREAD_PREFIX + i);
        }
        
        // JDK7 try-with-resources
        try (FileWriter fileWriter = new FileWriter(STRING_FILE_PATH); 
                PrintWriter printWriter = new PrintWriter(fileWriter);) {
            
            // get the initial status of the threads
            for (int i = I_START; i < I_END; i++) {
                status[i] = threads[i].getState();
                printWriter.println("Main: Status of Thread " + i + ": " + status[i]);
            }
            
            // start the threads
            for (int i = I_START; i < I_END; i++) {
                threads[i].start();
            }
            
            // detect status changes and write them into the output file
            boolean finish = false;
            while (!finish) {
                
                // check for thread state changes
                for (int i = I_START; i < I_END; i++) {
                    if (threads[i].getState() != status[i]) {
                        // thread status change detected
                        writeThreadInfo(printWriter, threads[i], status[i]);
                        status[i] = threads[i].getState();
                    }
                }
                
                // check if all the threads have finished
                finish = true;
                for (int i = I_START; i < I_END; i++) {
                    finish = finish && (threads[i].getState() == State.TERMINATED);
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * [Captain Obvious] Writes thread information to a PrintWriter.
     * 
     * @param printWriter The ProntWriter to write to.
     * @param thread The thread from which to extract information.
     * @param oldState The thread's previous state.
     */
    private static void writeThreadInfo(PrintWriter printWriter, Thread thread, State oldState) {
        printWriter.printf("Main: Id %d - %s\n", thread.getId(), thread.getName());
        printWriter.printf("Main: Priority: %d\n", thread.getPriority());
        printWriter.printf("Main: Old State: %s\n", oldState);
        printWriter.printf("Main: New State: %s\n", thread.getState());
        printWriter.printf("Main: *********************************\n");
    }
}
