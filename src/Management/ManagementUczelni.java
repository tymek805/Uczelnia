package Management;

import Objects.*;
import java.util.ArrayList;
import java.util.Objects;

public class ManagementUczelni {
    private ArrayList<Osoba> osoby = new ArrayList<>();
    private ArrayList<Kurs> kursy = new ArrayList<>();
    private final FileChanges fileHandler;

    public ManagementUczelni(){
        fileHandler = new FileChanges(this);
        fileHandler.readData();
//        initializeOsoby();
//        initializeKursy();
    }

    private void initializeOsoby(){
        osoby.add(new Student(new String[]{"Kacper", "Nowak", "84012078817", "M", "19",
                "2742", "1", "true,false,true,true,false"}));

        osoby.add(new Student(new String[]{"Daria", "Zawadzka", "68122164842", "K", "21",
                "2746", "2", "false,true,false,true,false"}));

        osoby.add(new PracownikAdministracyjny(new String[]{"Jan", "Pawlak", "75081232291", "M", "39",
                "10", "7000", "30", "2"}));

        osoby.add(new PracownikAdministracyjny(new String[]{"Beata", "Malinowska", "56101858367", "K", "49",
                "8", "7000", "14", "1"}));

        osoby.add(new PracownikBadawczoDydaktyczny(new String[]{"Jan", "Witkowski", "56062467691", "M", "57",
                "24", "7000", "12", "0"}));

        osoby.add(new PracownikBadawczoDydaktyczny(new String[]{"Zuzanna", "Czarnecka", "63060351145", "K", "43",
                "6", "7000", "8", "3"}));
    }
    private void initializeKursy(){
        kursy.add(new Kurs(new String[]{"Matematyka", "Jastrzębski", "10"}));
        kursy.add(new Kurs(new String[]{"Język obcy nowożytny", "Karpacka", "15"}));
    }

    public ArrayList<Object> wyszukiwanie(String klasaID, int kategoriaID, String searchValue){
        if (klasaID.contains("S") || klasaID.contains("P"))
            return wyszukanieOsoby(klasaID, kategoriaID, searchValue);
        else if (klasaID.contains("K"))
            return wyszukanieKursu(kategoriaID, searchValue);
        return null;
    }

    private ArrayList<Object> wyszukanieOsoby(String klasa, int kategoriaID, String searchValue){
        ArrayList<Object> wyszukaneOsoby = new ArrayList<>();
        for (Osoba osoba : osoby){
            if (osoba.getClass().getName().contains(klasa)){
                if (kategoriaID >= 0 && osoba.search(kategoriaID, searchValue) == 0)
                    wyszukaneOsoby.add(osoba);
                else
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
            else
                wyszukaneKursy.add(kurs);
        }
        return wyszukaneKursy;
    }

    public void saveData(){fileHandler.saveData(osoby, kursy);}

    public void writeAllDown(){
        for (Osoba osoba : osoby) osoba.getStan();
        for (Kurs kurs : kursy) kurs.getStan();
    }

    public ArrayList<Kurs> getKursy() {return kursy;}
    public ArrayList<Osoba> getOsoby() {return osoby;}
    public void setKursy(ArrayList<Kurs> kursy) {this.kursy = kursy;}
    public void setOsoby(ArrayList<Osoba> osoby) {this.osoby = osoby;}
}
