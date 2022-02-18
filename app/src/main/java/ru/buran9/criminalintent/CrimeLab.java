package ru.buran9.criminalintent;

// singletone class

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private List<Crime> listCrimes;

    //constructor
    private CrimeLab(Context context) {
        //создаем лист преступлений
        listCrimes = new ArrayList<>();
        //заполняем лист преступлений
        for (int i = 0; i < 50; i++) {
            Crime crime = new Crime();
            crime.setCrimeTitle("Crime #" + i);
            crime.setCrimeSolved(i % 2 == 0);
            listCrimes.add(crime);
        }
    }

    public List<Crime> getCrimes() {
        return listCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : listCrimes) {
            if (crime.getCrimeID().equals(id)) {
                return crime;
            }
        }
        return null;
    }

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }

        return sCrimeLab;
    }

}
