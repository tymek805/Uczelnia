import GUI.GUI;
import Management.ManagementUczelni;

public class Main {
    public static void main(String[] args) {
        ManagementUczelni manager = new ManagementUczelni();
        new GUI(manager);
    }
}
//TODO Lista 7 etap 2
// Usuwanie wszystkich powtarzających się Osób przy użyciu kolekcji HashSet.
// Zakładamy, że równe obiekty w przypadku Studentów to takie, które posiadają taki sam numer indeksu,
// natomiast w przypadku Pracowników uczelni to takie, które posiadają taki sam numer PESEL.