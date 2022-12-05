public abstract class PracownikUczelni extends Osoba {
    private int staz, pensja;
    private String[] kategoriePracownik;
    PracownikUczelni(String imie, String nazwisko, String pesel, String gender, int wiek,
                     int staz, int pensja) {
        super(imie, nazwisko, pesel, gender, wiek);
        this.staz = staz;
        this.pensja = pensja;

        kategoriePracownik = new String[]{String.valueOf(staz), String.valueOf(pensja)};
    }

    protected void getStan(){
        super.getStan();
        System.out.println("Staż pracy: " + staz + "\nPensja: " + pensja);
    }
    public int search(int kategoriaID, String valueSearchCategory){
        kategoriaID = super.search(kategoriaID, valueSearchCategory);
        if (kategoriaID < kategoriePracownik.length && kategoriaID >= 0){
            if (kategoriePracownik[kategoriaID].equals(valueSearchCategory)){
                getStan();
                return -1;
            }
        }
        return kategoriaID - kategoriePracownik.length;
    }
}
