package Strategia;

import Objects.Kurs;
import Objects.Student;

import java.util.ArrayList;

public class ByECTS implements Graduacja{

    @Override
    public boolean satisfiesRequirement(Student student) {
        int sumaECTS = 0;

        ArrayList<Kurs> kursy = student.getKursy();
        for (Kurs kurs: kursy){
            sumaECTS += kurs.getPunktyECTS();
        }

        return (sumaECTS < 10) ? false:true;
    }
}
