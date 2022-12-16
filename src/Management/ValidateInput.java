package Management;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ValidateInput {
    private Scanner scanner;
    public ValidateInput(){
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

    public  int arrayIdxValidator(int arrayLength, int bottomCheck){
        int inputINT;
        try{
            inputINT = scanner.nextInt();
            if (inputINT >= arrayLength || inputINT < bottomCheck) throw new InputMismatchException();
        }catch (InputMismatchException e){
            System.err.print("Niepoprawna wartość!\n-> ");
            inputINT = arrayIdxValidator(arrayLength, bottomCheck);
        }
        return inputINT;
    }

    public boolean answerYesOrNoValidator(){
        String input = scanner.next().toLowerCase();
        while (!input.matches("t|f|true|false|tak|nie")){
            System.err.print("Niepoprawna wartość!\n-> ");
            input = scanner.next().toLowerCase();
        }
        return input.matches("t|true|tak");
    }

    public Scanner getScanner() {return scanner;}
}
