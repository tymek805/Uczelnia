package Management;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ValidateInput_OLD {
    private Scanner scanner;
    public ValidateInput_OLD(){
        scanner = new Scanner(System.in);
    }

    public String objectTypeValidator(){
        String objectType = scanner.next().toUpperCase();
        while(!objectType.matches("S|PA|PBD|K|P")){
            System.err.print("Niepoprawny typ obiektu!\n-> ");
            objectType = scanner.next().toUpperCase();
        }
        return objectType;
    }

    public int arrayIdxValidator(int arrayLength, int bottomCheck){
        int inputINT;
        try{
            inputINT = scanner.nextInt();
            if (inputINT >= arrayLength || inputINT < bottomCheck) throw new InputMismatchException();
        }catch (InputMismatchException e){
            System.err.print("Niepoprawna wartość!\n-> ");
            try{Thread.sleep(10);}
            catch (InterruptedException exception) {exception.printStackTrace();}
            inputINT = arrayIdxValidator(arrayLength, bottomCheck);
            try{Thread.sleep(10);}
            catch (InterruptedException exception) {exception.printStackTrace();}
        }
        return inputINT;
    }

    public int intValidator(){
        String input = scanner.next();
        int inputINT;
        try{
            inputINT = Integer.parseInt(input);
        }catch (NumberFormatException e){
            System.err.print("Niepoprawna wartość!\n-> ");
            inputINT = intValidator();
        }
        return inputINT;
    }

    public String genderValidator(){
        System.out.print("""
                \n(M)eżczyzna\n
                (K)obieta
                ->\s""");
        String objectType = scanner.next().toUpperCase();
        while(!objectType.matches("M|K")){
            System.err.print("Niepoprawna płeć!\n-> ");
            objectType = scanner.next().toUpperCase();
        }
        return objectType;
    }

    public int stanowiskoValidator(String objectType){
        String[] stanowiska = new String[0];
        if (objectType.equals("PBD")){
            stanowiska = new String[]{"Asystent", "Adiunkt", "Profesor Nadzwyczajny", "Profesor Zwyczajny", "Wykładowca"};
        } else if (objectType.equals("PA")) {
            stanowiska = new String[]{"Referent", "Specjalista", "Starszy Specjalista", "Kierownik"};
        }
        System.out.println();
        for (int i = 0; i < stanowiska.length; i++){
            System.out.println("(" + (i + 1) + ") " + stanowiska[i]);
        }
        int val = arrayIdxValidator(stanowiska.length + 1, 1) - 1;
        return val;
    }

    public boolean answerYesOrNoValidator(){
        String input = scanner.next().toUpperCase();
        while (!input.matches("T|F|TRUE|FALSE|TAK|NIE")){
            System.err.print("Niepoprawna wartość!\n-> ");
            input = scanner.next().toLowerCase();
        }
        return input.matches("T|TRUE|TAK");
    }

    public Scanner getScanner() {return scanner;}
}
