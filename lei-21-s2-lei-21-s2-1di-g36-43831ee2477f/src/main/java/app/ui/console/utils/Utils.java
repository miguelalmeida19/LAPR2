package app.ui.console.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class Utils {

    static public String generatePassword() {
        String password = new Random().ints(10, 33, 122).mapToObj(i -> String.valueOf((char) i)).collect(Collectors.joining());
        return password;
    }

    static public String readLineFromConsole(String prompt)
    {
        try
        {
            Utils.print("\n" + prompt);

            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    static public int readIntegerFromConsole(String prompt)
    {
        do
        {
            try
            {
                String input = readLineFromConsole(prompt);

                int value = Integer.parseInt(input);

                return value;
            } catch (NumberFormatException ex)
            {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public double readDoubleFromConsole(String prompt)
    {
        do
        {
            try
            {
                String input = readLineFromConsole(prompt);

                double value = Double.parseDouble(input);

                return value;
            } catch (NumberFormatException ex)
            {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public Date readDateFromConsole(String prompt)
    {
        do
        {
            try
            {
                String strDate = readLineFromConsole(prompt);

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

                Date date = df.parse(strDate);

                return date;
            } catch (ParseException ex)
            {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, "Birth Date cannot be empty", ex);
            }
        } while (true);
    }

    static public boolean confirm(String message) {
        String input;
        do {
            input = Utils.readLineFromConsole("\n" + message + "\n");
        } while (!Objects.requireNonNull(input).equalsIgnoreCase("s") && !input.equalsIgnoreCase("n"));

        return input.equalsIgnoreCase("s");
    }

    static public Object showAndSelectOne(List list, String header)
    {
        showList(list,header);
        return selectsObject(list);
    }
    static public int showAndSelectIndex(List list, String header)
    {
        showList(list,header);
        return selectsIndex(list);
    }
    static public void showList(List list, String header)
    {
        Utils.print(header);

        int index = 0;
        for (Object o : list)
        {
            index++;

            Utils.print(index + ". " + o.toString());
        }
        Utils.print("");
        Utils.print("0 - Cancel");
    }

    static public Object selectsObject(List list)
    {
        String input;
        Integer value;
        do
        {
            input = Utils.readLineFromConsole("Type your option: ");
            value =  Integer.valueOf(input);
        } while (value < 0 || value > list.size());

        if (value == 0)
        {
            return null;
        } else
        {
            return list.get(value - 1);
        }
    }

    static public int selectsIndex(List list)
    {
        String input;
        Integer value;
        do
        {
            input = Utils.readLineFromConsole("Type your option: ");
            value =  Integer.valueOf(input);
        } while (value < 0 || value > list.size());

        return value - 1;
    }
    static public void print(String text){
        System.out.println(text);

    }
    static public void print(int text){
        System.out.println(text);

    }
    static public void print(double text){
        System.out.println(text);

    }

    static public void print(){
        System.out.println();

    }
}
