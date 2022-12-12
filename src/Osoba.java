import java.io.Serializable;
import java.util.HashMap;

public abstract class Osoba implements Serializable {
    private String imie, nazwisko, pesel, gender;
    private int wiek;
    private HashMap<String, String> genderDictionary= new HashMap<>();
    private String[] kategorieOsoba;

    Osoba(String imie, String nazwisko, String pesel, String gender, int wiek){
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.gender = gender;
        this.wiek = wiek;

        genderDictionary.put("M", "Mężczyzna");
        genderDictionary.put("K", "Kobieta");
        kategorieOsoba = new String[]{imie, nazwisko, pesel, genderDictionary.get(gender)};
    }

    protected void getStan(){
        System.out.println("Imię: " + imie + "\nNazwisko: "+ nazwisko + "\nPesel: " + pesel +
                "\nPłeć: "+ genderDictionary.get(gender) + "\nWiek: " + wiek);
    }

    public int search(int kategoriaID, String valueSearchCategory){
        if (kategoriaID < kategorieOsoba.length && kategoriaID >= 0){
            if (kategorieOsoba[kategoriaID].equals(valueSearchCategory)){
                getStan();
                return -1;
            }
        }
        return kategoriaID - kategorieOsoba.length;
    }

    public String[] getKategorie() {
        return kategorieOsoba;
    }
}
