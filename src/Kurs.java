import java.io.Serializable;

public class Kurs implements Serializable {
    String nazwa, prowadzacy;
    int punktyECTS;
    String[] kategorieKurs;

    public Kurs(String nazwa, String prowadzacy, int punktyECTS){
        this.nazwa = nazwa;
        this.prowadzacy = prowadzacy;
        this.punktyECTS = punktyECTS;

        kategorieKurs = new String[]{nazwa, prowadzacy, String.valueOf(punktyECTS)};
    }

    public Kurs(String[] args) {
        this.nazwa = args[0];
        this.prowadzacy = args[1];
        this.punktyECTS = Integer.parseInt(args[2]);

        kategorieKurs = new String[]{nazwa, prowadzacy, String.valueOf(punktyECTS)};
    }

    protected void getStan(){
        System.out.println("Nazwa: " + nazwa + "\nProwadzący: "+ prowadzacy + "\nPunkty ECTS: " + punktyECTS);
        System.out.println("--------------------------------------------------");
    }

    public int search(int kategoriaID, String valueSearchCategory){
        if (kategoriaID < kategorieKurs.length && kategoriaID >= 0){
            if (kategorieKurs[kategoriaID].equals(valueSearchCategory)){
                getStan();
                return 0;
            }
        }
        return -1;
    }

    public String[] getKategorie(){
        return kategorieKurs;
    }
}
