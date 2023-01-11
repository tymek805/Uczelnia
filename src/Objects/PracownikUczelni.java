package Objects;

import java.util.ArrayList;

public abstract class PracownikUczelni extends Osoba {
    private int staz, pensja;
    private final String[] kategoriePracownik;
    PracownikUczelni(String imie, String nazwisko, String pesel, String gender, int wiek,
                     int staz, int pensja) {
        super(imie, nazwisko, pesel, gender, wiek);
        this.staz = staz;
        this.pensja = pensja;

        kategoriePracownik = new String[]{String.valueOf(staz), String.valueOf(pensja)};
    }

    public void printStan(){
        super.printStan();
        System.out.println("Staż pracy: " + staz + "\nPensja: " + pensja);
    }
    public int search(int kategoriaID, String valueSearchCategory){
        kategoriaID = super.search(kategoriaID, valueSearchCategory);
        if (kategoriaID < kategoriePracownik.length && kategoriaID >= 0){
            if (kategoriePracownik[kategoriaID].equals(valueSearchCategory)){
                printStan();
                return -1;
            }
        } else if (kategoriaID == -1) {
            return -1;
        }
        return kategoriaID - kategoriePracownik.length;
    }

    @Override
    public ArrayList<Object> getStan() {
        ArrayList<Object> obj = new ArrayList<>();
        obj.addAll(super.getStan());
        obj.add(staz);
        obj.add(pensja);
        return obj;
    }
}
