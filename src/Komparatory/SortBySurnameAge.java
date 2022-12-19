package Komparatory;

import Objects.Osoba;

import java.util.Comparator;

public class SortBySurnameAge implements Comparator<Osoba> {
    public int compare(Osoba o1, Osoba o2) {
        int surnameCompareValue = o1.getNazwisko().compareTo(o2.getNazwisko());
        int ageCompareValue = Integer.compare(o1.getWiek(), o2.getWiek());
        return (surnameCompareValue == 0) ? ageCompareValue : surnameCompareValue;
    }
}
