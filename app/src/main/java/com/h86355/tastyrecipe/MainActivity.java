package com.h86355.tastyrecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.h86355.tastyrecipe.databinding.ActivityMainBinding;
import com.h86355.tastyrecipe.fragments.MainFragment;
import com.h86355.tastyrecipe.fragments.StartFragment;
import com.h86355.tastyrecipe.viewmodels.MainFragmentViewModel;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN_ACTIVITY";
    private boolean isFirstTime;
    private ActivityMainBinding binding;
    private MainFragmentViewModel mainFragmentViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        isFirstTime = getFirstTimeStart();

        if (isFirstTime) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(binding.layoutMainContainer.getId(), new StartFragment())
                    .commit();
            setFirstTimeStart();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.layout_main_container, new MainFragment(), getString(R.string.fragment_main_tag))
                    .commit();
        }
        mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
        requestRecipes();
    }

    private boolean getFirstTimeStart() {
        SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(this);
        return sf.getBoolean("isFirstTime", true);
    }

    private void setFirstTimeStart() {
        SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sf.edit();
        editor.putBoolean("isFirstTime", false);
        editor.apply();
    }

    private void requestRecipes() {
        String timeZone = "";
        long timeZoneHour = TimeUnit.HOURS.convert(TimeZone.getDefault().getRawOffset(), TimeUnit.MILLISECONDS);
        if (timeZoneHour < 0 && timeZoneHour > -10) {
            timeZone = "-" + "0" + timeZoneHour + "00";
        } else if (timeZoneHour < 0 && timeZoneHour <= -10) {
            timeZone = "-" + timeZoneHour + "00";
        } else if (timeZoneHour >= 0 && timeZoneHour < 10) {
            timeZone = "+" + "0" + timeZoneHour + "00";
        } else {
            timeZone = "+" + timeZoneHour + "00";
        }
        Log.d(TAG, "requestRecipes: " + timeZone);

        mainFragmentViewModel.getRecipesAPI(0, 10, "", "");
        mainFragmentViewModel.getRecommendedFeedsAPI(0, 1, timeZone, false);
    }
}