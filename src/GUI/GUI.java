package GUI;

import Komparatory.SortByECTS_Professor;
import Komparatory.SortByFullname;
import Komparatory.SortBySurname;
import Komparatory.SortBySurnameAge;
import Management.ManagementUczelni;
import Objects.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GUI {
    private JPanel currentDisplay;
    private final CardLayout cardLayout = new CardLayout();
    private final ManagementUczelni manager;
    private final String[] classValues = {"Student", "PracownikAdministracyjny", "PracownikBadawczoDydaktyczny", "Kurs"};

    public GUI(ManagementUczelni manager){
        this.manager = manager;
        startGUI();
    }

    private void startGUI(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(sidebarInitializer(), BorderLayout.LINE_START);
        currentDisplay = new JPanel(cardLayout);

        // Display 1 || Okienko początkowe
        JPanel container = new JPanel();
        JLabel headerLB = new JLabel("Witaj w projekcie Uczelnia!");
        headerLB.setHorizontalAlignment(JLabel.CENTER);
        container.add(headerLB, BorderLayout.PAGE_START);
        currentDisplay.add(container);

        // Standalone
        frame.add(currentDisplay, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }
    private JPanel sidebarInitializer(){
        JPanel functionPanel = new JPanel();
        functionPanel.setVisible(true);
        functionPanel.setLayout(new GridLayout(7, 1));

        // Wyszukaj
        JButton wyszukajBT = new JButton("Wyszukaj");
        wyszukajBT.addActionListener(this::wyszukaj);
        functionPanel.add(wyszukajBT);

        // Wypisz
        JButton wypiszBT = new JButton("Wyświetl");
        wypiszBT.addActionListener(this::wypisz);
        functionPanel.add(wypiszBT);

        // Dodaj
        JButton dodajBT = new JButton("Dodaj");
        dodajBT.addActionListener(this::dodaj);
        functionPanel.add(dodajBT);

        // Delete
        JButton deleteBT = new JButton("Usuń");
        deleteBT.addActionListener(this::wyszukaj);
        functionPanel.add(deleteBT);

        // Delete
        JButton sortBT = new JButton("Sortowanie");
        sortBT.addActionListener(this::sort);
        functionPanel.add(sortBT);

        // Blank
        JButton blankBT = new JButton("");
        blankBT.setEnabled(false);
        functionPanel.add(blankBT);

        // Koniec
        JButton koniecBT = new JButton("Zakończ");
        koniecBT.addActionListener(this::exitGUI);
        koniecBT.setForeground(Color.RED);
        functionPanel.add(koniecBT);

        return functionPanel;
    }

    private JTable  fillTable (String classValue, ArrayList<Object> objectArrayList){
        String[]  columnNames = manager.otrzymajSkladowe(classValue).toArray(new String[0]);

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){return false;}
        };

        for (Object obj: objectArrayList){
            ArrayList<Object> components = new ArrayList<>();
            if (obj instanceof Osoba) components = ((Osoba) obj).getStan();
            else if (obj instanceof Kurs) components = ((Kurs) obj).getStan();
            if (obj instanceof PracownikUczelni && classValue.equals("Pracownik")) components.remove(7);
            tableModel.addRow(components.toArray());
        }
        return new JTable(tableModel);
    }
    private JScrollPane makeTable (String classValue){
        ArrayList<Object> objectArrayList = manager.wyszukiwanie(classValue, -1 , null);
        return new JScrollPane(fillTable(classValue, objectArrayList));
    }

    private void classSelected(ActionEvent event){
        if (!event.getSource().toString().contains("invalid")) {
            JComboBox<?> source = (JComboBox<?>) event.getSource();

            String klasa = (String) source.getSelectedItem();
            ArrayList<String> skladowe = manager.otrzymajSkladowe(klasa);
            String[] args = new String[skladowe.size()];

            JPanel panel = (JPanel) source.getClientProperty("panel");
            if (panel.getComponents().length == 3) panel.remove(2);
            JPanel container = new JPanel(new BorderLayout());
            JPanel valuesContainer = new JPanel(new GridLayout(skladowe.size(), 1));
            for (int i = 0; i < skladowe.size(); i++) {
                String currentVal = skladowe.get(i);
                JPanel componentPanel = new JPanel(new FlowLayout());
                JLabel componentLabel = new JLabel(currentVal);
                componentPanel.add(componentLabel);
                if (currentVal.equals("Stan Studenta")) args[i] = "X";
                else {
                    if (currentVal.equals("Płeć")) {
                        JComboBox<String> genderCB = new JComboBox<>(new String[]{"Mężczyzna", "Kobieta"});
                        genderCB.setSelectedIndex(-1);
                        componentPanel.add(genderCB);
                    } else if (currentVal.equals("Stanowisko")) {
                        String[] stanowiska = new String[0];
                        if (klasa.equals(classValues[2]))
                            stanowiska = new String[]{"Asystent", "Adiunkt", "Profesor Nadzwyczajny", "Profesor Zwyczajny", "Wykładowca"};
                        else if (klasa.equals(classValues[1]))
                            stanowiska = new String[]{"Referent", "Specjalista", "Starszy Specjalista", "Kierownik"};

                        JComboBox<String> positionCB = new JComboBox<>(stanowiska);
                        positionCB.setSelectedIndex(-1);
                        componentPanel.add(positionCB);
                    } else {
                        JTextField componentTextField = new JTextField(JTextField.TRAILING);
                        componentPanel.add(componentTextField);
                    }
//                        case "Wiek", "Staż pracy", "Pensja", "Liczba publikacji", "Liczba nadgodzin", "Numer indeksu", "Rok studiów", "Punkty ECTS" ->
//                                args[i] = String.valueOf(intValidator());
//                        default -> args[i] = scanner.next();
                }
                valuesContainer.add(componentPanel);
            }
            container.add(valuesContainer, BorderLayout.CENTER);
            panel.add(container, BorderLayout.CENTER, 2);
            panel.revalidate();
            panel.revalidate();
        }
    }
    private void dodaj(ActionEvent event){
        JPanel panel = new JPanel(new BorderLayout());

        JComboBox<String> klasaCB = new JComboBox<>(classValues);
        klasaCB.putClientProperty("panel", panel);
        klasaCB.addActionListener(this::classSelected);
        klasaCB.setSelectedIndex(-1);

        JButton confirmBT = new JButton("DODAJ");
        confirmBT.addActionListener(e -> {
            // todo Wprowadź co jeśli brakuje
            if (klasaCB.getSelectedIndex() == -1){
            }else {
                JPanel valuePanel = (JPanel) ((JPanel) panel.getComponent(2)).getComponent(0);
                int length = valuePanel.getComponents().length;

                String[] args  = new String[length];
                for (int i = 0; i < valuePanel.getComponents().length; i++){
                    JPanel temp = (JPanel) valuePanel.getComponent(i);
                    Object obj = temp.getComponent(1);
                    if (obj instanceof JTextField){ args[i] = ((JTextField) obj).getText();}
                    else if (obj instanceof JComboBox<?>) {args[i] = ((JComboBox<?>) obj).getSelectedItem().toString();}
                    System.out.println(args[i]);
                }
                int klasaIDX = klasaCB.getSelectedIndex();

                if (klasaIDX == 0) manager.addOsoba(new Student(args));
                else if (klasaIDX == 1) manager.addOsoba(new PracownikAdministracyjny(args));
                else if (klasaIDX == 2) manager.addOsoba(new PracownikBadawczoDydaktyczny(args));
                else if (klasaIDX == 3) manager.addKurs(new Kurs(args));
            }
        });

        panel.add(klasaCB, BorderLayout.PAGE_START);
        panel.add(confirmBT, BorderLayout.PAGE_END);

        currentDisplay.add(panel, event.getActionCommand());
        cardLayout.show(currentDisplay, event.getActionCommand());
    }
    private void wyszukaj(ActionEvent event){
        JPanel panel = new JPanel(new BorderLayout());
        JPanel searchPanel_OUT = new JPanel(new BorderLayout());
        JPanel searchPanel_IN = new JPanel(new GridLayout());

        JComboBox<String> kategoriaCB = new JComboBox<>();
        JComboBox<String> klasaCB = new JComboBox<>(classValues);
        klasaCB.addActionListener(e -> {
            if (!e.getSource().toString().contains("invalid")){
                kategoriaCB.removeAllItems();
                String[] skladowe = manager.otrzymajSkladowe(String.valueOf(klasaCB.getSelectedItem())).toArray(new String[0]);
                for (int i = 0; i < skladowe.length; i++){
                    kategoriaCB.addItem(skladowe[i]);}
                kategoriaCB.setSelectedIndex(-1);
            }
        });
        klasaCB.setSelectedIndex(-1);
        searchPanel_IN.add(klasaCB);
        searchPanel_IN.add(kategoriaCB);

        JTextField searchValueTField = new JTextField();
        searchPanel_IN.add(searchValueTField);


        JScrollPane searchValuePane = new JScrollPane();
        JButton confirmBT = new JButton("WYSZUKAJ");
        confirmBT.addActionListener(e -> {
            if (klasaCB.getSelectedIndex() == -1){
                System.err.println("Nie ma wprowadzonej wartości!");
            }else {
                String klasa = (String) klasaCB.getSelectedItem();
                int kategoriaID = kategoriaCB.getSelectedIndex();
                String searchValue = searchValueTField.getText();

                ArrayList<Object> objectArrayList = manager.wyszukiwanie(klasa, kategoriaID, searchValue);
                JTable table = fillTable(klasa, objectArrayList);
                searchValuePane.getViewport().add(table);

                if (event.getActionCommand().equals("Usuń")){
                    JButton deleteBT = new JButton("USUŃ");
                    deleteBT.addActionListener(e1 -> {
                        manager.delete(klasa, kategoriaID, searchValue);
                    });
                    panel.add(deleteBT, BorderLayout.PAGE_END);
                }

                panel.revalidate();
                panel.repaint();
            }
        });
        searchPanel_IN.add(confirmBT);

        panel.add(searchValuePane, BorderLayout.CENTER);
        searchPanel_OUT.add(searchPanel_IN, BorderLayout.CENTER);
        panel.add(searchPanel_OUT, BorderLayout.PAGE_START);

        currentDisplay.add(panel, event.getActionCommand());
        cardLayout.show(currentDisplay, event.getActionCommand());
    }
    private void wypisz(ActionEvent event){
        String testValue = event.getActionCommand();
        if (testValue.equals("Wyświetl")) testValue = "WSZYSCY";

        JPanel panel = new JPanel(new BorderLayout());
        JPanel functionPanel_OUT = new JPanel();
        JPanel functionPanel_IN = new JPanel(new GridLayout());

        JButton wszyscyBT = new JButton("WSZYSCY");
        wszyscyBT.addActionListener(this::wypisz);
        functionPanel_IN.add(wszyscyBT);

        JButton pracownicyBT = new JButton("PRACOWNICY");
        pracownicyBT.addActionListener(this::wypisz);
        functionPanel_IN.add(pracownicyBT);

        JButton studenciBT = new JButton("STUDENCI");
        studenciBT.addActionListener(this::wypisz);
        functionPanel_IN.add(studenciBT);

        JButton kursyBT = new JButton("KURSY");
        kursyBT.addActionListener(this::wypisz);
        functionPanel_IN.add(kursyBT);

        functionPanel_OUT.add(functionPanel_IN, BorderLayout.CENTER);
        panel.add(functionPanel_OUT, BorderLayout.PAGE_START);

        JPanel container_IN = new JPanel(new GridLayout(3, 1));

        if (testValue.matches("WSZYSCY|PRACOWNICY"))
            container_IN.add(makeTable("Pracownik"));
        if (testValue.matches("WSZYSCY|STUDENCI"))
            container_IN.add(makeTable("Student"));
        if (testValue.matches("WSZYSCY|KURSY"))
            container_IN.add(makeTable("Kurs"));

        panel.add(container_IN, BorderLayout.CENTER);
        currentDisplay.add(panel, "ALL-TABLE");
        cardLayout.show(currentDisplay, "ALL-TABLE");
    }
    private void sort(ActionEvent event){
        JPanel panel = new JPanel(new BorderLayout());
        JPanel functionPanel_OUT = new JPanel();
        JPanel functionPanel_IN = new JPanel(new GridLayout());

        String[] komparatory = {"Sortuj według nazwiska",
                                "Sortuj według nazwiska i imienia",
                                "Sortuj według nazwiska i wieku",
                                "Sortuj według punktów ECTS i nazwiska prowadzącego"};
        JComboBox<String> sortCB = new JComboBox<>(komparatory);
        sortCB.addActionListener(this::comparison);
        sortCB.putClientProperty("panel", panel);
        sortCB.setSelectedIndex(-1);
        functionPanel_IN.add(sortCB);

        functionPanel_OUT.add(functionPanel_IN, BorderLayout.CENTER);
        panel.add(functionPanel_OUT, BorderLayout.PAGE_START);

        currentDisplay.add(panel, "SORT-TABLE");
        cardLayout.show(currentDisplay, "SORT-TABLE");
    }
    private void exitGUI(ActionEvent event){manager.saveData();}
    private void comparison(ActionEvent event){
        if (!event.getSource().toString().contains("invalid")){
            JComboBox<?> source = (JComboBox<?>) event.getSource();
            JPanel panel = (JPanel) source.getClientProperty("panel");
            JPanel container = new JPanel(new BorderLayout());
            if (panel.getComponents().length == 2) panel.remove(1);

            int choiceValue = source.getSelectedIndex();

            if (choiceValue == 3){
                ArrayList<Kurs> sortedKursy = (ArrayList<Kurs>) manager.getKursy().clone();
                ArrayList<Object> sortedKursyObj = new ArrayList<>(sortedKursy);
                sortedKursy.sort(new SortByECTS_Professor());
                container.add(new JScrollPane(fillTable("Kurs", sortedKursyObj)));
            }else {
                ArrayList<Osoba> sortedOsoby = (ArrayList<Osoba>) manager.getOsoby().clone();
                ArrayList<Object> sortedOsobyObj = new ArrayList<>(sortedOsoby);

                if (choiceValue == 0) sortedOsoby.sort(new SortBySurname());
                else if (choiceValue == 1) sortedOsoby.sort(new SortByFullname());
                else if (choiceValue == 2) sortedOsoby.sort(new SortBySurnameAge());

                JTable table = fillTable("Student", sortedOsobyObj);
                for (int i = table.getColumnCount() - 1; i > 1; i--){
                    if (i != 4) table.removeColumn(table.getColumnModel().getColumn(i));
                }
                container.add(new JScrollPane(table));
            }

            panel.add(container, BorderLayout.CENTER);
            panel.repaint();
            panel.revalidate();
        }
    }
}
