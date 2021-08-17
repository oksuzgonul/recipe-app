package com.example.bakingApp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.bakingApp.R;
import com.example.bakingApp.adapters.StepAdapter;
import com.example.bakingApp.fragments.RecipeDetailsFragment;
import com.example.bakingApp.fragments.StepDetailsFragment;
import com.example.bakingApp.objects.Recipe;
import com.example.bakingApp.objects.Step;

public class RecipeActivity extends AppCompatActivity implements StepAdapter.StepAdapterOnclickHandler {

    public final String INTENT_KEY_RECIPE = "recipe_data";
    private final String INTENT_KEY_STEP = "step_data";
    private boolean isTablet = false;
    private Recipe currentRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        if (findViewById(R.id.step_details_container) != null) {
            isTablet = true;
        }

        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        recipeDetailsFragment.setStepOnClickHandler(this);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra(INTENT_KEY_RECIPE)) {
                    currentRecipe = (Recipe) intent.getSerializableExtra(INTENT_KEY_RECIPE);
                }
            }
        } else {
            currentRecipe = (Recipe) savedInstanceState.getSerializable(INTENT_KEY_RECIPE);
        }

        assert currentRecipe != null;
        recipeDetailsFragment.setRecipeDetailData(currentRecipe);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recipe_details_container, recipeDetailsFragment)
                .commit();
    }

    @Override
    public void onClick(Step stepOnSlot) {
        if (isTablet) {
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setStepDetailData(stepOnSlot);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_container, stepDetailsFragment)
                    .commit();
        } else {
            Intent stepIntent = new Intent(this, StepActivity.class);
            stepIntent.putExtra(INTENT_KEY_STEP, stepOnSlot);
            startActivity(stepIntent);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(INTENT_KEY_RECIPE, currentRecipe);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}