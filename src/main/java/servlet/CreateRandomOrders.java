package servlet;

import utils.MTT_CONSTANTS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by govardhanreddy on 1/31/16.
 */
public class CreateRandomOrders {

    private static String declarationLine = "    private static List<Integer> order%s = new ArrayList<Integer>() {{";
    private static String closingLine = "    }};\n";

    public static void main(String[] args) {
        for (int i = 0; i < MTT_CONSTANTS.NUMBER_OF_SETS; i++) {
            String declaration = String.format(declarationLine, String.valueOf(i + 1));
            List<Integer> order = generateRandomOrder(MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016);
            System.out.println(declaration);
            String midLine = "        ";
            for (Integer num : order) {
                midLine += "add(" + num + ");";
            }
            System.out.println(midLine);
            System.out.println(closingLine);
        }
    }

    private static List<Integer> generateRandomOrder(int n) {
        List<Integer> result = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) {
            int cur = 0;
            do {
                cur = 1 + (int) (Math.random() * n);
//                System.out.println("Current: " + cur);
            } while (result.contains(cur));
            result.add(cur);
        }
        return result;
    }
}
