package Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given {{2,3,4},{1,3,5}}
 * Return {1,2,3,4,5}
 * @author Leon
 *
 */
public class InterleaveArrayUnion {
	public List<Integer> getUnion(int[][] matrix) {
        List<Integer> res = new ArrayList<Integer>();
        Map<Integer, Integer> tracker = new HashMap<Integer, Integer>();
        int len = 0;
        for (int i=0; i<matrix.length; ++i) {
            Arrays.sort(matrix[i]);
            tracker.put(i, 0);
            len += matrix[i].length;
        }
        while(res.size() < len) {
            int min = Integer.MAX_VALUE;
            int index = -1;
            for (int i=0; i<matrix.length; ++i) {
                int curInd = tracker.get(i);
                if (curInd == matrix[i].length) {
                    continue;
                }
                if (min > matrix[i][curInd]) {
                    index = i;
                    min = matrix[i][curInd];
                }
            }
            if (index == -1) {
                break;
            }
            if (res.size() >0 && min == res.get(res.size()-1)) {
                tracker.put(index, tracker.get(index)+1);
                continue;
            }
            res.add(min);
            tracker.put(index, tracker.get(index)+1);
        }
        return res;
    }
}
