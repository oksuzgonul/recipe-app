package com.example.bakingApp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.bakingApp.R;
import com.example.bakingApp.fragments.StepDetailsFragment;
import com.example.bakingApp.objects.Step;

public class StepActivity extends AppCompatActivity {
    private final String INTENT_KEY_STEP = "step_data";
    private String TAG = StepActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        if (savedInstanceState == null) {
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra(INTENT_KEY_STEP)) {
                    final Step currentStep = (Step) intent.getSerializableExtra(INTENT_KEY_STEP);
                    assert currentStep != null;
                    stepDetailsFragment.setStepDetailData(currentStep);
                }
            }
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.step_details_container, stepDetailsFragment)
                    .commit();
        }
    }
}
