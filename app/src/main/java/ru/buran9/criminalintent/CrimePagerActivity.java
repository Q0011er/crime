package ru.buran9.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.UUID;

// новый класс взамен CrimeActivity реализует ViewPager который позволяет
// смахивать экран вправо/влево для перелистования преступлений

public class CrimePagerActivity extends AppCompatActivity {

    public static final String EXTRA_CRIME_ID = "ru.buran9.crimeID";

    private ViewPager crimesViewPager;
    private List<Crime> crimesList;

    public static Intent newIntent(Context context, UUID crimeID) {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeID);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeID = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        crimesViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        crimesList = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        crimesViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                Crime crime = crimesList.get(position);
                return CrimeFragment.newInstance(crime.getCrimeID());
            }

            @Override
            public int getCount() {
                return crimesList.size();
            }
        });

        // вызваем тот фрагмент (преступление), на который нажал пользователь,
        // а не первый по умолчанию
        for (int i = 0; i < crimesList.size(); i++) {
            if (crimesList.get(i).getCrimeID().equals(crimeID)) {
                crimesViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
