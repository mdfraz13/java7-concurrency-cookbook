/**
 * 
 */
package ro.tatacalu.java7concurrency.ch03.recipe04;

import java.util.Random;

/**
 * @author tatacalu
 * 
 */
public class MatrixMock {

    private int data[][];

    /**
     * @param size
     *            the number of rows in the matrix
     * @param length
     *            the length of each row
     * @param number
     *            the number to search for
     */
    public MatrixMock(int size, int length, int number) {
        int counter = 0;
        this.data = new int[size][length];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < length; j++) {
                this.data[i][j] = random.nextInt(10);
                if (this.data[i][j] == number) {
                    counter++;
                }
            }
        }

        System.out.printf("Mock: There are %d ocurrences of number in generated data.\n", counter, number);
    }

    public int[] getRow(int row) {
        if ((row >= 0) && (row < data.length)) {
            return data[row];
        }
        return null;
    }
}
