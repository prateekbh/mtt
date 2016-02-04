package servlet.rest;

import servlet.Sets;

import java.util.Arrays;

/**
 * Created by govardhanreddy on 1/31/16.
 */
public class QuestionPaperSet {
    private final int paperCode;
    private final String[] questions;

    QuestionPaperSet(int paperCode) {
        this.paperCode = paperCode;
        questions = Sets.getQuestionAnswersForCode(paperCode);
    }

    public String[] getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        return "QuestionPaperSet{" +
                "paperCode=" + paperCode +
                ", questions=" + Arrays.toString(questions) +
                '}';
    }
}
