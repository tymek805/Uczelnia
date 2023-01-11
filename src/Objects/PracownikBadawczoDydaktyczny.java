package Objects;

import java.util.ArrayList;

public class PracownikBadawczoDydaktyczny extends PracownikUczelni{
    private int liczbaPublikacji;
    private String stanowisko;
    private final String[] kategoriePracownikBD;

    public PracownikBadawczoDydaktyczny(String[] args) {
        super(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
        this.liczbaPublikacji = Integer.parseInt(args[7]);
        stanowisko = args[8];
        this.kategoriePracownikBD = new String[]{String.valueOf(liczbaPublikacji), stanowisko};
    }

    public void printStan(){
        super.printStan();
        System.out.println("Liczba publikacji: " + liczbaPublikacji + "\nStanowisko: " + stanowisko);
        System.out.println("--------------------------------------------------");
    }

    public int search(int kategoriaID, String valueSearchCategory){
        kategoriaID = super.search(kategoriaID, valueSearchCategory);
        if (kategoriaID < kategoriePracownikBD.length && kategoriaID >= 0){
            if (kategoriePracownikBD[kategoriaID].equals(valueSearchCategory)){
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
        obj.add(liczbaPublikacji);
        obj.add(stanowisko);
        return obj;
    }
}
