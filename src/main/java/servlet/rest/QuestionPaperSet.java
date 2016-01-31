package servlet.rest;

import servlet.Sets;

import java.util.Arrays;

/**
 * Created by govardhanreddy on 1/31/16.
 */
public class QuestionPaperSet {
    private final int id;
    private final int[] order;
    private final String[] questions;

    QuestionPaperSet(int id, int[] order) {
        this.id = id;
        this.order = order;
        questions = Sets.getQuestionsForCode(id);
    }

    public int getId() {
        return id;
    }

    public int[] getOrder() {
        return order;
    }

    public String[] getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        return "QuestionPaperSet{" +
                "id=" + id +
                ", order=" + Arrays.toString(order) +
                ", questions=" + Arrays.toString(questions) +
                '}';
    }
}
