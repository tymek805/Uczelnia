package Obserwator;

import Objects.Student;
import Strategia.ByECTS;
import Strategia.ByGPA;
import Strategia.Sprawdzanie;

public class StudentObserver implements Observer{
    private final Student student;

    public StudentObserver(Student student){
        this.student = student;
    }

    @Override
    public void update(String changedValueType) {
        Sprawdzanie test = null;
        if (changedValueType.equals("ocena")) test = new Sprawdzanie(new ByGPA());
        else if (changedValueType.equals("ECTS")) test = new Sprawdzanie(new ByECTS());
        boolean testValue = test.check(student);
        student.setCzyZdaje(testValue);
        System.out.println(testValue);
    }
}
