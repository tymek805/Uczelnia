public class Main {
    public static void main(String[] args){
        // ----------OSOBY----------
        Osoba[] osoby = new Osoba[6];
        osoby[0] = new Student("Kapcer", "Nowak", "84012078817", "M", 19,
                2742, 1, new boolean[]{true, false, true, true, false});

        osoby[1] = new Student("Daria", "Zawadzka", "68122164842", "K", 21,
                2746, 2, new boolean[]{false, true, false, true, false});

        osoby[2] = new PracownikAdministracyjny("Krzysztof", "Pawlak", "75081232291", "M", 39,
                10, 7000,30,2);

        osoby[3] = new PracownikAdministracyjny("Beata", "Malinowska", "56101858367", "K", 49,
                8, 7000, 14,1);

        osoby[4] = new PracownikBadawczoDydaktyczny("Jan", "Witkowski", "56062467691", "M", 57,
                24, 7000, 12,0);

        osoby[5] = new PracownikBadawczoDydaktyczny("Zuzanna", "Czarnecka", "63060351145", "K", 43,
                6, 7000,8,3);

        // ----------KURSY----------
        Kurs[] kursy = new Kurs[2];
        kursy[0] = new Kurs("Matematyka", "Jastrzębski", 10);
        kursy[1] = new Kurs("Język obcy nowożytny", "Karpacka", 15);

        ((Student)osoby[0]).rozpoczecieKursu(kursy[1]);
        // ----------WYSZUKIWARKA----------
        String[] klasy = {"Student", "Pracownik", "Kurs"};

//        String[] kategorieOsoba = {"Nazwisko", "Imię", "Pesel", "Płeć"};
//        String[] kategoriePracownik = {"Staż pracy", "Pensja", "Stanowisko", "Liczba nadgodzin", "Liczba Publikacji"};
//        String[] kategorieStudent = {"Numer indeksu", "Rok studiów", "Kurs"};
//        String[] kategorieKurs = {"Nazwa", "Prowadzący", "Punkty ECTS"};

        int klasaID = 0; // Wprowadzenie co chcemy wyszukać
        int kategoriaID = -1; // Wprowadzenie po czym chcemy wyszukać
        String valueSearchCategory = "15"; // Wprowadzenie wartości wyszukania

        // Wyszukiwanie osoby
        for (Osoba osoba : osoby){
            if (osoba.getClass().getName().contains(klasy[klasaID])){
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

        // Wyszukiwanie kursu
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
