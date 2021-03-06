package com.example.appsale18022022.presentation.views.authentications.sign_up;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appsale18022022.data.datasources.remote.AppResource;
import com.example.appsale18022022.data.models.User;
import com.example.appsale18022022.data.repositories.AuthenticationRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {
    private AuthenticationRepository repository;
    private MutableLiveData<AppResource<User>> userData = new MutableLiveData<>();

    public SignUpViewModel(Context context) {
        repository = new AuthenticationRepository(context);
    }

    public LiveData<AppResource<User>> getUserData() { return userData; }

    public void register(String email, String password, String phone, String name, String address) {
        userData.setValue(new AppResource.Loading(null));
        Call<AppResource<User>> callLogin = repository.register(email, password, name, phone, address);
        callLogin.enqueue(new Callback<AppResource<User>>() {
            @Override
            public void onResponse(Call<AppResource<User>> call, Response<AppResource<User>> response) {
                if (response.isSuccessful()) {
                    AppResource<User> userResponse = response.body();
                    if (userResponse.data != null) {
                        userData.setValue(new AppResource.Success<>(userResponse.data));
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        userData.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<AppResource<User>> call, Throwable t) {
                userData.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
}
