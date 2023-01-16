package Strategia;

import Objects.Student;

import java.util.ArrayList;

public class ByGPA implements Graduacja{

    @Override
    public boolean satisfiesRequirement(Student student) {
        ArrayList<Double> oceny = student.getOceny();
        double GPA = 1;
        for (Double ocena:oceny){
            GPA += ocena;
        }
        GPA /= oceny.size();
        return (GPA < 3) ? false:true;
    }
}
