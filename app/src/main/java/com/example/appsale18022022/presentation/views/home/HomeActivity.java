package com.example.appsale18022022.presentation.views.home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsale18022022.R;
import com.example.appsale18022022.data.datasources.remote.AppResource;
import com.example.appsale18022022.data.models.Food;
import com.example.appsale18022022.presentation.adapters.FoodAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    HomeViewModel viewModel;
    LinearLayout layoutLoading;
    RecyclerView rcvFood;
    FoodAdapter foodAdapter;
    TextView tvCountCart;
    List<Food> listCart;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        layoutLoading = findViewById(R.id.layout_loading);
        rcvFood = findViewById(R.id.recyclerViewFood);
        toolbar = findViewById(R.id.toolbar_home);

        listCart = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        foodAdapter = new FoodAdapter();
        rcvFood.setAdapter(foodAdapter);
        rcvFood.setHasFixedSize(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white,null));

        viewModel.getFoods().observe(this, new Observer<AppResource<List<Food>>>() {
            @Override
            public void onChanged(AppResource<List<Food>> foodAppResource) {
                switch (foodAppResource.status) {
                    case LOADING:
                        layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        layoutLoading.setVisibility(View.GONE);
                        foodAdapter.updateListProduct(foodAppResource.data);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = menuItem.getActionView();
        tvCountCart = actionView.findViewById(R.id.text_cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {
        if (listCart != null) {
            if (listCart.size() == 0) {
                tvCountCart.setVisibility(View.GONE);
            } else {
                tvCountCart.setVisibility(View.VISIBLE);
                tvCountCart.setText(String.valueOf(Math.min(listCart.size(), 99)));
            }
        }
    }
}
