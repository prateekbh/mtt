package utils;

/**
 * Created by govardhanreddy on 2/6/16.
 */
public class ComputeStats {
    public static void main(String[] args) throws Exception {
        int ans = 0;
        for (int i = 1; i <= 99; i++) {
            ans += i * (i + 1);
        }



        System.out.println("ans for Q1 " + ans);
        System.out.println("Answer 2 : " + g1(50));
//        Utils.populateQuestionStats();
    }

    public static int f1(int n) {
        int c = 0;
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) c++;

        }
        return c;
    }

    public static int g1(int n) {
        int c = 0;
        for (int i = 1; i <= n; i++) c += f1(i);
        return c;
    }
}
