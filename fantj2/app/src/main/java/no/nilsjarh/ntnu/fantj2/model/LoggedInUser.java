package no.nilsjarh.ntnu.fantj2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    @Expose
    @SerializedName("id")
    private String userId;

    @Expose
    @SerializedName("email")
    private String userName;

    private String userToken;

    public LoggedInUser() {

    }

    public LoggedInUser(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
    public void setUserToken(String token) { this.userToken = token;}

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String email) {
        this.userName = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserToken() {
        return userToken;
    }
}