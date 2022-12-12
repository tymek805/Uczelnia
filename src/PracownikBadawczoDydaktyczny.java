public class PracownikBadawczoDydaktyczny extends PracownikUczelni{
    private int liczbaPublikacji;
    private String[] stanowsika = {"Asystent", "Adiunkt", "Profesor Nadzwyczajny", "Profesor Zwyczajny", "Wykładowca"};
    private String stanowisko;
    private String[] kategoriePracownikBD;

    public PracownikBadawczoDydaktyczny(String imie, String nazwisko, String pesel, String gender, int wiek, int staz, int pensja,
                                 int liczbaPublikacji, int stanowiskoID) {
        super(imie, nazwisko, pesel, gender, wiek, staz, pensja);
        stanowisko = stanowsika[stanowiskoID];
        this.liczbaPublikacji = liczbaPublikacji;
        this.kategoriePracownikBD = new String[]{stanowisko, String.valueOf(liczbaPublikacji)};
    }

    public PracownikBadawczoDydaktyczny(String[] args) {
        super(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
        this.liczbaPublikacji = Integer.parseInt(args[7]);
        stanowisko = stanowsika[Integer.parseInt(args[8])];
        this.kategoriePracownikBD = new String[]{stanowisko, String.valueOf(liczbaPublikacji)};
    }

    public void getStan(){
        super.getStan();
        System.out.println("Liczba publikacji: " + liczbaPublikacji + "\nStanowisko: " + stanowisko);

        System.out.println("--------------------------------------------------");
    }

    public int search(int kategoriaID, String valueSearchCategory){
        kategoriaID = super.search(kategoriaID, valueSearchCategory);
        if (kategoriaID < kategoriePracownikBD.length && kategoriaID >= 0){
            if (kategoriePracownikBD[kategoriaID].equals(valueSearchCategory)){
                getStan();
                return 0;
            }
        } else if (kategoriaID < 0) {
            return 0;
        }
        return -1;
    }

    public String[] getKategorie(){
        super.getKategorie();
        return kategoriePracownikBD;
    }
}
