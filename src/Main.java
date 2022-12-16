import Management.ManagementUczelni;
import Management.UserInput;

public class Main {
    public static void main(String[] args){
        ManagementUczelni manager = new ManagementUczelni();
        UserInput input = new UserInput(manager);
        manager.saveData();
        //todo dodać w dodawaniu/wyszukiwaniu wybór płci a nie wpisywanie
        //todo stanowisko validator
        //todo komparatory
    }
}
