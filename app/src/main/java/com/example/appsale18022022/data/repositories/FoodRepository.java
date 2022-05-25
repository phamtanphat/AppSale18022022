package com.example.appsale18022022.data.repositories;

import com.example.appsale18022022.data.datasources.remote.ApiService;
import com.example.appsale18022022.data.datasources.remote.AppResource;
import com.example.appsale18022022.data.datasources.remote.RetrofitClient;
import com.example.appsale18022022.data.models.Food;

import retrofit2.Call;

public class FoodRepository {
    private ApiService apiService;

    public FoodRepository() {
        apiService = RetrofitClient.getInstance().getApiService();
    }

    public Call<AppResource<Food>> fetchFoods() {
        return apiService.fetchFoods();
    }

}
