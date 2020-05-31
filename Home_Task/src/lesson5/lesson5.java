package lesson5;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class lesson5 {
    static final int SIZE = 10000000;
    static final int HALF_SIZE = SIZE / 2;


    public static float[] array() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        return arr;
    }

    public static void calculateOneArray(float[] array) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long finish = System.currentTimeMillis();
        System.out.printf("The first method took: %d \n", (finish - start));

    }

    public static void calculateTwoArrays(float[] array) {
        long start = System.currentTimeMillis();
        float[] arr1 = new float[HALF_SIZE];
        float[] arr2 = new float[HALF_SIZE];
        System.arraycopy(array, 0, arr1, 0, HALF_SIZE);
        System.arraycopy(array, HALF_SIZE, arr2, 0, HALF_SIZE);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < arr1.length; i++) {
                    arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < arr2.length; i++) {
                    arr2[i] = (float) (arr2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(arr1, 0, array, 0, HALF_SIZE);
        System.arraycopy(arr2, 0, array, HALF_SIZE, HALF_SIZE);
        long finish = System.currentTimeMillis();
        System.out.printf("The second method took: %d \n", (finish - start));

    }

    public static void additionalMethod(float[] array) {
        long start = System.currentTimeMillis();
        float[] arr1 = new float[HALF_SIZE];
        float[] arr2 = new float[HALF_SIZE];
        System.arraycopy(array, 0, arr1, 0, HALF_SIZE);
        System.arraycopy(array, HALF_SIZE, arr2, 0, HALF_SIZE);
        ExecutorService service1 = Executors.newSingleThreadExecutor();
        service1.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < arr1.length; i++) {
                    arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        ExecutorService service2 = Executors.newSingleThreadExecutor();
        service2.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < arr2.length; i++) {
                    arr2[i] = (float) (arr2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        service1.shutdown();
        service2.shutdown();
        System.arraycopy(arr1, 0, array, 0, HALF_SIZE);
        System.arraycopy(arr2, 0, array, HALF_SIZE, HALF_SIZE);
        long finish = System.currentTimeMillis();
        System.out.printf("The third method took: %d \n", (finish - start));

    }

    public static void main(String[] args) {
        calculateOneArray(array());
        calculateTwoArrays(array());
        additionalMethod(array());
    }
}
