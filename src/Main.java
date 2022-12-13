public class Main {
    public static void main(String[] args){
        managementUczelni manager = new managementUczelni();
        userInput input = new userInput(manager);
        manager.saveData();
        //todo dodać w dodawaniu/wyszukiwaniu wybór płci a nie wpisywanie
        //todo dodać wpisywanie kursu dla studenta
        //todo dodać dodawanie kursu do studenta "na bieżąco"
    }
}
