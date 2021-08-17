package com.example.bakingApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingApp.R;
import com.example.bakingApp.objects.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {
    private List<Recipe> recipeList;
    private final RecipeAdapterOnClickHandler onClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipeOnSlot);
    }

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler) {onClickHandler = clickHandler;}

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView recipeTitle;
        public RecipeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeTitle = itemView.findViewById(R.id.recipe_title);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = recipeList.get(adapterPosition);
            onClickHandler.onClick(recipe);
        }
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int LayoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(LayoutIdForListItem, parent, false);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder holder, int position) {
        Recipe recipeOnSlot = recipeList.get(position);
        TextView textView = holder.recipeTitle;
        textView.setText(recipeOnSlot.getName());
    }

    @Override
    public int getItemCount() {
        if (null == recipeList) return 0;
        return recipeList.size();
    }

    public void setRecipeData(List<Recipe> recipeDataUpdate) {
        recipeList = recipeDataUpdate;
        notifyDataSetChanged();
    }
}
