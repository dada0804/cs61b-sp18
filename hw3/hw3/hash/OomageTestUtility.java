package hw3.hash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int bucketNum;
        HashMap<Integer, Integer> buckets = new HashMap<>();
        for (int i = 0; i < M; i ++){
            buckets.put(i, 0);
        }
        for (Oomage o : oomages){
            bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            int count = buckets.get(bucketNum);
            buckets.put(bucketNum, count + 1);
        }
        for (int c :buckets.values()){
            if (c < oomages.size() / 50 || c > oomages.size() / 2.5){
                return false;
            }
        }


        return true;
    }
}
