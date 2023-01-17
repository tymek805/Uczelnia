package GUI;

import Komparatory.SortByECTS_Professor;
import Komparatory.SortByFullname;
import Komparatory.SortBySurname;
import Komparatory.SortBySurnameAge;
import Management.InputValidatorClass;
import Management.ManagementUczelni;
import Objects.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GUI extends InputValidatorClass {
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
        functionPanel.setLayout(new GridLayout(8, 1));

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

        // Duplicate
        JButton duplicateBT = new JButton("Usuń duplikaty");
        duplicateBT.addActionListener(this::duplicate);
        functionPanel.add(duplicateBT);

        // Blank
        JPanel doesSatisfyContainer = new JPanel(new GridLayout(2, 1));

        JButton addValueBT = new JButton("Dodaj Ocenę / Kurs");
        addValueBT.addActionListener(this::dodajKursOcene);
        doesSatisfyContainer.add(addValueBT);

        JButton blankBT = new JButton("");
        blankBT.setEnabled(false);
        doesSatisfyContainer.add(blankBT);

        functionPanel.add(doesSatisfyContainer);

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

    private void addNewObjectComponents (ActionEvent event){
        if (!event.getSource().toString().contains("invalid")) {
            JComboBox<?> source = (JComboBox<?>) event.getSource();
            String klasa = (String) source.getSelectedItem();
            ArrayList<String> components = manager.otrzymajSkladowe(klasa);
            JPanel panel = (JPanel) source.getClientProperty("panel");

            if (panel.getComponents().length == 3) panel.remove(2);
            JPanel container = new JPanel(new BorderLayout());
            JPanel valuesContainer = new JPanel(new GridLayout(components.size(), 1));

            for (int i = 0; i < components.size(); i++) {
                String currentValue = components.get(i);
                JPanel componentPanel = new JPanel();

                if (!currentValue.equals("Stan Studenta")) componentPanel.add(new JLabel(currentValue));
                if (currentValue.equals("Płeć")) {
                    JComboBox<String> genderCB = new JComboBox<>(new String[]{"Mężczyzna", "Kobieta"});
                    genderCB.setSelectedIndex(-1);
                    componentPanel.add(genderCB);
                }
                else if (currentValue.equals("Stanowisko")) {
                    String[] stanowiska = new String[0];
                    if (klasa.equals(classValues[2]))
                        stanowiska = new String[]{"Asystent", "Adiunkt", "Profesor Nadzwyczajny", "Profesor Zwyczajny", "Wykładowca"};
                    else if (klasa.equals(classValues[1]))
                        stanowiska = new String[]{"Referent", "Specjalista", "Starszy Specjalista", "Kierownik"};

                    JComboBox<String> positionCB = new JComboBox<>(stanowiska);
                    positionCB.setSelectedIndex(-1);
                    componentPanel.add(positionCB);
                }
                else if(currentValue.equals("Stan Studenta")){
                    JPanel checkboxAllPanel = new JPanel(new GridLayout(2, 3));
                    String[] stanStudentaLista = {"student I-stopnia studiów", "student studiów stacjonarnych", "uczestnik programu ERASMUS", "student II-stopnia studiów", "student studiów niestacjonarnych"};
                    for (String s : stanStudentaLista) {
                        JCheckBox checkBox = new JCheckBox(s);
                        checkboxAllPanel.add(checkBox);
                    }
                    componentPanel.add(checkboxAllPanel);
                }
                else {
                    JTextField componentTextField = new JTextField(JTextField.TRAILING);
                    componentPanel.add(componentTextField);
                }
                valuesContainer.add(componentPanel);
            }
            container.add(valuesContainer);

            panel.add(container, BorderLayout.CENTER, 2);
            panel.revalidate();
            panel.revalidate();
        }
    }
    private void dodaj(ActionEvent event){
        JPanel panel = new JPanel(new BorderLayout());

        JComboBox<String> klasaCB = new JComboBox<>(classValues);
        klasaCB.putClientProperty("panel", panel);
        klasaCB.addActionListener(this::addNewObjectComponents);
        klasaCB.setSelectedIndex(-1);

        JButton confirmBT = new JButton("DODAJ");
        confirmBT.addActionListener(e -> {
            if (klasaCB.getSelectedIndex() != -1){
                JPanel valuePanel = (JPanel) ((JPanel) panel.getComponent(2)).getComponent(0);
                int length = valuePanel.getComponents().length;
                int klasaIDX = klasaCB.getSelectedIndex();

                String[] args  = new String[length];
                for (int i = 0; i < length; i++){
                    JPanel temp = (JPanel) valuePanel.getComponent(i);

                    String nazwaTypu = "";
                    Object obj;
                    if (temp.getComponent(0) instanceof JLabel){
                        nazwaTypu = ((JLabel) temp.getComponent(0)).getText();
                        obj = temp.getComponent(1);
                    }else {
                        obj = temp.getComponent(0);
                    }

                    try{
                        if (obj instanceof JTextField){ args[i] = inputValidator((JTextField) obj, nazwaTypu);}
                        else if (obj instanceof JComboBox<?>) {args[i] = ((JComboBox<?>) obj).getSelectedItem().toString();}
                        else if (obj instanceof JPanel){
                            String[] stanStudentaArgs = new String[5];
                            for (int j = 0; j < 5; j++){
                                boolean value = ((JCheckBox) ((JPanel) obj).getComponent(j)).isSelected();
                                if (value) stanStudentaArgs[j] = "TAK";
                                else stanStudentaArgs[j] = "NIE";
                            }
                            args[i] = String.join(",", stanStudentaArgs);
                        }
                    } catch (NullPointerException exception){
                        JOptionPane.showMessageDialog(panel, "Wypełnij wszystkie pola!", "",JOptionPane.WARNING_MESSAGE);
                        klasaIDX = -1;
                        break;
                    } catch (NumberFormatException exception){
                        JOptionPane.showMessageDialog(panel, "Niepoprawny format w polu:\n" + nazwaTypu, "", JOptionPane.WARNING_MESSAGE);
                        klasaIDX = -1;
                        break;
                    }
                }
                if (klasaIDX == 0) manager.addOsoba(new Student(args));
                else if (klasaIDX == 1) manager.addOsoba(new PracownikAdministracyjny(args));
                else if (klasaIDX == 2) manager.addOsoba(new PracownikBadawczoDydaktyczny(args));
                else if (klasaIDX == 3) manager.addKurs(new Kurs(args));

                if (klasaIDX != -1){
                    dodaj(event);
                    JOptionPane.showMessageDialog(panel, "Dodanie obiektu zakończone pomyślnie!", "Sukces",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else JOptionPane.showMessageDialog(panel, "Wypełnij wszystkie pola!", "",JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(panel, "Nie ma wprowadzonej wartości!", "",JOptionPane.WARNING_MESSAGE);
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
    private void duplicate(ActionEvent event){
        JPanel panel = new JPanel(new BorderLayout());

        ArrayList<Osoba> osobaArrayList = manager.getOsoby();
        ArrayList<Object> objectArrayList = new ArrayList<>();
        HashSet<String> peselHashSet = new HashSet<>();
        HashSet<Integer> indexHashSet = new HashSet<>();
        for (int i = 0; i < osobaArrayList.size(); i++){
            Osoba osoba = osobaArrayList.get(i);
            if (osoba instanceof PracownikUczelni){
                if (!peselHashSet.add(osoba.getPesel())){
                    JOptionPane.showMessageDialog(panel, "Duplikat został znaleziony dla peselu: " + osoba.getPesel(), "",JOptionPane.INFORMATION_MESSAGE);
                    osobaArrayList.remove(osoba);
                }else {
                    peselHashSet.add(osoba.getPesel());
                    objectArrayList.add(osoba);
                }
            }
            else if (osoba instanceof Student){
                int tempIndeks = ((Student) osoba).getNumerIndeksu();
                if (!indexHashSet.add(tempIndeks)){
                    JOptionPane.showMessageDialog(panel, "Duplikat został znaleziony dla indeksu: " + tempIndeks, "",JOptionPane.INFORMATION_MESSAGE);
                    osobaArrayList.remove(osoba);
                }else{
                    indexHashSet.add(tempIndeks);
                    objectArrayList.add(osoba);
                }
            }
        }
        panel.add(new JScrollPane(fillTable("Osoba", objectArrayList)), BorderLayout.CENTER);
        currentDisplay.add(panel, "DUPLICATE-TABLE");
        cardLayout.show(currentDisplay, "DUPLICATE-TABLE");
    }
    private void dodajKursOcene(ActionEvent event){
        JPanel panel = new JPanel(new BorderLayout());
        JPanel container = new JPanel(new FlowLayout());

        String[] values = new String[]{"Ocena", "Kurs"};
        JComboBox<String> kursOcenaCB = new JComboBox<>(values);
        kursOcenaCB.setSelectedIndex(-1);
        container.add(kursOcenaCB);

        JComboBox<String> studentCB = new JComboBox<>();
        studentCB.putClientProperty("panel", container);
        ArrayList<Osoba> osoby = manager.getOsoby();
        ArrayList<Student> students = new ArrayList<>();
        for (Osoba osoba : osoby) {
            if (osoba instanceof Student) {
                studentCB.addItem(osoba.getImie() + " " + osoba.getNazwisko());
                students.add((Student) osoba);
            }
        }
        studentCB.addActionListener(e -> {
            JComboBox<String> source = (JComboBox<String>) e.getSource();
            if (source.getSelectedIndex() != -1){
                String value = values[kursOcenaCB.getSelectedIndex()];
                JPanel tempPanel = (JPanel) source.getClientProperty("panel");
                if (tempPanel.getComponents().length == 4){ tempPanel.remove(3); tempPanel.remove(2);}
                if (value.equals("Ocena")){
                    JComboBox<Double> ocenaCB = new JComboBox<>();
                    for (double i = 2.0; i < 6; i += 0.5) ocenaCB.addItem(i);
                    ocenaCB.setSelectedIndex(-1);
                    tempPanel.add(ocenaCB);
                }else if (value.equals("Kurs")) {
                    JComboBox<String> kursCB = new JComboBox<>();
                    ArrayList<Kurs> kursy = manager.getKursy();
                    for (Kurs kurs: kursy) kursCB.addItem(kurs.getNazwa());
                    kursCB.setSelectedIndex(-1);
                    tempPanel.add(kursCB);
                }
                JButton confirmBT = new JButton("Dodaj");
                confirmBT.addActionListener(e1 -> {
                    int idx = ((JComboBox<?>)tempPanel.getComponent(1)).getSelectedIndex();
                    Student student = students.get(idx);
                    JComboBox<?> compontent = (JComboBox<?>) tempPanel.getComponent(2);
                    if (compontent.getSelectedItem() instanceof Double){
                        student.dodajOcene((Double) compontent.getSelectedItem());
                        observerNotified("Średnia", student.getCzyZdaje());
                    }else if (compontent.getSelectedItem() instanceof String){
                        int componentIDX = compontent.getSelectedIndex();
                        student.startKursu(manager.getKursy().get(componentIDX));
                        observerNotified("ECTS", student.getCzyZdaje());
                    }
                });

                tempPanel.add(confirmBT);
                tempPanel.revalidate();
                tempPanel.repaint();
            }
        });
        studentCB.setSelectedIndex(-1);
        container.add(studentCB);
        panel.add(container, BorderLayout.PAGE_START);
        currentDisplay.add(panel, "SATISFY");
        cardLayout.show(currentDisplay, "SATISFY");
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
                if (choiceValue == 0) sortedOsoby.sort(new SortBySurname());
                else if (choiceValue == 1) sortedOsoby.sort(new SortByFullname());
                else if (choiceValue == 2) sortedOsoby.sort(new SortBySurnameAge());

                ArrayList<Object> sortedOsobyObj = new ArrayList<>(sortedOsoby);
                JTable table = fillTable("Osoba", sortedOsobyObj);

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

    public void observerNotified(String value, boolean trueFalse){
        String str = " niewystarczająca";
        if (trueFalse){
            str = " wystarczająca";
        }
        JOptionPane.showMessageDialog(currentDisplay, value + str + " aby uzyskać zaliczenie", "",JOptionPane.WARNING_MESSAGE);};
}
    