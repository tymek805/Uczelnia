package Management;

import javax.swing.*;
import java.math.BigInteger;

public abstract class InputValidatorClass {
    protected String inputValidator(JTextField source, String nazwaTypu) throws NullPointerException, NumberFormatException{
        String text_to_validate = source.getText();
        if (nazwaTypu.matches("Wiek|Staż pracy|Pensja|Liczba publikacji|Liczba nadgodzin|Numer indeksu|Rok studiów|Punkty ECTS"))
            intValidator(text_to_validate);
        else if (nazwaTypu.equals("Pesel"))
            peselValidator(text_to_validate);
        return text_to_validate;
    }

    private void intValidator(String text_to_validate){
        Integer.parseInt(text_to_validate);
    }
    private void peselValidator(String text_to_validate) throws NumberFormatException{
        BigInteger value = new BigInteger(text_to_validate);
        if (String.valueOf(value).length() != 11)
            throw new NumberFormatException();
    }
}
