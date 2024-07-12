package manager.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputControl {
    private final Scanner entry = new Scanner(System.in);

    // universal control if user input is integer number
    public int intEntry(String prompt){
        System.out.print(prompt);
        int number;
        while (true){
            try {
                number = entry.nextInt();
                entry.nextLine();
                break;
            }catch (InputMismatchException e){
                System.out.print("--- Enter only integer number: ");
                entry.nextLine();
            }
        }
        return number;
    }

    public double doubleEntry(String prompt) {
        System.out.print(prompt);
        double number;
        while (true) {
            try {
                number = entry.nextDouble();
                entry.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.print("--- Enter only double number: ");
                entry.nextLine();
            }
        }
        return number;
    }

    // Validate phone number using regex
    public String phoneNumberEntry(String prompt) {
        System.out.print(prompt);
        boolean exit = false;
        String phoneNumber = "";
        while (!exit) {
            phoneNumber = entry.nextLine();
            if (phoneNumber.isEmpty()) {
                System.out.print("--- Input cannot be empty. Please try again --- : ");
            } else if (!isValidPhoneNumber(phoneNumber)) {
                System.out.print("--- Invalid phone number format. Please try again --- : ");
            } else {
                exit = true;
            }
        }
        return phoneNumber;
    }

    public String textEntry(String prompt){
        System.out.print(prompt);
        boolean exit= false;
        String line="";
        while (!exit){
            line = entry.nextLine();
            if (line.isEmpty()){
                System.out.print("--- Input cannot be empty. Please try again --- :");
            } else {
                exit=true;
            }
        }
        return line;
    }

    public String stringEntry(String prompt) {
        System.out.print(prompt);
        boolean exit= false;
        String line="";
        while (!exit){
            line = entry.nextLine();
            if (line.isEmpty()){
                System.out.print("--- Input cannot be empty. Please try again --- :");
            } else if (!line.matches("[a-zA-Z ]+")) {
                System.out.print("--- Invalid input. Write only alphabets --- :");
            } else {
                exit=true;
            }
        }
        return line;
    }

    // Check if the phone number matches the European format
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Define the regex pattern for a valid European phone number
        String regex = "^(\\+\\d{1,3}\\s?)?\\d{3}\\s?\\d{3}\\s?\\d{3,4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public Date dateEntry(String prompt) {
        System.out.print(prompt);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        Date date = null;
        while (date == null) {
            String input = entry.nextLine();
            try {
                date = dateFormat.parse(input);
            } catch (ParseException e) {
                System.out.print("--- Invalid date format. Please enter the date in yyyy-MM-dd format: ");
            }
        }
        return date;
    }
}
