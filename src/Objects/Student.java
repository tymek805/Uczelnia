package Objects;

import Obserwator.StudentObserver;

import java.util.ArrayList;

public class Student extends Osoba{
    private int numerIndeksu, rokStudiow;
    private String output = "";
    private final String[] stanStudentaLista = {"student I-stopnia studiów", "student studiów stacjonarnych", "uczestnik programu ERASMUS", "student II-stopnia studiów", "student studiów niestacjonarnych"};
    private String[] stanStudenta;
    private final String[] kategorieStudent;
    private ArrayList<Kurs> kursy = new ArrayList<>();
    private ArrayList<Double> oceny = new ArrayList<>();

    private StudentObserver observer = new StudentObserver(this);
    private boolean czyZdaje;

    public Student(String[] args) {
        super(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]));
        this.numerIndeksu = Integer.parseInt(args[5]);
        this.rokStudiow = Integer.parseInt(args[6]);
        stanStudenta = args[7].split(",");
        for (int i = 0; i < stanStudenta.length; i++){
            if (stanStudenta[i].equals("TAK"))
                output += stanStudentaLista[i] + ", ";
        }
        if (!output.equals("")){
        output = output.substring(0, output.length()-2);}
        this.kategorieStudent = new String[]{String.valueOf(numerIndeksu), String.valueOf(rokStudiow)};

    }

    public void writeKursy(){
        for (Kurs kurs : kursy){
            kurs.printStan();
        }
    }

    public ArrayList<Kurs> getKursy(){return kursy;}

    public void printStan(){
        super.printStan();
        System.out.println("Numer indeksu: " + numerIndeksu + "\nRok studiów: " + rokStudiow +
                "\nStan studenta: " + output);
        writeKursy();
        System.out.println("--------------------------------------------------");
    }

    public int search(int kategoriaID, String valueSearchCategory){
        kategoriaID = super.search(kategoriaID, valueSearchCategory);
        if (kategoriaID < kategorieStudent.length && kategoriaID >= 0){
            if (kategorieStudent[kategoriaID].equals(valueSearchCategory)){
                printStan();
                return 0;
            }
        }
        return -1;
    }

    @Override
    public ArrayList<Object> getStan() {
        ArrayList<Object> obj = new ArrayList<>();
        obj.addAll(super.getStan());
        obj.add(numerIndeksu);
        obj.add(rokStudiow);
        obj.add(output);
        return obj;
    }

    public void startKursu(Kurs kurs){kursy.add(kurs); notifyObserver("ECTS");}
    public void koniecKursu(Kurs kurs) {kursy.remove(kurs); notifyObserver("ECTS");}

    public void dodajOcene(Double ocena){oceny.add(ocena); notifyObserver("ocena");}
    public ArrayList<Double> getOceny() {return oceny;}

    public void setCzyZdaje(boolean czyZdaje) {this.czyZdaje = czyZdaje;}
    public boolean getCzyZdaje() {return czyZdaje;}

    public void registerObserver(StudentObserver observer)
    {
        this.observer = observer;
    }
    public void removeObserver()
    {
        this.observer = null;
    }

    private void notifyObserver(String value){
        observer.update(value);
    }
}
