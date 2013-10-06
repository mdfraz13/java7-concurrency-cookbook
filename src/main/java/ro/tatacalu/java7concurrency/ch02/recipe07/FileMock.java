/**
 * 
 */
package ro.tatacalu.java7concurrency.ch02.recipe07;

import ro.tatacalu.java7concurrency.util.TCNumberUtils;

/**
 * @author tatacalu
 * 
 */
public class FileMock {

    private String content[];
    private int    index;

    /**
     * Constructs a new FileMock instance.
     * 
     * @param size
     *            The size of the content array.
     * @param length
     *            the lengh of each line stored inside the content array.
     */
    public FileMock(int size, int length) {
        this.content = new String[size];

        for (int i = TCNumberUtils.INT_ZERO; i < size; i++) {
            StringBuilder builder = new StringBuilder(size);

            for (int j = TCNumberUtils.INT_ZERO; j < length; j++) {
                int randomChar = (int) (Math.random() * TCNumberUtils.INT_255);
                builder.append((char) randomChar);
            }

            content[i] = builder.toString();
        }

        this.index = TCNumberUtils.INT_ZERO;
    }

    public boolean hasMoreLines() {
        return this.index < this.content.length;
    }

    public String getLine() {
        if (this.hasMoreLines()) {
            System.out.println("Mock: " + (this.content.length - this.index));
            return content[index++];
        }
        return null;
    }
}
