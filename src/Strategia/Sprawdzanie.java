package Strategia;

import Objects.Student;

public class Sprawdzanie {
    private Graduacja graduacja;

    public Sprawdzanie(Graduacja graduacja){
        this.graduacja = graduacja;
    }

    public boolean check(Student student){
        return graduacja.satisfiesRequirement(student);
    }
}
