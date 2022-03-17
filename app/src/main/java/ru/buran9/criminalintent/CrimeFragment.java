package ru.buran9.criminalintent;


import static android.widget.CompoundButton.OnCheckedChangeListener;

import android.app.Activity;
import android.content.Intent;
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
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    public static final int REQUEST_DATE = 0;


    private Crime oneCrime;
    private EditText crimeTitleField;
    private Button crimeDateButton;
    private CheckBox crimeSolvedCheckBox;

    // создаем СТАТИЧЕСКИЙ МЕТОД который будет создавать объект CrimeFragment
    // и хранить в Bundle необходимые программисту аргументы
    // метод вызывается из активности-хоста CrimeActivity
    public static CrimeFragment newInstance(UUID crimeID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeID);
        CrimeFragment crimeFragment = new CrimeFragment();
        // setArguments(Bundle) встроенный метод фрагмента
        crimeFragment.setArguments(args);
        return crimeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // UUID crimeID = (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);

        // исползуем встроенный метод getArguments() класса Fragment
        UUID crimeID = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        oneCrime = CrimeLab.get(getActivity()).getCrime(crimeID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View crimeFragmentView = inflater.inflate(R.layout.fragment_crime, container, false);

        crimeTitleField = (EditText) crimeFragmentView.findViewById(R.id.crime_title);
        crimeTitleField.setText(oneCrime.getCrimeTitle());
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
        setDateForButton();

        // по нажатию кнопки создаем объект DatePickerFragment и вызваем его унаследованный
        // метод show с аргументами (FragmentManager, String)
        crimeDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(oneCrime.getCrimeDate());
                // задаем CrimeFragment как целевой фрагмент для передачи данных от DatePickerFragment
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        crimeSolvedCheckBox = (CheckBox) crimeFragmentView.findViewById(R.id.crime_solved);
        crimeSolvedCheckBox.setChecked(oneCrime.isCrimeSolved());
        crimeSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                oneCrime.setCrimeSolved(isChecked);
            }
        });
        return crimeFragmentView;
    }


    // переопределяем метод активности-хоста onActivityResult() который принимает данные с датой от
    // фрагмента DatePickerFragment и переопределяет дату у конкретного объекта Crime
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            oneCrime.setCrimeDate(date);
            setDateForButton();
        }
    }

    // метод отрисовки даты объекта Crime на кнопке
    private void setDateForButton() {
        crimeDateButton.setText(new SimpleDateFormat("yyyy.MM.dd 'time' HH:mm").format(oneCrime.getCrimeDate()));
    }
}
