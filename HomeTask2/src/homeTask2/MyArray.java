package homeTask2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyArray {
    public static String[][] strings(int x, int y) throws MyArraySizeException {
        if (x != 4 || y != 4)
            throw new MyArraySizeException("Change size of Array to [4][4].");
        return new String[x][y];
    }

    public static int summary(String[][] strings) throws MyArrayDataException {
        int[][] q = new int[strings.length][strings[0].length];
        int sum = 0;
        int x = 0;
        int y = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[i].length; j++) {
                try {
                    strings[i][j] = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            for (int i = 0; i < strings.length; i++) {
                for (int j = 0; j < strings[i].length; j++) {
                    q[i][j] = Integer.parseInt(strings[i][j]);
                    sum += q[i][j];
                    x = i;
                    y = j;
                }
            }
        } catch (NumberFormatException e) {
            throw new MyArrayDataException("Your Array contains a value that is different from int. Index: [" + x + "][" + y + "].");
        }
        return sum;
    }

    public static void main(String[] args) {
        try {
            System.out.println(summary(strings(4, 4)));
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
    }
}
