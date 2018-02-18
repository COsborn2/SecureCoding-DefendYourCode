import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;

import com.sun.media.sound.InvalidDataException;

/**
 * @author Cameron Osborn
 * @author Jameson Price
 * @author Jordan Caraway
 *
 *         Secure Java Application
 *
 *         1. Prompts for and reads user first name, then last name (max 50
 *         chars)
 *
 *         2. Prompt for and read two int values
 *
 *         3. Prompt for and read name of input file from user
 *
 *         4. Prompt for and read name of output file
 *
 *         5. Prompt user to enter password, store, then ask user to re-enter,
 *         and verify correctness
 *
 *         6. Open output file and write name along with result of adding two
 *         int values and result of multiplying two integer values followed by
 *         input file contents
 *
 *
 *         Program must accept valid input from the user and write to the output
 *         file without error. Any error that does arise should be reported to
 *         either output file, or error log
 */
public class Main {

    public static void main(String[] args) {
        File inputFile;
        File outputFile;
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(System.getProperty("user.dir")+"\\Main.java");
        /**
         String firstName = getFirstName(buf);
         String lastName = getLastName(buf);

         int int1 = getIntValue(buf);
         int int2 = getIntValue(buf);


         System.out.println("First name: " + firstName);
         System.out.println("Last name: " + lastName);
         System.out.println("First Int: " + int1);
         System.out.println("Second Int: " + int2);
         */
        inputFile = new File(getFile(buf));
    }

    private static String getFirstName(BufferedReader buf) {
        String firstName;
        char[] tempFirstName = {};
        while (true) {
            try {
                tempFirstName = new char[51];
                System.out.print("Enter first name (up to 50 chars): ");
                buf.read(tempFirstName, 0, 50);
                firstName = new String(tempFirstName);

                firstName = firstName.replaceAll("\n", ""); //remove new line

                //check if name is valid with regex -> https://regexr.com/3kqol
                if (!(Pattern.matches("^((?!.*[\\/\\^\\$\\#\\@\\!\\*\\;\\<\\>\\&\\\\]).{1,50})$", firstName))) {
                    throw new InvalidDataException("Invalid input");
                }
                break;
            } catch (Exception e) {
                System.out.println("Input not accepted: Try again!");
            }
        }
        return firstName;
    }

    private static String getLastName(BufferedReader buf) {
        String lastName;
        char[] tempLastName = {};
        while (true) {
            try {
                tempLastName = new char[51];
                System.out.print("Enter last name (up to 50 chars): ");
                buf.read(tempLastName, 0, 50);
                lastName = new String(tempLastName);

                lastName = lastName.replaceAll("\n", ""); //remove new line
                //check if name is valid with regex -> https://regexr.com/3kqol
                if (!(Pattern.matches("^((?!.*[\\/\\^\\$\\#\\@\\!\\*\\;\\<\\>\\&\\\\]).{1,50})$", lastName))) {
                    throw new InvalidDataException("Invalid input");
                }
                break;
            } catch (Exception e) {
                System.out.println("Input not accepted: Try again!");
            }
        }
        return lastName;
    }

    private static int getIntValue(BufferedReader buf) {
        String s = "";
        char[] tempInput = {};
        int val;
        while (true) {
            try {
                tempInput = new char[13];
                System.out.print("Enter an int value (Between -2147483647 to 2147483647): ");
                buf.read(tempInput, 0, 13);
                s = new String(tempInput);
                s = s.replaceAll("\\s", ""); //Strips out all whitespace characters like newline and space
                s = s.replaceAll("\u0000", ""); //Strips out null characters produced by the buffered reader

                if (!isInt(s)) {
                    throw new InvalidDataException("Invalid input");
                }
                val = Integer.parseInt(s);
                break;
            } catch (Exception e) {
                System.out.println("Input not accepted: Try again!");
            }
        }
        return val;
    }

    private static boolean isInt(String s) //helper method for getIntValue
    {
        if (s.equals(null) || s.isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private static String getFile(BufferedReader buf) {
        String s = "";
        char[] tempInput;
        while (true) {
            try {
                tempInput = new char[301];
                System.out.println("Enter a valid absolute file path for input(Must exist in the working directory or a subdirectory)");
                System.out.print("-File must be a java, c, or text file: ");
                buf.read(tempInput, 0, 300);
                s = new String(tempInput);
                s = s.replaceAll("\n", ""); //remove new line
                s = s.replaceAll("\u0000", ""); //Strips out null characters produced by the buffered reader

                if(!s.contains(System.getProperty("user.dir"))) //Checks to make sure file is at least in the working directory first
                {
                    throw new Exception("This path does not exist in the working directory");
                }

                if (!new File(s).exists()) //Checks if file actually exists
                {
                    throw new FileNotFoundException("File does not exist");
                }

                if(new File(s).isDirectory()) //checks if file is actually a directory
                {
                    throw new FileNotFoundException("Directories are not valid files");
                }

                if(!(s.contains(".txt") | s.contains(".java") | s.contains(".c"))) //checks to make sure file is of valid type
                {
                    throw new Exception("File must be of type .txt, .java. or .c");
                }
                /*
                if(!s.contains(System.getProperty("user.dir")+"\\Main.java"))
                {
                    throw new Exception("File can not be this application source");
                }
                */
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return s;
    }
}