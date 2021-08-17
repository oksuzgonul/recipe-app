package com.example.bakingApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingApp.R;
import com.example.bakingApp.objects.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder> {

    private List<Ingredient> ingredientList;

    public IngredientAdapter() {}

    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView ingredientText;
        public IngredientAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientText = itemView.findViewById(R.id.ingredient_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Ingredient ingredient = ingredientList.get(adapterPosition);
        }
    }

    @NonNull
    @Override
    public IngredientAdapter.IngredientAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int LayoutIdForListItem = R.layout.ingredient_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(LayoutIdForListItem, parent, false);
        return new IngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.IngredientAdapterViewHolder holder, int position) {
        Ingredient ingredientOnSlot = ingredientList.get(position);
        TextView textView = holder.ingredientText;
        textView.setText(ingredientOnSlot.getIngredient());
    }

    @Override
    public int getItemCount() {
        if (null == ingredientList) return 0;
        return ingredientList.size();
    }

    public void setIngredientData(List<Ingredient> ingredientDataUpdate) {
        ingredientList = ingredientDataUpdate;
        notifyDataSetChanged();
    }
}
