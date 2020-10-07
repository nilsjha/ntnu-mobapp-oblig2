package no.nilsjarh.ntnu.fantj2;

import android.util.Log;

import no.nilsjarh.ntnu.fantj2.model.LoggedInUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.function.Consumer;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public void login(String username, String password, Consumer<Result<LoggedInUser>> resultCallback) {

        Retrofit rest = RestService.getRetrofitClient(false);

        try {
            // TODO: handle loggedInUser authentication
            AuthApi authService = rest.create(AuthApi.class);
            Call<Void> authCall = authService.doLogin(username, password);
            Log.d("AUTH", "DATASOURCE BEFORE EBNQ");
                authCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> tokenResponse) {
                        LoggedInUser user = new LoggedInUser();
                        Log.d("AUTH", "GOT USER DATA");

                        if (tokenResponse.isSuccessful()) {
                            String token = tokenResponse.headers().get("Authorization");
                            System.err.println("OK LOGIN AUTH" + token);
                            Call<LoggedInUser> userCall = authService.getCurrentUser(token);
                            user.setUserToken(token);
                            user.setUserName(username);

                            resultCallback.accept(new Result.Success<>(user));
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("AUTH", call.toString());
                        Log.d("AUTH", t.getMessage());
                        Log.d("AUTH", "FAILED HARD");
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