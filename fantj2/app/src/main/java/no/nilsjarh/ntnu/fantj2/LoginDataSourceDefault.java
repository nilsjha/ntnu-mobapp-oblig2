package no.nilsjarh.ntnu.fantj2;

import no.nilsjarh.ntnu.fantj2.Result;
import no.nilsjarh.ntnu.fantj2.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSourceDefault {

    public no.nilsjarh.ntnu.fantj2.data.Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new no.nilsjarh.ntnu.fantj2.data.Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}