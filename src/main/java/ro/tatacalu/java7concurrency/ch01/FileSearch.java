/**
 * 
 */
package ro.tatacalu.java7concurrency.ch01;

import java.io.File;

import ro.tatacalu.java7concurrency.util.TCNumberUtils;

/**
 * @author Matei
 * 
 */
public class FileSearch implements Runnable {

    private static final String STRING_FILE_OUTPUT_FORMAT = "%s : %s\n";
    private static final String STRING_SEARCH_INTERRUPTED = "%s: The search has been interrupted";
    
    /**
     * The root path from where to start the file search.
     */
    private String initialPath;

    /**
     * The file name to search for.
     */
    private String fileName;

    /**
     * @param initialPath
     *            The root path from where to start the file search.
     * @param fileName
     *            The file name to search for.
     */
    public FileSearch(String initialPath, String fileName) {
        super();
        this.initialPath = initialPath;
        this.fileName = fileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        File file = new File(this.initialPath);
        
        if (file.isDirectory()) {
            try {
                this.processDirectory(file);
            } catch (InterruptedException ie) {
                System.out.printf(STRING_SEARCH_INTERRUPTED, Thread.currentThread().getName());
            }
        }
    }
    
    /**
     * Evaluates whether the input file's name is equal to the one we are looking for.
     * 
     * @param file The input file.
     * @throws InterruptedException
     */
    private void processFile(File file) throws InterruptedException {
        
        if (file.getName().equals(this.fileName)) {
            System.out.printf(STRING_FILE_OUTPUT_FORMAT, Thread.currentThread().getName(), file.getAbsolutePath());
        }
        
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }
    
    private void processDirectory(File file) throws InterruptedException {
        
        File fileList[] = file.listFiles();
        
        if (fileList != null) {
            for (int i = TCNumberUtils.INT_ZERO; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    this.processDirectory(fileList[i]);
                } else {
                    this.processFile(fileList[i]);
                }
            }
        }
    }
}
