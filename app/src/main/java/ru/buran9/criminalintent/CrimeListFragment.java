package ru.buran9.criminalintent;

import android.os.Bundle;
import android.view.LayoutInflater;
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
        crimeAdapter = new CrimeAdapter(crimes);
        crimeRecycleView.setAdapter(crimeAdapter);
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

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), oneCrime.getCrimeTitle()+" click!", Toast.LENGTH_SHORT).show();
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



}
