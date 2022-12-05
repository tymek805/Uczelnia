import java.util.ArrayList;

public class managementUczelni {
    private ArrayList<Osoba> osoby = new ArrayList<>();
    private ArrayList<Kurs> kursy = new ArrayList<>();
    private final String[] klasy = {"Student", "Pracownik", "Kurs"};

    managementUczelni(){
        initializeKursy();
        initializeOsoby();
        ((Student) osoby.get(0)).rozpoczecieKursu(kursy.get(1));
    }
    private void initializeOsoby(){
        osoby.add(new Student("Kacper", "Nowak", "84012078817", "M", 19,
                2742, 1, new boolean[]{true, false, true, true, false}));

        osoby.add(new Student("Daria", "Zawadzka", "68122164842", "K", 21,
                2746, 2, new boolean[]{false, true, false, true, false}));

        osoby.add(new PracownikAdministracyjny("Jan", "Pawlak", "75081232291", "M", 39,
                10, 7000,30,2));

        osoby.add(new PracownikAdministracyjny("Beata", "Malinowska", "56101858367", "K", 49,
                8, 7000, 14,1));

        osoby.add(new PracownikBadawczoDydaktyczny("Jan", "Witkowski", "56062467691", "M", 57,
                24, 7000, 12,0));

        osoby.add(new PracownikBadawczoDydaktyczny("Zuzanna", "Czarnecka", "63060351145", "K", 43,
                6, 7000,8,3));
    }

    private void initializeKursy(){
        kursy.add(new Kurs("Matematyka", "Jastrzębski", 10));
        kursy.add(new Kurs("Język obcy nowożytny", "Karpacka", 15));
    }

    public void wyszukanie(int klasaID, int kategoriaID, String valueSearchCategory){
        if (klasaID == 0 || klasaID == 1){
            wyszukanieOsoby(klasy[klasaID], kategoriaID, valueSearchCategory);
        } else if (klasaID == 2) {
            wyszukanieKursu(kategoriaID, valueSearchCategory);
        }
    }

    public void wyszukanieOsoby(String klasa, int kategoriaID, String valueSearchCategory){
        for (Osoba osoba : osoby){
            if (osoba.getClass().getName().contains(klasa)){
                if (kategoriaID >= 0){
                    int returnVal = osoba.search(kategoriaID, valueSearchCategory);
                    if (returnVal == 0){
                        System.out.println("Not suitable...");
                    }
                } else {
                    osoba.getStan();
                }
            }
        }
    }
    public void wyszukanieKursu(int kategoriaID, String valueSearchCategory){
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
}
