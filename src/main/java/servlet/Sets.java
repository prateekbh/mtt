package servlet;

import utils.MTT_CONSTANTS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by govardhanreddy on 1/31/16.
 */
public class Sets {

    private static ArrayList<Integer> order1 = new ArrayList<Integer>() {{
        add(4);add(11);add(8);add(6);add(5);add(1);add(7);add(3);add(12);add(10);add(2);add(9);
    }};

    private static ArrayList<Integer> order2 = new ArrayList<Integer>() {{
        add(4);add(2);add(5);add(9);add(8);add(6);add(10);add(11);add(12);add(3);add(1);add(7);
    }};

    private static ArrayList<Integer> order3 = new ArrayList<Integer>() {{
        add(1);add(7);add(2);add(6);add(5);add(8);add(3);add(9);add(10);add(11);add(4);add(12);
    }};

    private static ArrayList<Integer> order4 = new ArrayList<Integer>() {{
        add(6);add(8);add(11);add(7);add(2);add(3);add(1);add(12);add(5);add(9);add(4);add(10);
    }};

    private static ArrayList<Integer> order5 = new ArrayList<Integer>() {{
        add(5);add(6);add(1);add(9);add(3);add(8);add(2);add(10);add(7);add(11);add(4);add(12);
    }};

    private static ArrayList<Integer> order6 = new ArrayList<Integer>() {{
        add(8);add(5);add(2);add(7);add(3);add(11);add(9);add(6);add(4);add(10);add(1);add(12);
    }};

    private static ArrayList<Integer> order7 = new ArrayList<Integer>() {{
        add(5);add(8);add(3);add(9);add(6);add(1);add(10);add(2);add(12);add(4);add(7);add(11);
    }};

    private static ArrayList<Integer> order8 = new ArrayList<Integer>() {{
        add(11);add(5);add(7);add(10);add(8);add(6);add(1);add(12);add(4);add(2);add(9);add(3);
    }};

    private static ArrayList<Integer> order9 = new ArrayList<Integer>() {{
        add(9);add(4);add(1);add(12);add(2);add(5);add(10);add(11);add(3);add(7);add(6);add(8);
    }};

    private static ArrayList<Integer> order10 = new ArrayList<Integer>() {{
        add(6);add(9);add(8);add(10);add(2);add(3);add(11);add(12);add(7);add(1);add(4);add(5);
    }};

    /*******************************************************************************************/
    private static HashMap<Integer, ArrayList<Integer>> codeToOrderMap = new HashMap<Integer, ArrayList<Integer>>() {{
        put(1, order1);
        put(2, order2);
        put(3, order3);
        put(4, order4);
        put(5, order5);
        put(6, order6);
        put(7, order7);
        put(8, order8);
        put(9, order9);
        put(10, order10);
    }};


    public static ArrayList<Integer> getOrderForCode(int code) {
        return codeToOrderMap.get(code);
    }

    public static final String[] set0 = new String[] {
            "Question 1",
            "Question 2",
            "Question 3",
            "Question 4",
            "Question 5",
            "Question 6",
            "Question 7",
            "Question 8",
            "Question 9",
            "Question 10",
            "Question 11",
            "Question 12",
    };
    public static String[] getQuestionAnswersForCode(int code) {
        String questions[] = new String[MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016];
        ArrayList<Integer> order = getOrderForCode(code);
        System.out.println("code :  " + code + " order: " + order);
        for (int i = 0; i < MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016; i++) {
            System.out.println("order get i " + order.get(i));
            questions[i] = set0[order.get(i) - 1];  // since it is 1 based index
        }
        return questions;
    }
}
