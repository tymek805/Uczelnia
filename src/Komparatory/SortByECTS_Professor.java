package Komparatory;

import Objects.Kurs;

import java.util.Comparator;

public class SortByECTS_Professor implements Comparator<Kurs> {
    public int compare(Kurs k1, Kurs k2) {
        int ECTS_CompareValue = Integer.compare(k1.getPunktyECTS(), k2.getPunktyECTS());
        int prowadzacyCompareValue = k1.getProwadzacy().compareTo(k2.getProwadzacy());
        return (ECTS_CompareValue == 0) ? prowadzacyCompareValue : ECTS_CompareValue;
    }
}
