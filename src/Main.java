import GUI.GUI;
import Management.ManagementUczelni;

public class Main {
    public static void main(String[] args) {
        ManagementUczelni manager = new ManagementUczelni();
        GUI gui = new GUI(manager);
//        UserInput input = new UserInput(manager);
    }
}

//TODO Lista 6
// - Dodanie komparatorów, które sortują elementy ArrayList’y „Osoby” według:
//      a. Nazwiska,
//      b. Nazwiska i Imienia (imiona sortowane alfabetycznie dla tych samych nazwisk),
//      c. Nazwiska i Wieku (wiek sortowany malejąco dla tych samych nazwisk),
//      d. oraz listę „Kursy” wg. punktów ECTS oraz Nazwiska Prowadzącego.
// <->
// - Zaimplementuj wzorzec projektowy:
//      a. Strategia
//      b. Obserwator
// dla projektu "Uczelnia".


//TODO Lista 7 etap 2
// Usuwanie wszystkich powtarzających się Osób przy użyciu kolekcji HashSet.
// Zakładamy, że równe obiekty w przypadku Studentów to takie, które posiadają taki sam numer indeksu,
// natomiast w przypadku Pracowników uczelni to takie, które posiadają taki sam numer PESEL.