package Komparatory;

import Objects.Osoba;

import java.util.Comparator;

public class SortByFullname implements Comparator<Osoba> {
    public int compare(Osoba o1, Osoba o2) {
        int surnameCompareValue = o1.getNazwisko().compareTo(o2.getNazwisko());
        int nameCompareValue = o1.getImie().compareTo(o2.getImie());
        return (surnameCompareValue == 0) ? nameCompareValue : surnameCompareValue;
    }
}
