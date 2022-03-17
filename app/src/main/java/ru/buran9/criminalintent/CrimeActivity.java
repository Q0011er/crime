package ru.buran9.criminalintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

// активность отключена в манифесте
// вмеcто данного класса используется CrimePagerActivity с реализацией ViewPager

public class CrimeActivity extends SingleFragmentActivity {

    public static final String EXTRA_CRIME_ID = "criminalintent.crime_id";

    public static Intent newIntent(Context context, UUID crimeID) {
        Intent intent = new Intent(context, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeID);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID crimeID = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeID);
    }
}