package Utils;

import java.util.Random;

public class rand {
    static Random rnd = new Random();
    // min 0 max 3 числа 0 1 2
    public static int getIntRnd(int min, int minmax) {
        return min + rnd.nextInt(minmax);
    }

    public static float getFloatRnd() {
        return rnd.nextFloat();
    }
}
