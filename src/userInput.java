import java.util.*;

public class userInput {
    private final Scanner scanner;
    private final managementUczelni manager;
    private ArrayList<Osoba> osobaArrayList;
    private ArrayList<Kurs> kursArrayList;
    private boolean stillRunning = true;

    public userInput(managementUczelni manager) {
        this.manager = manager;
        scanner = new Scanner(System.in);
        osobaArrayList = manager.getOsoby();
        kursArrayList = manager.getKursy();
        startInput();
    }

    private void startInput() {
        String featuresText = """
                (dodaj) Dodanie obiektu
                (wyszukaj) Wyszukaj obiekty
                (wypisz) Wyświetl wszystkie obiekty
                (pomoc) Wyświetl możliwe komendy
                (koniec) Kończy działanie programu""";
        String separator = "/////////////////////////////////////////////\n";
        System.out.println(separator +
                "Witaj w projekcie Uczelnia!");
        while (stillRunning) {
            System.out.print(separator);
            System.out.println("Oto lista możliwych operacji: \n\n" + featuresText);
            System.out.print("-> ");
            String input = scanner.next();
            System.out.println();
            switch (input) {
                case ("koniec"):
                    finalizer();
                    break;

                case ("dodaj"):
                    dodanieObiektu();
                    break;

                case ("wypisz"):
                    manager.writeDownAll();
                    break;

                case ("wyszukaj"):
                    wyszukaj();
                    break;

                case ("pomoc"):
                    break;
                default:
                    System.out.println("Nieznana funkcja!");
            }
        }
        System.out.println(separator);
    }

    private void dodanieObiektu() {
        System.out.println("""
                Dla dodania obiektu wpisz:
                (PA) PracownikAdministracyjny
                (PBD) PracownikBadawczoDydaktyczny
                (S) Student
                (K) Kurs""");

        String objectType = scanner.next().toUpperCase();
        while(!objectType.matches("S|PA|PBD|K")){
            System.err.print("Niepoprawny typ obiektu!\n -> ");
            objectType = scanner.next().toUpperCase();
        }
        ArrayList<String> skladowe = otrzymajSkladowe(objectType);
        String[] args = new String[skladowe.size()];

        for (int i = 0; i < skladowe.size(); i++) {
            System.out.print("Proszę wprowadzić " + skladowe.get(i) + ": ");
            if (skladowe.get(i).equals("Stan Studenta")) args[i] = studentHandler();
            else args[i] = scanner.next();
        }

        switch (objectType) {
            case ("PBD"):
                PracownikBadawczoDydaktyczny pracownikBadawczoDydaktyczny = new PracownikBadawczoDydaktyczny(args);
                osobaArrayList.add(pracownikBadawczoDydaktyczny);
                break;
            case ("PA"):
                PracownikAdministracyjny pracownikAdministracyjny = new PracownikAdministracyjny(args);
                osobaArrayList.add(pracownikAdministracyjny);
                break;
            case ("S"):
                Student student = new Student(args);
                osobaArrayList.add(student);
                break;
            case ("K"):
                Kurs kurs = new Kurs(args);
                kursArrayList.add(kurs);
                break;
        }
    }

    private void wyszukaj() {
        HashMap<String, String> klasy = new HashMap<>();
        klasy.put("S", "Student");
        klasy.put("P", "Pracownik");
        klasy.put("PBD", "Pracownik");
        klasy.put("PA", "Pracownik");
        klasy.put("K", "Kurs");

        // Pobranie klasy
        System.out.println("Co chcesz wyszukać?");
        System.out.print("""
                (S) Studenta
                (P) Wszystkich pracowników
                (PA) Pracownika Administracyjnego
                (PBD) Pracownika Badawczo-Dydaktycznego
                (K) Kurs
                 ->\s""");
        String objectCheck = scanner.next().toUpperCase();
        while(!objectCheck.matches("S|P|PA|PBD|K")){
            System.err.print("Niepoprawny typ obiektu!\n -> ");
            objectCheck = scanner.next().toUpperCase();
        }
        ArrayList<String> skladowe = otrzymajSkladowe(objectCheck);
        if (objectCheck.equals("S")){skladowe.remove(skladowe.indexOf("Stan Studenta"));}
        System.out.println("\nProszę wybrać filtr wyszukania: ");
        for (int i = 0; i < skladowe.size(); i++) {
            System.out.println("(" + (i + 1) + ") " + skladowe.get(i));
        }
        System.out.println("(0) Wypisz wszystkie obiekty");
        System.out.print(" -> ");

        // Pobieranie kategorii
        int kategoryCheck;
        try {
            kategoryCheck = scanner.nextInt() - 1;
            if (kategoryCheck > skladowe.size() || kategoryCheck < -1) throw new InputMismatchException();
        }catch (InputMismatchException e){
            try{Thread.sleep(10);
            }catch (InterruptedException ie){ie.printStackTrace();}

            System.err.println("Błędna kategoria!");
            return;
        }

        // Pobieranie wartości filtru
        String searchValue = "";
        if (kategoryCheck != -1){
            System.out.print("Wpisz wartość filtru\n -> ");
            searchValue = scanner.next();
        }
        manager.wyszukanie(klasy.get(objectCheck), kategoryCheck, searchValue);
    }

    private ArrayList<String> otrzymajSkladowe(String optionCheck) {
        ArrayList<String> skladowe = new ArrayList<>();
        if (optionCheck.contains("P") || optionCheck.equals("S")) {
            skladowe.addAll(Arrays.asList("Imię", "Nazwisko", "Pesel", "Płeć", "Wiek"));

            if (optionCheck.contains("P")) {
                skladowe.addAll(Arrays.asList("Staż pracy", "Pensja", "Stanowisko"));

                if (optionCheck.equals("PBD")) skladowe.add("Liczba publikacji");
                else if (optionCheck.equals("PA")) skladowe.add("Liczba nadgodzin");
            } else if (optionCheck.equals("S")) {
                skladowe.addAll(Arrays.asList("Numer indeksu", "Rok studiów", "Stan Studenta"));
                // TODO zapytanie - czy dodać kurs
            }
        } else if (optionCheck.equals("K")) {
            skladowe.addAll(Arrays.asList("Nazwa", "Prowadzący", "Punkty ECTS"));
        } else {
            System.out.println("Formuła nierozpoznana!");
            return null;
        }
        return skladowe;
    }

    private String studentHandler(){
        String[] stanStudenta = {"uczestnik programu ERASMUS", "student I-stopnia studiów", "student II-stopnia studiów", "student studiów stacjonarnych", "student studiów niestacjonarnych"};
        String output = "";
        System.out.println("(T) dla tak\n(F) dla nie");
        for (int j = 0; j < stanStudenta.length; j++) {
            System.out.print("Czy jest to " + stanStudenta[j] + "?\n -> ");
            String input = scanner.next().toUpperCase();
            while (!input.matches("T|F")){
                System.err.print("Niepoprawna wartość!\n -> ");
                input = scanner.next().toUpperCase();
            }
            output += input;
            if (j < stanStudenta.length - 1) output += ",";
        }
        return output;
    }

    private void finalizer() {
        manager.setOsoby(osobaArrayList);
        manager.setKursy(kursArrayList);
        stillRunning = false;
    }
}
