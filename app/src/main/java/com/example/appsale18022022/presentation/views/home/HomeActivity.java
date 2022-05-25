package com.example.appsale18022022.presentation.views.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.appsale18022022.R;
import com.example.appsale18022022.data.datasources.remote.AppResource;
import com.example.appsale18022022.data.models.Food;
import com.example.appsale18022022.presentation.views.authentications.sign_up.SignUpActivity;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    HomeViewModel viewModel;
    LinearLayout layoutLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        layoutLoading = findViewById(R.id.layout_loading);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        viewModel.getFoods().observe(this, new Observer<AppResource<List<Food>>>() {
            @Override
            public void onChanged(AppResource<List<Food>> foodAppResource) {
                switch (foodAppResource.status) {
                    case LOADING:
                        layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        layoutLoading.setVisibility(View.GONE);
                        break;
                    case ERROR:
                        Toast.makeText(HomeActivity.this, foodAppResource.message, Toast.LENGTH_SHORT).show();
                        layoutLoading.setVisibility(View.GONE);
                        break;
                }
            }
        });

        viewModel.fetchFoods();
    }
}
