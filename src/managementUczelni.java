import java.util.ArrayList;

public class managementUczelni {
    private ArrayList<Osoba> osoby = new ArrayList<>();
    private ArrayList<Kurs> kursy = new ArrayList<>();
    private final FileChanges fileHandler;

    managementUczelni(){
        fileHandler = new FileChanges(this);
//        fileHandler.readData();
        initializeOsoby();
        initializeKursy();
//        ((Student) osoby.get(0)).startKursu(kursy.get(1));
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

    public void wyszukanie(String klasaID, int kategoriaID, String searchValue){
        if (klasaID.contains("S") || klasaID.contains("P"))
            wyszukanieOsoby(klasaID, kategoriaID, searchValue);
        else if (klasaID.contains("K"))
            wyszukanieKursu(kategoriaID, searchValue);
    }

    private void wyszukanieOsoby(String klasa, int kategoriaID, String searchValue){
        for (Osoba osoba : osoby){
            if (osoba.getClass().getName().contains(klasa)){
                if (kategoriaID >= 0)
                    osoba.search(kategoriaID, searchValue);
                else
                    osoba.getStan();
            }
        }
    }

    private void wyszukanieKursu(int kategoriaID, String valueSearchCategory){
        for (Kurs kurs : kursy){
            if (kategoriaID >= 0){
                int returnVal = kurs.search(kategoriaID, valueSearchCategory);
                if (returnVal == 0){
                    System.out.println("Not suitable...");
                }
            } else {
                kurs.getStan();
            }
        }
    }

    public void saveData(){fileHandler.saveData(osoby, kursy);}

    public void writeDownAll(){
        for (Osoba osoba : osoby) osoba.getStan();
        for (Kurs kurs : kursy) kurs.getStan();
    }

    public ArrayList<Kurs> getKursy() {return kursy;}
    public ArrayList<Osoba> getOsoby() {return osoby;}
    public void setKursy(ArrayList<Kurs> kursy) {this.kursy = kursy;}
    public void setOsoby(ArrayList<Osoba> osoby) {this.osoby = osoby;}
}
