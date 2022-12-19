package Objects;

import java.io.Serializable;

public class Kurs implements Serializable {
    private final String nazwa, prowadzacy;
    private final int punktyECTS;
    private final String[] kategorieKurs;

    public Kurs(String[] args) {
        this.nazwa = args[0];
        this.prowadzacy = args[1];
        this.punktyECTS = Integer.parseInt(args[2]);

        kategorieKurs = new String[]{nazwa, prowadzacy, String.valueOf(punktyECTS)};
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

    public int getPunktyECTS() {return punktyECTS;}
    public String getProwadzacy() {return prowadzacy;}

    public void getStan(){
        System.out.println(this.getClass().getSimpleName());
        System.out.println("Nazwa: " + nazwa + "\nProwadzący: "+ prowadzacy + "\nPunkty ECTS: " + punktyECTS);
        System.out.println("--------------------------------------------------");
    }
}
