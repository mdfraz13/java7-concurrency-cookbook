package ro.tatacalu.java7concurrency.ch01;

import java.util.concurrent.TimeUnit;

public class MainInterruptedException {

    public static void main(String[] args) {
        
        FileSearch fileSearch = new FileSearch("C:\\", "pagefile.sys");
        Thread thread = new Thread(fileSearch);
        thread.start();
        
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        
        thread.interrupt();
    }

}
