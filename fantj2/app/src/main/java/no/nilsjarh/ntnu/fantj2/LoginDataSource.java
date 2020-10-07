package no.nilsjarh.ntnu.fantj2;

import no.nilsjarh.ntnu.fantj2.model.LoggedInUser;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        Retrofit rest = RestService.getRetrofitClient(true);

        try {
            // TODO: handle loggedInUser authentication

            AuthApi authService = rest.create(AuthApi.class);
            LoggedInUser user = new LoggedInUser();

            Call<String> authCall = authService.doLogin(username, password);
            Call<LoggedInUser> userCall = null;
            boolean tokenOk = false;


                Response<String> tokenResponse = authCall.execute();

                if (tokenResponse.isSuccessful()) {
                    tokenOk = true;
                    String token = tokenResponse.body();
                    userCall = authService.getCurrentUser("Bearer " + token);

                    Response<LoggedInUser> userResponse = userCall.execute();

                    if (userResponse.isSuccessful()) {
                        user = userResponse.body();
                        user.setUserToken(token);
                        return new Result.Success<>(user);
                    } else {
                        return new Result.Error(new IOException("Error getting user data " + userResponse.code()));
                    }
                } else {
                    return new Result.Error(new IOException("Error authenticating " + tokenResponse.code()));
                }

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));

        } finally {
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}