package Array_Generator;

import java.util.Random;

public class ArrayGenerator {

    public static int[] generateArray(int size) {
        if(size <= 0) {
            throw new IllegalArgumentException("Array size must be > 0");
        }
        else {
            int[] array = new int[size];
            Random rand = new Random();
            for (int i = 0; i < size; i++) {
                array[i] = rand.nextInt(size * 10) + 1;  // values from 1 to size*10
            }
            return array;
        }
    }
}
