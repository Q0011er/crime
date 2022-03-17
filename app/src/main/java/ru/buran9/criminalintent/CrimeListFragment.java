package ru.buran9.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView crimeRecycleView;
    private CrimeAdapter crimeAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //сообщаем FragmentManager что экземпляр CrimeListFragment должен получить
        //обратный вызов меню (отобразить меню) определнного в onCreateOptionsMenu()
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        crimeRecycleView = (RecyclerView) view.findViewById(R.id.crime_recycle_view);
        crimeRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (crimeAdapter == null) {
            crimeAdapter = new CrimeAdapter(crimes);
            crimeRecycleView.setAdapter(crimeAdapter);
        } else {
            crimeAdapter.notifyDataSetChanged();
        }
    }

    // внутренний класс - наследник ViewHolder для хранения представления list_item_crime
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView crimeTitleTextView;
        private TextView crimeDateTextView;
        private ImageView crimeSolvedImageView;
        private Crime oneCrime;

        // конструктор
        // вызывается из метода onCreateViewHolder класса Adapter
        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            crimeTitleTextView = (TextView) itemView.findViewById(R.id.textview_recycler_crimetitle);
            crimeDateTextView = (TextView) itemView.findViewById(R.id.textview_recycler_crimedate);
            crimeSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved_img);
            itemView.setOnClickListener(this);
        }

        // метод для связывания виджетов View с конкретным объектом Crime
        // вызывается из метода onBindViewHolder класса Adapter
        public void bind(Crime crime) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'time' HH:mm");
            oneCrime = crime;
            crimeTitleTextView.setText(oneCrime.getCrimeTitle());
            crimeDateTextView.setText(sdf.format(oneCrime.getCrimeDate()));
            crimeSolvedImageView.setVisibility(oneCrime.isCrimeSolved() ? View.VISIBLE : View.GONE);
        }

        //вызываем CrimePagerActivity по клику по вью из RecyclerView
        @Override
        public void onClick(View view) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), oneCrime.getCrimeID());
            startActivity(intent);
        }
    }

    // внутренний класс - наследник Adapter для управления конкретными View
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> crimesList;

        // конструктор
        public CrimeAdapter(List<Crime> crimes) {
            crimesList = crimes;
        }

        // метод вызывается когда RecyclerView требуется новое представление для отображения
        // очередного элемента списка. В методе создается объект CrimeHolder.
        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = crimesList.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return crimesList.size();
        }
    }



    // определяем меню для фрагмента, вызвается методом setHasOptionsMenu(boolean) и обрабатывается
    // FragmentManager'ом
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
    }

    // метод для обработки нажатия по пункту меню
    // после обработки команды возвращаем true - сообщаем, что дальнейшая обработка не требуется
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getCrimeID());
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

}
