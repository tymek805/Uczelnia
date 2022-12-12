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

    public Student(String[] args) {
        super(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]));
        this.numerIndeksu = Integer.parseInt(args[5]);
        this.rokStudiow = Integer.parseInt(args[6]);
        String[] test = args[7].split(",");

        for (int i = 0; i < test.length; i++){
            if (test[i].equals("true")){
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

    public String[] getKategorie(){
        super.getKategorie();
        return kategorieStudent;
    }

    public void startKursu(Kurs kurs){
        kursy.add(kurs);
    }
}
