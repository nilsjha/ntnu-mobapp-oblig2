package no.nilsjarh.ntnu.fantj2.model;

import android.util.Log;

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
    private String email;
    private String userToken;

    public LoggedInUser() {

    }

    public LoggedInUser(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }
    public void setUserToken(String token) { this.userToken = token;}

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUserToken() {
        return userToken;
    }
}