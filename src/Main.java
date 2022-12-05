public class Main {
    public static void main(String[] args){
        managementUczelni manager = new managementUczelni();

        // {"Student", "Pracownik", "Kurs"}
        int klasaID = 1;

        // Osoba -> {"Imię", "Nazwisko", "Pesel", "Płeć"}
        // Student -> {"Numer indeksu", "Rok studiów", "Kurs"}
        // Pracownik -> {"Staż pracy", "Pensja", "Stanowisko", (*A)"Liczba nadgodzin", (*BD)"Liczba Publikacji"}
        // Kurs -> {"Nazwa", "Prowadzący", "Punkty ECTS"}
        int kategoriaID = 0;

        // Wprowadzenie wartości wyszukania
        String valueSearchCategory = "Jan";

        manager.wyszukanie(klasaID, kategoriaID, valueSearchCategory);
    }
}
