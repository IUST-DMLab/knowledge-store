package ir.ac.iust.dml.kg.knowledge.commons;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
    public static Set<Integer> randomIndex(int count, int max) {
        if (count >= max) {
            final Set<Integer> indexes = new HashSet<>(count);
            for (int i = 0; i < max; i++) indexes.add(i);
            return indexes;
        } else {
            final Set<Integer> indexes = new HashSet<>(count);
            while (indexes.size() < count)
                indexes.add(randomGenerator.nextInt(max));
            return indexes;
        }
    }
}
