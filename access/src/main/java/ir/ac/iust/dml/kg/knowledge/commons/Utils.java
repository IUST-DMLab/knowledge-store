package ir.ac.iust.dml.kg.knowledge.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by HosseiN on 08/04/2017.
 */
public class Utils {
    public static final Random randomGenerator = new Random(System
            .currentTimeMillis());

    /**
     * Generate random unique array of 0 to max - 1
     *
     * @param count size of array
     * @param max   max of generated
     * @return
     */
    public static int[] randomIndex(int count, int max) {
        List<Integer> indexes = new ArrayList<>(max);
        for (int i = 0; i < max; i++)
            indexes.add(i);
        Collections.shuffle(indexes, randomGenerator);
        int[] result = new int[count < max ? count : max];
        for (int i = 0; i < result.length; i++)
            result[i] = indexes.get(i);
        return result;
    }
}
