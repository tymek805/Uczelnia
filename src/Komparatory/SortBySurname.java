package Komparatory;

import Objects.Osoba;
import java.util.Comparator;

public class SortBySurname implements Comparator<Osoba> {
    public int compare(Osoba o1, Osoba o2) {
        return o1.getNazwisko().compareTo(o2.getNazwisko());
    }
}