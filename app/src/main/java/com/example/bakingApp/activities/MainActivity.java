package com.example.bakingApp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bakingApp.R;
import com.example.bakingApp.adapters.RecipeAdapter;
import com.example.bakingApp.objects.Recipe;
import com.example.bakingApp.utils.JsonUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    private static final String DATA_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    public final String INTENT_KEY_RECIPE = "recipe_data";

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    private TextView errorMessageDisplay;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int spanCount = getSpanForDisplayWidth();

        recyclerView = findViewById(R.id.recipe_recyclerview);

        errorMessageDisplay = findViewById(R.id.error_message);
        progressBar = findViewById(R.id.progressbar);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, spanCount, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        recipeAdapter = new RecipeAdapter(this);
        recyclerView.setAdapter(recipeAdapter);

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;

        Network activeNetwork = connectivityManager.getActiveNetwork();
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
        if (networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            loadRecipeData();
        } else {
            progressBar.setVisibility(View.GONE);
            errorMessageDisplay.setText(R.string.error_no_internet);
            errorMessageDisplay.setVisibility(View.VISIBLE);
        }

    }

    private int getSpanForDisplayWidth() {
        int spanWidth = 1;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int width = metrics.widthPixels;
        int dpi = metrics.densityDpi;
        int ratio = dpi/160;
        int score = width  / ratio;
        if (score >= 600) {
            spanWidth = 3;
        }
        return spanWidth;
    }

    private void loadRecipeData() {
        errorMessageDisplay.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        Uri readUri = Uri.parse(DATA_URL);
        Uri.Builder uriBuilder = readUri.buildUpon();
        new FetchRecipeTask().execute(uriBuilder.toString());
    }

    @Override
    public void onClick(Recipe recipeOnSlot) {
        final Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(INTENT_KEY_RECIPE, recipeOnSlot);
        startActivity(intent);
    }

    public class FetchRecipeTask extends AsyncTask<String, Void, List<Recipe>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Recipe> doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }
            return JsonUtils.fetchRecipeData(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            progressBar.setVisibility(View.INVISIBLE);
            if (recipes != null) {
                errorMessageDisplay.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                recipeAdapter.setRecipeData(recipes);
            } else {
                recyclerView.setVisibility(View.INVISIBLE);
                errorMessageDisplay.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(recipes);
        }
    }
}