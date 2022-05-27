package com.example.appsale18022022.data.datasources.remote;

import android.content.Context;

import com.example.appsale18022022.common.AppConstant;
import com.example.appsale18022022.common.SharePref;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private Retrofit retrofit;
    private ApiService apiService;

    private RetrofitClient(Context context) {
        retrofit = createRetrofit(context);
        apiService = createApiService(retrofit);
    }

    public static RetrofitClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitClient(context);
        }
        return instance;
    }

    private Retrofit createRetrofit(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request();
                        String token = SharePref.getInstance(context).getToken();
                        if (!token.isEmpty()) {
                            newRequest.newBuilder()
                                    .addHeader("Authorization", "Bearer " + token)
                                    .build();
                        }
                        return chain.proceed(newRequest);
                    }
                })
                .protocols(Arrays.asList(Protocol.HTTP_1_1))
                .build();
        Gson gson = new GsonBuilder().setLenient().create();

        return new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private ApiService createApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
