package servlet;

import javax.ws.rs.*;

@Path(ResourcesPath.CUSTOMER)
public class Customer {

    public enum Status {
        NEW("new"),
        GOT_REGISTRATION("registration_complete"),
        INFORMED("sms_sent"),
        INFORM_FAILED("sms failed"),
        PROCESS_COMPLETED("processed"),
        ARCHIVED("archived");

        private final String statusCode;

        Status(String statusCode) {
            this.statusCode = statusCode;
        }
        public String getStatusCode() {
            return statusCode;
        }

    }

    // Customer Data

    String name;
    String trNumber;
    String mobileNumber;
    String vehicleDetails;
    String agentName;
    Status status;
    long createdOn;
    long modifiedOn;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    String registrationNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrNumber() {
        return trNumber;
    }

    public void setTrNumber(String trNumber) {
        this.trNumber = trNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(String vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        if (null == o || !(o instanceof Customer)) return false;
        Customer that = (Customer) o;
        return name.equals(that.name) && trNumber.equals(that.trNumber) && mobileNumber.equals(that.mobileNumber);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", trNumber='" + trNumber + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", vehicleDetails='" + vehicleDetails + '\'' +
                ", agentName='" + agentName + '\'' +
                ", status='" + status + '\'' +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                '}';
    }
}
