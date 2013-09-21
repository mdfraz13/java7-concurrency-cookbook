/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

import java.util.Date;

/**
 * @author Matei
 *
 */
public class MainJoin {

    /**
     * @param args
     */
    public static void main(String[] args) {
        DataSourcesLoader dataSourcesLoader = new DataSourcesLoader();
        Thread dataSourcesLoaderThread = new Thread(dataSourcesLoader);
        
        NetworkConnectionsLoader networkConnectionsLoader = new NetworkConnectionsLoader();
        Thread networkConnectionsLoaderThread = new Thread(networkConnectionsLoader);
        
        dataSourcesLoaderThread.start();
        networkConnectionsLoaderThread.start();
        
        try {
            dataSourcesLoaderThread.join();
            networkConnectionsLoaderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.printf("Main configuration has been loaded: %s\n", new Date());
    }

}
