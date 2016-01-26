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

    String name;
    int id;
    boolean isGovt;
    int schoolId;
    int center;
    int[] correctlyAnsweredQuestions;
    int[] wronglyAnsweredQuestions;
    int[] unansweredQuestions;
    long createdOn;
    long modifiedOn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isGovt() {
        return isGovt;
    }

    public void setIsGovt(boolean isGovt) {
        this.isGovt = isGovt;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getCenter() {
        return center;
    }

    public void setCenter(int center) {
        this.center = center;
    }

    public int[] getCorrectlyAnsweredQuestions() {
        return correctlyAnsweredQuestions;
    }

    public void setCorrectlyAnsweredQuestions(int[] correctlyAnsweredQuestions) {
        this.correctlyAnsweredQuestions = correctlyAnsweredQuestions;
    }

    public int[] getWronglyAnsweredQuestions() {
        return wronglyAnsweredQuestions;
    }

    public void setWronglyAnsweredQuestions(int[] wronglyAnsweredQuestions) {
        this.wronglyAnsweredQuestions = wronglyAnsweredQuestions;
    }

    public int[] getUnansweredQuestions() {
        return unansweredQuestions;
    }

    public void setUnansweredQuestions(int[] unansweredQuestions) {
        this.unansweredQuestions = unansweredQuestions;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public long getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(long modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (null == o || !(o instanceof Student)) return false;
        Student that = (Student) o;
        return this.id == that.id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", isGovt=" + isGovt +
                ", schoolId=" + schoolId +
                ", center=" + center +
                ", correctlyAnsweredQuestions=" + Arrays.toString(correctlyAnsweredQuestions) +
                ", wronglyAnsweredQuestions=" + Arrays.toString(wronglyAnsweredQuestions) +
                ", unansweredQuestions=" + Arrays.toString(unansweredQuestions) +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                '}';
    }
}
