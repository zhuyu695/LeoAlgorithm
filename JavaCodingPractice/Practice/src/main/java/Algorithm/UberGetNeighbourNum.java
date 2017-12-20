package Algorithm;

/**
 * Given max number.
 * Find all numbers have neighbour digit with 1 difference:
 * like give:  200
 * return : 121, 123, 12, 21, 23, 32, 34, 43 ...
 */
public class UberGetNeighbourNum {
    public void getNum(int num) {
        for (int i=1; i<=9; ++i) {
            getNumber(num, i, i);
        }
    }

    public void getNumber(int num, int prev, int lastDigit) {
        if (prev > num) {
            return;
        }
        if (lastDigit > 0 && lastDigit < 9) {
            int cur = prev * 10 + lastDigit -1;
            if (cur <= num) {
                System.out.println(cur);
                getNumber(num, cur, lastDigit - 1);
            }
            cur = prev * 10 + lastDigit + 1;
            if (cur <= num) {
                System.out.println(cur);
                getNumber(num, cur, lastDigit + 1);
            }
        } else if (lastDigit == 0) {
            int cur = prev*10 + lastDigit+1;
            if (cur <= num) {
                System.out.println(cur);
                getNumber(num, cur, lastDigit + 1);
            }
        } else if (lastDigit == 9) {
            int cur = prev*10 + lastDigit-1;
            if (cur <= num) {
                System.out.println(cur);
                getNumber(num, cur, lastDigit - 1);
            }
        }
    }
}
