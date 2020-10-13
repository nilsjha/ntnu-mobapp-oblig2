package no.nilsjarh.ntnu.fantj2;

import android.util.Log;

import no.nilsjarh.ntnu.fantj2.model.LoggedInUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.function.Consumer;


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public void login(String username, String password, Consumer<Result<LoggedInUser>> resultCallback) {

        Retrofit retrofitClient = RestService.getRetrofitClient(false);

        try {
            // TODO: handle loggedInUser authentication
            AuthApi authService = retrofitClient.create(AuthApi.class);
            MarketplaceApi marketService = retrofitClient.create(MarketplaceApi.class);
            Call<Void> authCall = authService.doLogin(username, password);
            Log.d("AUTH-INFO", "Starting auth");
                authCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> tokenResponse) {

                        if (tokenResponse.isSuccessful()) {
                            String token = tokenResponse.headers().get("Authorization");
                            Call<LoggedInUser> userCall = authService.getCurrentUser(token);
                            userCall.enqueue(new Callback<LoggedInUser>() {
                                @Override
                                public void onResponse(Call<LoggedInUser> call, Response<LoggedInUser> response) {
                                    LoggedInUser user = response.body();
                                    user.setUserToken(token);
                                    user.setUserEmail(username);
                                    Log.d("AUTH-INFO", "Auth OK for " + username);
                                    resultCallback.accept(new Result.Success<>(user));
                                }

                                @Override
                                public void onFailure(Call<LoggedInUser> call, Throwable t) {
                                    LoggedInUser user = new LoggedInUser();
                                    user.setUserToken(token);
                                    user.setUserEmail(username);
                                    Log.d("AUTH-WARN", "Auth OK for " + username
                                            + ", but failed to retreive user data from server." +
                                            "Some userdata might not be instantiated"
                                    );
                                    resultCallback.accept(new Result.Success<>(user));
                                }
                            });

                        } else {
                            if (tokenResponse.code() == 401) {
                                Log.d("AUTH-INFO", "Auth failed for " + username);
                                resultCallback.accept(new Result.Error(
                                       new Exception("Wrong username or password")));
                            } else {
                                Log.d("AUTH-ERROR", "Unknown server error");
                                resultCallback.accept(new Result.Error(
                                        new Exception("Unknown error")));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        resultCallback.accept(new Result.Error(new IOException()));
                        Log.d("AUTH-ERROR", "Unable to communicate with server");
                        Log.d("AUTH-ERROR", t.getMessage());
                    }
                });

        } catch (Exception e) {
            //return new Result.Error(new IOException("Error logging in", e));
            e.printStackTrace();

        } finally {
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}