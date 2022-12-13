import java.io.*;
import java.util.ArrayList;

public class FileChanges implements Serializable {
    private final managementUczelni manager;
    private final String pathFile = "daneUczelnia.txt";
    private final String pathBackup = "backupData.txt";

    public FileChanges(managementUczelni manager){
        this.manager = manager;
    }

    public void saveData(ArrayList<Osoba> osoby, ArrayList<Kurs> kursy) {
        if (saveFile(osoby, kursy, pathFile) == 0){
            if (saveFile(osoby, kursy, pathBackup) == 0)
                System.out.println("Zapisywanie danych przebiegło pomyślnie!");
            else
                System.out.println("Nie udało się utworzyć kopii zapasowej!");

        }else System.out.println("Nie udało się zapisać danych!\n" +
                "W przypadku utraty danych proszę skorzystać z kopii zapasowej");
    }

    public void readData(){
        ArrayList<Object> objectsList = readFile(pathFile);

        ArrayList<Osoba> osoby = manager.getOsoby();
        ArrayList<Kurs> kursy = manager.getKursy();
        for (Object obj:objectsList) {
            if (obj instanceof Osoba) osoby.add((Osoba)obj);
            else if (obj instanceof Kurs) kursy.add((Kurs)obj);
        }
        manager.setOsoby(osoby);
        manager.setKursy(kursy);
    }

    private ArrayList<Object> readFile(String path) {
        ArrayList<Object> objectsList = new ArrayList<>();

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(path))) {
            boolean isMore = true;
            while (isMore){
                Object obj = input.readObject();
                if (obj != null) objectsList.add(obj);
                else isMore = false;
            }
        }catch (FileNotFoundException | StreamCorruptedException e){
            e.printStackTrace();
            if (!path.equals(pathBackup)){
                System.out.println("File cannot be found!\n" +
                        "Reading data from backup file...");
                objectsList = readFile(pathBackup);
            }else {
                System.out.println("Reading backup not available!");
                System.exit(-1);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return objectsList;
    }

    private int saveFile(ArrayList<Osoba> osoby, ArrayList<Kurs> kursy, String path){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path))) {

            for (Osoba osoba:osoby) outputStream.writeObject(osoba);
            for (Kurs kurs:kursy)outputStream.writeObject(kurs);
            outputStream.writeObject(null);

            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
