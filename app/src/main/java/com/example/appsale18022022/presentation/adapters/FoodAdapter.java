package com.example.appsale18022022.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appsale18022022.R;
import com.example.appsale18022022.common.AppConstant;
import com.example.appsale18022022.data.models.Food;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    List<Food> listFoods;
    Context context;

    public FoodAdapter() {
        listFoods = new ArrayList<>();
    }

    public void updateListProduct(List<Food> data) {
        if (listFoods != null && listFoods.size() > 0) {
            listFoods.clear();
        }
        listFoods.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodViewHolder holder, int position) {
        holder.bind(context, listFoods.get(position));
    }

    @Override
    public int getItemCount() {
        return listFoods.size();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAddress, tvPrice;
        ImageView img;

        public FoodViewHolder(@NonNull View view) {
            super(view);
            tvName = view.findViewById(R.id.textViewName);
            tvAddress = view.findViewById(R.id.textViewAddress);
            tvPrice = view.findViewById(R.id.textViewPrice);
            img = view.findViewById(R.id.imageView);
        }

        public void bind(Context context, Food food) {
            tvName.setText(food.getName());
            tvAddress.setText(food.getAddress());
            tvPrice.setText(new DecimalFormat("#,###").format(food.getPrice()) + " VND");
            Glide.with(context)
                    .load(AppConstant.BASE_URL  + food.getImg())
                    .placeholder(R.drawable.image_logo)
                    .into(img);
        }
    }


}
