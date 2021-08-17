package com.example.bakingApp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingApp.R;
import com.example.bakingApp.adapters.IngredientAdapter;
import com.example.bakingApp.adapters.StepAdapter;
import com.example.bakingApp.objects.Recipe;
import com.example.bakingApp.utils.WidgetUpdateService;

public class RecipeDetailsFragment extends Fragment {

    private static final String RECIPE_SAVED = "recipe_saved";

    private RecyclerView stepRecyclerView;
    private RecyclerView ingredientRecyclerView;

    private StepAdapter stepAdapter;
    private IngredientAdapter ingredientAdapter;

    private Recipe recipeOnFragment;

    private StepAdapter.StepAdapterOnclickHandler stepAdapterOnclickHandler;

    private boolean isTablet = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            recipeOnFragment = (Recipe) savedInstanceState.getSerializable(RECIPE_SAVED);
        }
        if (getActivity().findViewById(R.id.step_details_container) != null) {
            isTablet = true;
        }

        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        Button showInWidget = rootView.findViewById(R.id.widget_button);
        showInWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetUpdateService.startActionWidgetUpdate(getContext(), recipeOnFragment);
            }
        });

        stepRecyclerView = rootView.findViewById(R.id.step_recyclerview);
        ingredientRecyclerView = rootView.findViewById(R.id.ingredient_recyclerview);
        LinearLayoutManager layoutManagerIngredients =
                new LinearLayoutManager(getContext());

        ingredientRecyclerView.setLayoutManager(layoutManagerIngredients);
        ingredientRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManagerSteps =
                new LinearLayoutManager(getContext());
        stepRecyclerView.setLayoutManager(layoutManagerSteps);
        stepRecyclerView.setHasFixedSize(true);

        ingredientAdapter = new IngredientAdapter();
        ingredientRecyclerView.setAdapter(ingredientAdapter);

        stepAdapter = new StepAdapter(stepAdapterOnclickHandler);
        stepRecyclerView.setAdapter(stepAdapter);

        ingredientAdapter.setIngredientData(recipeOnFragment.getIngredientsList());
        stepAdapter.setStepData(recipeOnFragment.getStepsList());

        return rootView;
    }

    @Override
    public void onStop() {
        WidgetUpdateService.stopActionWidgetUpdate(getContext(), recipeOnFragment);
        super.onStop();
    }

    public void setRecipeDetailData(Recipe recipeToSet) { recipeOnFragment = recipeToSet;}
    public void setStepOnClickHandler(StepAdapter.StepAdapterOnclickHandler handlerToSet) { stepAdapterOnclickHandler = handlerToSet;}
}
