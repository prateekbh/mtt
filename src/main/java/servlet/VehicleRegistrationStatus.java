package servlet;

import utils.Utils;

/**
 * Created by govardhanreddy on 9/7/15.
 */
// TSRTA portal's response in object form

public class VehicleRegistrationStatus {
    Customer.Status status;
    String registrationNumber;
    String makerClass;
    String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMakerClass() {
        return makerClass;
    }

    public void setMakerClass(String makerClass) {
        this.makerClass = makerClass;
    }

    public Customer.Status getStatus() {
        return status;
    }

    public void setStatus(Customer.Status status) {
        this.status = status;
    }
    public void setStatus(String status) {
        this.status = Utils.getStatusForString(status);
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String toString() {
        return "VehicleRegistrationStatus{" +
                "status='" + status + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", makerClass='" + makerClass + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
