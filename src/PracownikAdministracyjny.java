public class PracownikAdministracyjny extends PracownikUczelni{
    private int liczbaNadgodzin;
    private final String[] stanowsika = {"Referent", "Specjalista", "Starszy Specjalista", "Kierownik"};
    private String stanowisko;
    private final String[] kategoriePracownikA;

    public PracownikAdministracyjny(String[] args) {
        super(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
        this.liczbaNadgodzin = Integer.parseInt(args[7]);
        stanowisko = stanowsika[Integer.parseInt(args[8])];
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
