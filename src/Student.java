import java.util.ArrayList;
import java.util.HashMap;

public class Student extends Osoba{
    private int numerIndeksu, rokStudiow;
    private String output;
    private boolean[] stanStudenta;
    private String[] stanStudentaLista = {"uczestnik programu ERASMUS", "student I-stopnia studiów", "student II-stopnia studiów", "student studiów stacjonarnych", "student studiów niestacjonarnych"};
    private String[] kategorieStudent;
    ArrayList<Kurs> kursy = new ArrayList<>();
    public Student(String imie, String nazwisko, String pesel, String gender, int wiek,
                   int numerIndeksu, int rokStudiow, boolean[] stanStudenta){
        super(imie, nazwisko, pesel, gender, wiek);
        this.numerIndeksu = numerIndeksu;
        this.rokStudiow = rokStudiow;
        this.stanStudenta = stanStudenta;
        for (int i = 0; i < stanStudenta.length; i++){
            if (stanStudenta[i]){
                output += ", " + stanStudentaLista[i];
            }
        }
        this.kategorieStudent = new String[]{String.valueOf(numerIndeksu), String.valueOf(rokStudiow), output};
    }
    public void getKursy(){
        for (Kurs kurs : kursy){
            kurs.getStan();
        }
    }

    public void getStan(){
        super.getStan();
        System.out.println("Numer indeksu: " + numerIndeksu + "\nRok studiów: " + rokStudiow +
                "\nStan studenta: " + output);
        getKursy();
        System.out.println("--------------------------------------------------");
    }

    public int search(int kategoriaID, String valueSearchCategory){
        kategoriaID = super.search(kategoriaID, valueSearchCategory);
        if (kategoriaID < kategorieStudent.length && kategoriaID >= 0){
            if (kategorieStudent[kategoriaID].equals(valueSearchCategory)){
                getStan();
                return 0;
            }
        } else if (kategoriaID < 0) {
            return 0;
        }
        return -1;
    }

    public void rozpoczecieKursu(Kurs kurs){
        kursy.add(kurs);
    }
}
