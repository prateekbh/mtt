package utils;

import servlet.Sets;
import java.util.ArrayList;

/**
 * Created by govardhanreddy on 2/5/16.
 */
public class QuestionPaperCreator {
    public static void main(String[] args) {

        for (int i = 0; i < MTT_CONSTANTS.NUMBER_OF_SETS; i++) {
            ArrayList<Integer> order = Sets.getOrderForCode(i + 1);
            String set = "set" + (i + 1);
            String systemCommand = "touch " + set + " ";
//            System.out.println("order: " + order);
            int serialNum = 1;
            for (Integer q : order) {
                systemCommand += " && echo -n " + serialNum + ". >> " + set + " && cat " + q + " >> " + set;
            }
            System.out.println(systemCommand);
        }
    }

    private static void concatenateTwoFiles() {

    }
}
