package lesson2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MyArray {


    public static String[][] strings(int x, int y) throws MyArraySizeException {
        if (x != 4 || y != 4)
            throw new MyArraySizeException("Change size of Array to [4][4].");
        return new String[x][y];
    }

    public static int[][] ints(String[][] strings) {
        int[][] q = new int[strings.length][strings[0].length];
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
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[i].length; j++) {
                q[i][j] = Integer.parseInt(strings[i][j]);
            }
        }
        return q;
    }

    public static void main(String[] args) {
        int[][] qwerty;
        int sum = 0;
        try {
            qwerty = ints(strings(4, 4));
            for (int[] ints : qwerty) {
                for (int iints : ints) {
                    sum += iints;
                }
            }
            System.out.println(sum);
        } catch (MyArraySizeException e) {
            e.printStackTrace();
        }catch (NumberFormatException e){
            throw new MyArrayDataException("Your Array contains a value that is different from int.");
        }
    }
}
