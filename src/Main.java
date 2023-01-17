import GUI.GUI;
import Management.ManagementUczelni;

public class Main {
    public static void main(String[] args) {
        ManagementUczelni manager = new ManagementUczelni();
        new GUI(manager);
    }
}