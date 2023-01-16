package Management;

import Objects.*;
import Obserwator.StudentObserver;

import java.util.ArrayList;
import java.util.Arrays;

public class ManagementUczelni {
    private ArrayList<Osoba> osoby = new ArrayList<>();
    private ArrayList<Kurs> kursy = new ArrayList<>();
    private final FileChanges fileHandler;

    public ManagementUczelni(){
        fileHandler = new FileChanges(this);
        fileHandler.readData();
//        initializeOsoby();
//        initializeKursy();
        start();
    }

    private void initializeOsoby(){
        osoby.add(new Student(new String[]{"Kacper", "Nowak", "84012078817", "Mężczyzna", "19",
                "2742", "1", "TAK,NIE,TAK,TAK,NIE"}));

        osoby.add(new Student(new String[]{"Daria", "Zawadzka", "68122164842", "Kobieta", "21",
                "2746", "2", "NIE,TAK,NIE,TAK,NIE"}));

        osoby.add(new PracownikAdministracyjny(new String[]{"Jan", "Pawlak", "75081232291", "Mężczyzna", "39",
                "10", "7000", "30", "Starszy Specjalista"}));

        osoby.add(new PracownikAdministracyjny(new String[]{"Beata", "Malinowska", "56101858367", "Kobieta", "49",
                "8", "7000", "14", "Specjalista"}));

        osoby.add(new PracownikBadawczoDydaktyczny(new String[]{"Jan", "Witkowski", "56062467691", "Mężczyzna", "57",
                "24", "7000", "12", "Asystent"}));

        osoby.add(new PracownikBadawczoDydaktyczny(new String[]{"Zuzanna", "Czarnecka", "63060351145", "Kobieta", "43",
                "6", "7000", "8", "Profesor Zwyczajny"}));

    }
    private void initializeKursy(){
        kursy.add(new Kurs(new String[]{"Matematyka", "Jastrzębski", "10"}));
        kursy.add(new Kurs(new String[]{"Język obcy nowożytny", "Karpacka", "15"}));
    }

    public ArrayList<Object> wyszukiwanie(String klasa, int kategoriaID, String searchValue){
        if (klasa.equals("Student") || klasa.contains("Pracownik"))
            return wyszukanieOsoby(klasa, kategoriaID, searchValue);
        else if (klasa.equals("Kurs"))
            return wyszukanieKursu(kategoriaID, searchValue);
        return null;
    }

    private ArrayList<Object> wyszukanieOsoby(String klasa, int kategoriaID, String searchValue){
        ArrayList<Object> wyszukaneOsoby = new ArrayList<>();
        for (Osoba osoba : osoby){
            if (osoba.getClass().getSimpleName().contains(klasa)){
                if (kategoriaID >= 0 && osoba.search(kategoriaID, searchValue) == 0)
                    wyszukaneOsoby.add(osoba);
                else if (kategoriaID == -1)
                    wyszukaneOsoby.add(osoba);
            }
        }
        return wyszukaneOsoby;
    }

    private ArrayList<Object> wyszukanieKursu(int kategoriaID, String valueSearchCategory){
        ArrayList<Object> wyszukaneKursy = new ArrayList<>();
        for (Kurs kurs : kursy){
            if (kategoriaID >= 0 && kurs.search(kategoriaID, valueSearchCategory) == 0)
                wyszukaneKursy.add(kurs);
            else if (kategoriaID == -1)
                wyszukaneKursy.add(kurs);
        }
        return wyszukaneKursy;
    }

    public void delete(String klasa, int kategoriaID, String searchValue){
        ArrayList<Object> deleteObjectsArray = wyszukiwanie(klasa, kategoriaID, searchValue);
        for (Object object:deleteObjectsArray){
            if (object instanceof Osoba) osoby.remove(object);
            else if (object instanceof Kurs) kursy.remove(object);
        }
        fileHandler.saveData(osoby, kursy);
    }

    public void saveData(){fileHandler.saveData(osoby, kursy); System.exit(0);}

    public void writeAllDown(){
        for (Osoba osoba : osoby) osoba.printStan();
        for (Kurs kurs : kursy) kurs.printStan();
    }

    public ArrayList<String> otrzymajSkladowe(String optionCheck) {
        ArrayList<String> skladowe = new ArrayList<>();
        if (optionCheck.contains("Pracownik") || optionCheck.equals("Student") || optionCheck.equals("Osoba")) {
            skladowe.addAll(Arrays.asList("Imię", "Nazwisko", "Pesel", "Płeć", "Wiek"));
            if (optionCheck.contains("Pracownik")) {
                skladowe.addAll(Arrays.asList("Staż pracy", "Pensja"));
                if (optionCheck.equals("PracownikBadawczoDydaktyczny")) skladowe.add("Liczba publikacji");
                else if (optionCheck.equals("PracownikAdministracyjny")) skladowe.add("Liczba nadgodzin");
                skladowe.add("Stanowisko");
            } else if (optionCheck.equals("Student")) {
                skladowe.addAll(Arrays.asList("Numer indeksu", "Rok studiów", "Stan Studenta"));
            }
        } else if (optionCheck.equals("Kurs")) {
            skladowe.addAll(Arrays.asList("Nazwa", "Prowadzący", "Punkty ECTS"));
        }
        return skladowe;
    }

    // GET ~ SET ~ ADD
    public ArrayList<Kurs> getKursy() {return kursy;}
    public ArrayList<Osoba> getOsoby() {return osoby;}
    public void setKursy(ArrayList<Kurs> kursy) {this.kursy = kursy;}
    public void setOsoby(ArrayList<Osoba> osoby) {this.osoby = osoby;}

    public void addOsoba(Osoba osoba){osoby.add(osoba);}
    public void addKurs(Kurs kurs){kursy.add(kurs);}

    public void start(){
        ArrayList<StudentObserver> observers = new ArrayList<>();
        for (Osoba osoba : osoby)
            if (osoba instanceof Student){
                observers.add(new StudentObserver((Student) osoba));
                ((Student) osoba).registerObserver(new StudentObserver((Student) osoba));
            }
    }
}
