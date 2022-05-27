package com.example.appsale18022022.presentation.views.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appsale18022022.data.datasources.remote.AppResource;
import com.example.appsale18022022.data.models.Food;
import com.example.appsale18022022.data.models.User;
import com.example.appsale18022022.data.repositories.FoodRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private FoodRepository repository;
    private MutableLiveData<AppResource<List<Food>>> fooddData = new MutableLiveData<>();

    public HomeViewModel(Context context) {
        repository = new FoodRepository(context);
    }

    public LiveData<AppResource<List<Food>>> getFoods() {
        return fooddData;
    }

    public void fetchFoods() {
        fooddData.setValue(new AppResource.Loading(null));
        Call<AppResource<List<Food>>> callFoods = repository.fetchFoods();
        callFoods.enqueue(new Callback<AppResource<List<Food>>>() {
            @Override
            public void onResponse(Call<AppResource<List<Food>>> call, Response<AppResource<List<Food>>> response) {
                if (response.isSuccessful()) {
                    AppResource<List<Food>> foodResponse = response.body();
                    if (foodResponse.data != null) {
                        fooddData.setValue(new AppResource.Success<>(foodResponse.data));
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        fooddData.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<List<Food>>> call, Throwable t) {
                fooddData.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
}
