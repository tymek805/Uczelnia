package Objects;

import java.util.ArrayList;

public class PracownikAdministracyjny extends PracownikUczelni{
    private int liczbaNadgodzin;
    private String stanowisko;
    private final String[] kategoriePracownikA;

    public PracownikAdministracyjny(String[] args) {
        super(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
        this.liczbaNadgodzin = Integer.parseInt(args[7]);
        stanowisko = args[8];
        this.kategoriePracownikA = new String[]{String.valueOf(liczbaNadgodzin), stanowisko};
    }

    public void printStan(){
        super.printStan();
        System.out.println("Liczba nadgodzin: " + liczbaNadgodzin + "\nStanowisko: " + stanowisko);
        System.out.println("--------------------------------------------------");
    }

    public int search(int kategoriaID, String valueSearchCategory){
        kategoriaID = super.search(kategoriaID, valueSearchCategory);
        if (kategoriaID < kategoriePracownikA.length && kategoriaID >= 0){
            if (kategoriePracownikA[kategoriaID].equals(valueSearchCategory)){
                printStan();
                return 0;
            }
        } else if (kategoriaID == -1) {
            return 0;
        }
        return -1;
    }

    @Override
    public ArrayList<Object> getStan() {
        ArrayList<Object> obj = new ArrayList<>();
        obj.addAll(super.getStan());
        obj.add(liczbaNadgodzin);
        obj.add(stanowisko);
        return obj;
    }
}
