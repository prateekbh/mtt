package servlet;

import utils.MTT_CONSTANTS;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by govardhanreddy on 1/31/16.
 */
public class Sets {

    private static ArrayList<Integer> order1 = new ArrayList<Integer>() {{
        add(10);add(2);add(8);add(6);add(5);add(9);add(4);add(7);add(1);add(3);
    }};

    private static ArrayList<Integer> order2 = new ArrayList<Integer>() {{
        add(6);add(7);add(4);add(3);add(1);add(8);add(2);add(5);add(10);add(9);
    }};

    private static ArrayList<Integer> order3 = new ArrayList<Integer>() {{
        add(5);add(7);add(2);add(8);add(4);add(3);add(6);add(9);add(10);add(1);
    }};

    private static ArrayList<Integer> order4 = new ArrayList<Integer>() {{
        add(3);add(1);add(5);add(2);add(6);add(8);add(4);add(7);add(9);add(10);
    }};

    private static ArrayList<Integer> order5 = new ArrayList<Integer>() {{
        add(4);add(8);add(7);add(1);add(2);add(6);add(10);add(3);add(5);add(9);
    }};

    private static ArrayList<Integer> order6 = new ArrayList<Integer>() {{
        add(1);add(7);add(6);add(8);add(5);add(9);add(4);add(3);add(10);add(2);
    }};

    private static ArrayList<Integer> order7 = new ArrayList<Integer>() {{
        add(3);add(8);add(10);add(7);add(2);add(4);add(1);add(5);add(9);add(6);
    }};

    private static ArrayList<Integer> order8 = new ArrayList<Integer>() {{
        add(9);add(10);add(4);add(7);add(3);add(1);add(6);add(5);add(8);add(2);
    }};

    private static ArrayList<Integer> order9 = new ArrayList<Integer>() {{
        add(6);add(7);add(10);add(4);add(9);add(2);add(3);add(1);add(5);add(8);
    }};

    private static ArrayList<Integer> order10 = new ArrayList<Integer>() {{
        add(3);add(4);add(5);add(2);add(1);add(7);add(9);add(6);add(10);add(8);
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

    public static String[] getQuestionAnswersForCode(int code) {
        String questionAnswers[] = new String[MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016];
        ArrayList<Integer> order = getOrderForCode(code);
        System.out.println("code :  " + code + " order: " + order);
        try {
            String[] questions = Utils.getQuestionsForSet0();
            String[] answers = Utils.getAnswersForSet0();
            for (int i = 0; i < MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016; i++) {
                System.out.println("order get i " + order.get(i));
                questionAnswers[i] = " Correct Answer: " + answers[order.get(i) - 1] + " Question: " + questions[order.get(i) - 1];  // since it is 1 based index
            }
        } catch (Exception ex) {
            System.out.println("Exception while retrieving answers: " + ex);
        }
        return questionAnswers;
    }

    public static int getSet0OrderOfQuestion(int code, Integer index) {
        ArrayList<Integer> order = getOrderForCode(code);
        return order.indexOf(code);
    }
}
