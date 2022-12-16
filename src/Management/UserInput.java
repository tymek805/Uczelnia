package Management;

import Objects.*;
import java.util.*;

public class UserInput extends ValidateInput {
    private final Scanner scanner;
    private final ManagementUczelni manager;
    private final ArrayList<Osoba> osobaArrayList;
    private final ArrayList<Kurs> kursArrayList;
    private HashMap<String, String> klasy;
    private HashMap<String, String[]> allDeleteCategories;
    private boolean stillRunning = true;

    public UserInput(ManagementUczelni manager) {
        super();
        this.manager = manager;
        scanner = getScanner();
        osobaArrayList = manager.getOsoby();
        kursArrayList = manager.getKursy();
        bigValueSetter();
        startInput();
    }

    private void startInput() {
        String featuresText = """
                (dodaj) Dodanie obiektu
                (wyszukaj) Wyszukaj obiekty
                (wypisz) Wyświetl wszystkie obiekty
                (delete) Usuń wybrany obiekt
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
                case ("koniec") -> finalizer();
                case ("dodaj") -> dodanieObiektu();
                case ("wypisz") -> manager.writeAllDown();
                case ("wyszukaj") -> wyszukaj();
                case ("delete") -> usuwanieObiektu();
                case ("pomoc") -> System.out.print("");
                default -> System.out.println("Nieznana funkcja!");
            }
        }
        System.out.println(separator);
    }

    private void dodanieObiektu() {
        System.out.print("""
                Dla dodania obiektu wpisz:
                (PA) PracownikAdministracyjny
                (PBD) PracownikBadawczoDydaktyczny
                (S) Student
                (K) Kurs
                ->\s""");
        String objectType = objectTypeValidator();

        ArrayList<String> skladowe = otrzymajSkladowe(objectType);
        String[] args = new String[skladowe.size()];

        // todo dopracować wpisywanie. Testy dla wieku peselu etc.
        // todo ważne! dodać wybieranie stanowiska dla pracowników
        for (int i = 0; i < skladowe.size(); i++) {
            System.out.print("Proszę wprowadzić " + skladowe.get(i) + ": ");
            if (skladowe.get(i).equals("Stan Studenta")) args[i] = studentHandler();
            else args[i] = scanner.next();
        }

        Object obj = switch (objectType) {
            case ("PBD") -> new PracownikBadawczoDydaktyczny(args);
            case ("PA") -> new PracownikAdministracyjny(args);
            case ("S") -> {
                Student student = new Student(args);
                System.out.print("Czy chcesz dodać kurs dla danego studenta?\n -> ");
                if (answerYesOrNoValidator()){
                    System.out.println("Wybierz kurs: \n");
                    for (int i = 0; i < kursArrayList.size(); i++){
                        System.out.print("(" + (i + 1) + ") ");
                        kursArrayList.get(i).getStan();
                    }
                    System.out.print(" -> ");
                    int kursIDX = arrayIdxValidator(kursArrayList.size(), 1);
                    student.startKursu(kursArrayList.get(kursIDX));
                }
                yield student;
            }
            case ("K") -> new Kurs(args);
            default -> null;
        };
        if (obj instanceof Osoba) osobaArrayList.add((Osoba) obj);
        else if (obj instanceof Kurs) kursArrayList.add((Kurs) obj);
    }

    private void usuwanieObiektu() {
        // Wybieranie klasy obiektu do usunięcia
        System.out.print("""
                Dla usunięcia obiektu wpisz:
                (P) Pracownik
                (S) Student
                (K) Kurs
                ->\s""");
        String objectType = objectTypeValidator();

        // Wybieranie kategorii
        String[] deleteCategory = allDeleteCategories.get(objectType);
        System.out.println("Wybierz kategorię usuwania: ");
        for (int i = 0; i < deleteCategory.length; i++){
            System.out.println("(" + (i + 1) + ") " + deleteCategory[i]);
        }
        System.out.print("-> ");
        int deleteIDX = arrayIdxValidator(deleteCategory.length + 1, 1) - 1;

        // Obliczenie poprawnego indeksu kategorii
        ArrayList<String> skladowe = otrzymajSkladowe(objectType);
        if (objectType.equals("S")){skladowe.remove(skladowe.indexOf("Stan Studenta"));}
        deleteIDX = skladowe.indexOf(deleteCategory[deleteIDX]);

        // Wpisanie wartości wyszukania
        System.out.print("Wprowadź wartość kategorii\n-> ");
        String deleteValue = scanner.next();

        // Wyszukanie
        ArrayList<Object> deleteObjectsArray = manager.wyszukiwanie(klasy.get(objectType), deleteIDX, deleteValue);
        for (Object object:deleteObjectsArray){
            if (object instanceof Osoba) osobaArrayList.remove(object);
            else if (object instanceof Kurs) kursArrayList.remove(object);
        }
    }

    private void wyszukaj() {
        // Wybranie klasy obiektu do wyszukania
        System.out.println("Co chcesz wyszukać?");
        System.out.print("""
                (S) Studenta
                (P) Wszystkich pracowników
                (PA) Pracownika Administracyjnego
                (PBD) Pracownika Badawczo-Dydaktycznego
                (K) Kurs
                 ->\s""");
        String objectCheck = objectTypeValidator();
        ArrayList<String> skladowe = otrzymajSkladowe(objectCheck);
        if (objectCheck.equals("S")){skladowe.remove(skladowe.indexOf("Stan Studenta"));}

        // Wybranie kategorii
        System.out.println("\nProszę wybrać filtr wyszukania: ");
        for (int i = 0; i < skladowe.size(); i++) {
            System.out.println("(" + (i + 1) + ") " + skladowe.get(i));
        }
        System.out.print("(0) Wypisz wszystkie obiekty\n-> ");
        int kategoryCheck = arrayIdxValidator(skladowe.size() + 1, 0) - 1;

        // Wpisanie wartości wyszukania
        String searchValue = "";
        if (kategoryCheck != -1){
            System.out.print("Wpisz wartość filtru\n -> ");
            searchValue = scanner.next();
        }

        // Wyszukanie
        manager.wyszukiwanie(klasy.get(objectCheck), kategoryCheck, searchValue);
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
            }
        } else if (optionCheck.equals("K")) {
            skladowe.addAll(Arrays.asList("Nazwa", "Prowadzący", "Punkty ECTS"));
        }
        return skladowe;
    }

    private String studentHandler(){
        String[] stanStudenta = {"uczestnik programu ERASMUS", "student I-stopnia studiów", "student II-stopnia studiów", "student studiów stacjonarnych", "student studiów niestacjonarnych"};
        String output = "";
        for (int j = 0; j < stanStudenta.length; j++) {
            System.out.print("Czy jest to " + stanStudenta[j] + "?\n -> ");
            output += answerYesOrNoValidator();
            if (j < stanStudenta.length - 1) output += ",";
        }
        return output;
    }

    private void bigValueSetter(){
        klasy = new HashMap<>();
        klasy.put("S", "Student");
        klasy.put("P", "Pracownik");
        klasy.put("PBD", "Pracownik");
        klasy.put("PA", "Pracownik");
        klasy.put("K", "Kurs");
        allDeleteCategories = new HashMap<>();
        allDeleteCategories.put("P", new String[]{"Imię", "Nazwisko", "Staż pracy", "Stanowisko"});
        allDeleteCategories.put("PA", new String[]{"Imię", "Nazwisko", "Staż pracy", "Stanowisko"});
        allDeleteCategories.put("PBD", new String[]{"Imię", "Nazwisko", "Staż pracy", "Stanowisko"});
        allDeleteCategories.put("S", new String[]{"Imię", "Nazwisko", "Numer indeksu", "Rok studiów"});
        allDeleteCategories.put("K", new String[]{"Nazwa", "Prowadzący", "Punkty ECTS"});
    }

    private void finalizer() {
        manager.setOsoby(osobaArrayList);
        manager.setKursy(kursArrayList);
        stillRunning = false;
    }
}