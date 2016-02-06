package servlet;

import javax.ws.rs.*;
import java.util.Arrays;

@Path(ResourcesPath.STUDENT)
public class Student {

    public enum Status {
        REGISTERED("registration_complete"),
        EVALUATED("evaluation_done");

        private final String statusCode;

        Status(String statusCode) {
            this.statusCode = statusCode;
        }
        public String getStatusCode() {
            return statusCode;
        }
    }

    // Student Data


    int id;
    String name;
    int questionPaperCode;
    String school;
    String place;
    String center;
    String gender;
    double score;

    public Student(int id, String name, int questionPaperCode, String school, String place, String center, String gender, double score) {
        this.id = id;
        this.name = name;
        this.questionPaperCode = questionPaperCode;
        this.school = school;
        this.place = place;
        this.center = center;
        this.gender = gender;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuestionPaperCode() {
        return questionPaperCode;
    }

    public void setQuestionPaperCode(int questionPaperCode) {
        this.questionPaperCode = questionPaperCode;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", questionPaperCode=" + questionPaperCode +
                ", school='" + school + '\'' +
                ", place='" + place + '\'' +
                ", center='" + center + '\'' +
                ", gender='" + gender + '\'' +
                ", score=" + score +
                '}';
    }
}
