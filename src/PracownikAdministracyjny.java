public class PracownikAdministracyjny extends PracownikUczelni{
    private int liczbaNadgodzin;
    private String[] stanowsika = {"Referent", "Specjalista", "Starszy Specjalista", "Kierownik"};
    private String stanowisko;
    private String[] kategoriePracownikA;
    public PracownikAdministracyjny(String imie, String nazwisko, String pesel, String gender, int wiek, int staz, int pensja,
                             int liczbaNadgodzin, int stanowiskoID) {
        super(imie, nazwisko, pesel, gender, wiek, staz, pensja);
        stanowisko = stanowsika[stanowiskoID];
        this.liczbaNadgodzin = liczbaNadgodzin;
        kategoriePracownikA = new String[]{stanowisko, String.valueOf(liczbaNadgodzin)};
    }

    public void getStan(){
        super.getStan();
        System.out.println("Liczba nadgodzin: " + liczbaNadgodzin + "\nStanowisko: " + stanowisko);

        System.out.println("--------------------------------------------------");
    }

    public int search(int kategoriaID, String valueSearchCategory){
        kategoriaID = super.search(kategoriaID, valueSearchCategory);
        if (kategoriaID < kategoriePracownikA.length && kategoriaID >= 0){
            if (kategoriePracownikA[kategoriaID].equals(valueSearchCategory)){
                getStan();
                return 0;
            }
        } else if (kategoriaID < 0) {
            return 0;
        }
        return -1;
    }
}
