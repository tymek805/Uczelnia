public class Kurs {
    String nazwa, prowadzacy;
    int punktyECTS;
    String[] kategorieKurs;

    public Kurs(String nazwa, String prowadzacy, int punktyECTS){
        this.nazwa = nazwa;
        this.prowadzacy = prowadzacy;
        this.punktyECTS = punktyECTS;

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
}
