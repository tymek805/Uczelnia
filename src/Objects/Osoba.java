package Objects;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Osoba implements Serializable {
    private final String imie, nazwisko, pesel, gender;
    private final String[] kategorieOsoba;
    private int wiek;

    Osoba(String imie, String nazwisko, String pesel, String gender, int wiek){
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.gender = gender;
        this.wiek = wiek;

        kategorieOsoba = new String[]{imie, nazwisko, pesel, gender, String.valueOf(wiek)};
    }

    public int search(int kategoriaID, String valueSearchCategory){
        if (kategoriaID < kategorieOsoba.length && kategoriaID >= 0){
            if (kategorieOsoba[kategoriaID].equals(valueSearchCategory)){
                printStan();
                return -1;
            }
        }
        return kategoriaID - kategorieOsoba.length;
    }

    public String getNazwisko() {return nazwisko;}
    public String getImie() {return imie;}
    public int getWiek() {return wiek;}
    public String getPesel() {return pesel;}

    public ArrayList<Object> getStan(){
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(imie);
        obj.add(nazwisko);
        obj.add(pesel);
        obj.add(gender);
        obj.add(wiek);
        return obj;
    }

    public void printStan(){
        System.out.println(this.getClass().getSimpleName());
        System.out.println("Imię: " + imie + "\nNazwisko: "+ nazwisko + "\nPesel: " + pesel +
                "\nPłeć: "+ gender + "\nWiek: " + wiek);
    }
}
