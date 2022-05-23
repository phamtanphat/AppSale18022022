package com.example.appsale18022022.data.repositories;

import com.example.appsale18022022.data.datasources.remote.ApiService;
import com.example.appsale18022022.data.datasources.remote.AppResource;
import com.example.appsale18022022.data.datasources.remote.RetrofitClient;
import com.example.appsale18022022.data.models.User;

import retrofit2.Call;

public class AuthenticationRepository {
    private ApiService apiService;

    public AuthenticationRepository(){
        apiService = RetrofitClient.getInstance().getApiService();
    }

    public Call<AppResource<User>> login(String email, String password){
        return apiService.login(new User(email,password));
    }
}
