package ru.buran9.criminalintent;


import static android.widget.CompoundButton.*;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CrimeFragment extends Fragment {

    private Crime oneCrime;
    private EditText crimeTitleField;
    private Button crimeDateButton;
    private CheckBox crimeSolvedCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oneCrime = new Crime();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View crimeFragmentView = inflater.inflate(R.layout.fragment_crime, container, false);

        crimeTitleField = (EditText) crimeFragmentView.findViewById(R.id.crime_title);
        crimeTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                oneCrime.setCrimeTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        crimeDateButton = (Button) crimeFragmentView.findViewById(R.id.crime_date);
        crimeDateButton.setText(oneCrime.getCrimeDate().toString());
        crimeDateButton.setEnabled(false);

        crimeSolvedCheckBox = (CheckBox) crimeFragmentView.findViewById(R.id.crime_solved);
        crimeSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                oneCrime.setCrimeSolved(isChecked);
            }
        });


        return crimeFragmentView;
    }
}
