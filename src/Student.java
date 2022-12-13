import java.util.ArrayList;

public class Student extends Osoba{
    private int numerIndeksu, rokStudiow;
    private String output = "";
    private final String[] stanStudentaLista = {"uczestnik programu ERASMUS", "student I-stopnia studiów", "student II-stopnia studiów", "student studiów stacjonarnych", "student studiów niestacjonarnych"};
    private String[] stanStudenta;
    private final String[] kategorieStudent;
    private ArrayList<Kurs> kursy = new ArrayList<>();

    public Student(String[] args) {
        super(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]));
        this.numerIndeksu = Integer.parseInt(args[5]);
        this.rokStudiow = Integer.parseInt(args[6]);
        stanStudenta = args[7].split(",");
        for (int i = 0; i < stanStudenta.length; i++){
            if (stanStudenta[i].equals("T")){
                output += stanStudentaLista[i] + ", ";
            }
        }
        if (!output.equals("")){
        output = output.substring(0, output.length()-2);}
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

    public void startKursu(Kurs kurs){kursy.add(kurs);}
    public void koniecKursu(Kurs kurs) {kursy.remove(kurs);}
}
