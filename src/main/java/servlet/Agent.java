package servlet;

/**
 * Created by govardhanreddy on 8/7/15.
 */
public class Agent {

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    String userName;
    String name;
    String mobileNum;
    long createdOn;
    long modifiedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Agent agent = (Agent) o;

        if (userName != null ? !userName.equals(agent.userName) : agent.userName != null) return false;
        return !(name != null ? !name.equals(agent.name) : agent.name != null);
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (createdOn ^ (createdOn >>> 32));
        result = 31 * result + (int) (modifiedOn ^ (modifiedOn >>> 32));
        return result;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
