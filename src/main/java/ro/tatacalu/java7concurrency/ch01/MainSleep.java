package ro.tatacalu.java7concurrency.ch01;

import java.util.concurrent.TimeUnit;

public class MainSleep {
    
    private static final long LONG_MAIN_SLEEP_PERIOD = 5L;
    
    public static void main(String[] args) {
        FileClock fileClock = new FileClock();
        Thread thread = new Thread(fileClock);
        thread.start();
        
        try {
            TimeUnit.SECONDS.sleep(LONG_MAIN_SLEEP_PERIOD);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        thread.interrupt();
    }

}
