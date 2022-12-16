package Objects;

public class PracownikBadawczoDydaktyczny extends PracownikUczelni{
    private int liczbaPublikacji;
    private final String[] stanowiska = {"Asystent", "Adiunkt", "Profesor Nadzwyczajny", "Profesor Zwyczajny", "Wykładowca"};
    private String stanowisko;
    private final String[] kategoriePracownikBD;

    public PracownikBadawczoDydaktyczny(String[] args) {
        super(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
        this.liczbaPublikacji = Integer.parseInt(args[7]);
        stanowisko = stanowiska[Integer.parseInt(args[8])];
        this.kategoriePracownikBD = new String[]{String.valueOf(liczbaPublikacji), stanowisko};
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
}
