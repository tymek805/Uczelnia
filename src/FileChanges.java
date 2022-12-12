import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class FileChanges implements Serializable {
    managementUczelni manager;
    public FileChanges(managementUczelni manager){this.manager = manager;}
    public void savaData(ArrayList<Osoba> osoby, ArrayList<Kurs> kursy) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("daneUczelnia.txt"))) {
            for (Osoba osoba:osoby) {
                outputStream.writeObject(osoba);
            }
            for (Kurs kurs:kursy){
                outputStream.writeObject(kurs);
            }
            outputStream.writeObject(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        ArrayList<Object> objectsList = new ArrayList<>();

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("daneUczelnia.txt"))) {
            boolean isMore = true;
            while (isMore){
                Object obj = input.readObject();
                if (obj != null) objectsList.add(obj);
                else isMore = false;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<Osoba> osoby = manager.getOsoby();
        ArrayList<Kurs> kursy = manager.getKursy();
        for (Object obj:objectsList) {
            if (obj instanceof Osoba) osoby.add((Osoba)obj);
            else if (obj instanceof Kurs) kursy.add((Kurs)obj);
        }
        manager.setOsoby(osoby);
        manager.setKursy(kursy);
    }
}
