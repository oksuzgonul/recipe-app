package com.example.bakingApp;

import android.content.Intent;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.bakingApp.activities.RecipeActivity;
import com.example.bakingApp.objects.Ingredient;
import com.example.bakingApp.objects.Recipe;
import com.example.bakingApp.objects.Step;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityBasicTest {
    private final String INTENT_KEY_RECIPE = "recipe_data";

    private Ingredient ingredient = new Ingredient(
            0,
            "cup",
            "Test Ingredient");
    private Step step = new Step(
            0,
            "Test Step",
            "Test Step Desc",
            "Video url",
            "Thumb url");

    private List<Ingredient> ingredientsList = Arrays.asList(ingredient);
    private List<Step> stepList = Arrays.asList(step);

    private Recipe recipe = new Recipe(
            0,
            "Test Recipe",
            ingredientsList,
            stepList,
            0,
            "img url");
    @Rule
    public ActivityTestRule<RecipeActivity> activityTestRule
            = new ActivityTestRule<RecipeActivity>(RecipeActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            intent.putExtra(INTENT_KEY_RECIPE, recipe);
            return intent;
        }
    };

    @Test
    public void clickStepItem_opensStep() {
        onView(withId(R.id.step_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.step_detail_desc))
                .check(matches(withText("Test Step Desc")));
    }
}
