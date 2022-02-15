package ru.buran9.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID crimeID;
    private String crimeTitle;
    private Date crimeDate;
    private boolean crimeSolved;

    public Crime() {
        crimeID = UUID.randomUUID();
        crimeDate = new Date();
    }

    public UUID getCrimeID() {
        return crimeID;
    }

    public String getCrimeTitle() {
        return crimeTitle;
    }

    public void setCrimeTitle(String crimeTitle) {
        this.crimeTitle = crimeTitle;
    }

    public Date getCrimeDate() {
        return crimeDate;
    }

    public void setCrimeDate(Date crimeDate) {
        this.crimeDate = crimeDate;
    }

    public boolean isCrimeSolved() {
        return crimeSolved;
    }

    public void setCrimeSolved(boolean crimeSolved) {
        this.crimeSolved = crimeSolved;
    }
}
