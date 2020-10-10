package no.nilsjarh.ntnu.fantj2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {
    @Expose
    @SerializedName("id")
    private String userId;

    @Expose
    @SerializedName("email")
    private String userEmail;

    @Expose
    @SerializedName("createdDate")
    private Date userCreated;

    @Expose
    @SerializedName("currentState")
    private String userState;

    @Expose
    @SerializedName("firstName")
    private String userFirstName;

    @Expose
    @SerializedName("lastName")
    private String userLastName;

    @Expose
    @SerializedName("mobilePhone")
    private String userMobilePhone;


    public User() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Date userCreated) {
        this.userCreated = userCreated;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserMobilePhone() {
        return userMobilePhone;
    }

    public void setUserMobilePhone(String userMobilePhone) {
        this.userMobilePhone = userMobilePhone;
    }

    public String getFullName() {
        return userFirstName + "" + userLastName;
    }
}
