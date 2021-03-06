package no.nilsjarh.ntnu.fantj2;

import android.util.Log;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

import no.nilsjarh.ntnu.fantj2.model.LoggedInUser;
import no.nilsjarh.ntnu.fantj2.model.User;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    public String getToken() {
        return instance.user.getUserToken();
    }

    /**
     * Return user id of current authenticated user
     * @return String id of user
     */
    public String getUserId() {
        return this.user.getUserId();
    }
    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public void login(String username, String password, Consumer<Result<LoggedInUser>> resultCallback) {
        // handle login

        dataSource.login(username, password, (Result<LoggedInUser> loggedInUserResult)->{
            Log.d("AUTH-INFO", "Got result from LoginDataSource");
            Log.d("AUTH-INFO", loggedInUserResult.toString());
            if (loggedInUserResult instanceof Result.Success) {
                setLoggedInUser(((Result.Success<LoggedInUser>) loggedInUserResult).getData());
            }
            resultCallback.accept(loggedInUserResult);
        });

    }
}