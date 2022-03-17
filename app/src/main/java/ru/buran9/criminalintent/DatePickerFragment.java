package ru.buran9.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

// всплывающее диалоговое окно-фрагмент с выбором даты из календаря
// реализуем передачу данных от одного фрагмента другму с помощью методов
// setTargetFragment() и getTargetFragment() когда оба фрагмента принадлежат одной активности!!!

public class DatePickerFragment extends DialogFragment {

    public static final String ARG_DATE = "date";
    public static final String EXTRA_DATE = "ru.buran9.date";
    private DatePicker argDatePicker;

    // можно было бы вместо onCreateDialog(Bundl) переопределить метод onCreateView()
    // но тогда пришлось бы заголовк и кнопку ОК определять отдельно самостоятельно
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // для обработки даты хранящейся в аргументах объекта приходится использовать
        // класс Calendar, чтобы потом отобразить дату во вью через целочисленные значения (int)
        Date date = (Date) getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        // отображаем дату в календаре диалогового окна на основе сохраненных аргументов
        argDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_picker);
        argDatePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = argDatePicker.getYear();
                        int month = argDatePicker.getMonth();
                        int day = argDatePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day).getTime();
                        // вызываем собсвенный метод для передачи даты целевому фрагменту
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }

    // метод для создания объекта DatePickerFragment и хранения аргументов даты
    // вызывается из CrimeFragment
    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // метод для передачи данных целевой активности определенной методом
    // setTargetFragment при клике на кнопку в CrimeFragment
    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

    }

}
