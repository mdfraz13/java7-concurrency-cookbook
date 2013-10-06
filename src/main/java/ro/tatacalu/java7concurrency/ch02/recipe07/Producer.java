/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe07;

/**
 * @author tatacalu
 * 
 */
public class Producer implements Runnable {

    private FileMock mock;
    private Buffer   buffer;

    public Producer(FileMock mock, Buffer buffer) {
        this.mock = mock;
        this.buffer = buffer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        this.buffer.setPendingLines(true);
        while (this.mock.hasMoreLines()) {
            String line = this.mock.getLine();
            this.buffer.insert(line);
        }
        this.buffer.setPendingLines(false);
    }

}
