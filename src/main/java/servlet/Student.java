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
    String place;
    int schoolId;
    int center;

    public Student(String name, int id, String place, int schoolId, int center) {
        this.name = name;
        this.id = id;
        this.place = place;
        this.schoolId = schoolId;
        this.center = center;
    }

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
                ", place='" + place + '\'' +
                ", schoolId=" + schoolId +
                ", center=" + center +
                '}';
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
