import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class userInput {
    private Scanner scanner;
    private managementUczelni manager;
    ArrayList<Osoba> osobaArrayList;
    ArrayList<Kurs> kursArrayList;
    private boolean stillRunning = true;
    userInput(managementUczelni manager) {
        this.manager = manager;
        scanner = new Scanner(System.in);
        osobaArrayList = manager.getOsoby();
        kursArrayList = manager.getKursy();
        startInput();
    }
    private void startInput(){
        while (stillRunning) {
            System.out.println("Aby dodać obiekt wpisz 'dodaj'");
            String input = scanner.next();

            if(input.equals("koniec")) finalizer();
            else if (input.equals("dodaj")) dodanieObiektu();
            else System.out.println("Nieznana funkcja!");
        }
    }
    private void dodanieObiektu(){
        System.out.println("Dla dodania obiektu wpisz: \n" +
                "PA  -> PracownikAdministracyjny\n" +
                "PBD -> PracownikBadawczoDydaktyczny\n" +
                "S   -> Student\n" +
                "K   -> Kurs");

        String objectType = scanner.next().toUpperCase();
        ArrayList<String> skladowe = otrzymajSkladowe(objectType);
        String[] args = new String[skladowe.size()];

        for (int i = 0; i < skladowe.size(); i++){
            System.out.print("Proszę wprowadzić " + skladowe.get(i) + ": ");
            if (skladowe.get(i).equals("Stan Studenta")){
                String[] stanStudenta = {"uczestnik programu ERASMUS", "student I-stopnia studiów", "student II-stopnia studiów", "student studiów stacjonarnych", "student studiów niestacjonarnych"};
                String output = "";
                System.out.println("dla TAK pisz true\ndla NIE wpisz false");
                for (int j = 0; j < stanStudenta.length; j++){
                    System.out.print("Czy jest to " + stanStudenta[j] + "? ");
                    output += scanner.next();
                    if (j < stanStudenta.length - 1) output += ",";
                }
                args[i] = output;
            }else args[i] = scanner.next();
        }
        switch (objectType){
            case ("PBD"):
                PracownikBadawczoDydaktyczny pracownikBadawczoDydaktyczny = new PracownikBadawczoDydaktyczny(args);
                osobaArrayList.add(pracownikBadawczoDydaktyczny);
                break;
            case("PA"):
                PracownikAdministracyjny pracownikAdministracyjny = new PracownikAdministracyjny(args);
                osobaArrayList.add(pracownikAdministracyjny);
                break;
            case("S"):
                Student student = new Student(args);
                osobaArrayList.add(student);
                break;
            case("K"):
                Kurs kurs = new Kurs(args);
                kursArrayList.add(kurs);
                break;
            default:
                System.out.println("Niepoprawna formuła!");
        }
    }
    private ArrayList<String> otrzymajSkladowe(String optionCheck){
        ArrayList<String> skladowe = new ArrayList<>();
        if (optionCheck.equals("PBD") || optionCheck.equals("PA") || optionCheck.equals("S")) {
            skladowe.addAll(Arrays.asList("Imię", "Nazwisko", "Pesel", "Płeć", "Wiek"));

            if (optionCheck.equals("PBD") || optionCheck.equals("PA")){
                skladowe.addAll(Arrays.asList("Staż pracy", "Pensja", "Stanowisko"));

                if (optionCheck.equals("PBD")) skladowe.add("Liczba publikacji");
                else if (optionCheck.equals("PA")) skladowe.add("Liczba nadgodzin");
            }else if (optionCheck.equals("S")){
                skladowe.addAll(Arrays.asList("Numer indeksu", "Rok studiów", "Stan Studenta"));
                // TODO zapytanie - czy dodać kurs
            }
        } else if (optionCheck.equals("K")) {
            skladowe.addAll(Arrays.asList("Nazwa", "Prowadzący", "Punkty ECTS"));
        }else {
            System.out.println("Formuła nierozpoznana!");
            return null;
        }
        return skladowe;
    }

    private void finalizer(){
        manager.setOsoby(osobaArrayList);
        manager.setKursy(kursArrayList);
        stillRunning = false;
    }
}
