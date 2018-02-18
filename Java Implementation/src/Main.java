import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

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

    public static void main(String[] args)
    {
        String inputFile;
        String outputFile;

        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));

        String firstName = getFirstName(buf);
        String lastName = getLastName(buf);

        int int1 = getIntValue(buf);
        int int2 = getIntValue(buf);


        System.out.print("First name: " + firstName);
        System.out.print("Last name: " + lastName);
        System.out.print("First Int: " + int1);
        System.out.print("Second Int: " + int2);
    }

    private static String getFirstName(BufferedReader buf)
    {
        String firstName;
        char[] tempFirstName = {};
        while (true)
        {
            try
            {
                tempFirstName = new char[51];
                System.out.print("Enter first name (up to 50 chars): ");
                buf.read(tempFirstName, 0, 50);
                firstName = new String(tempFirstName);

                firstName = firstName.replaceAll("\n", ""); //remove new line

                //check if name is valid with regex -> https://regexr.com/3kqol
                if(!(Pattern.matches("^((?!.*[\\/\\^\\$\\#\\@\\!\\*\\;\\<\\>\\&\\\\]).{1,50})$", firstName)))
                {
                    throw new InvalidDataException("Invalid input");
                }
                break;
            } catch (Exception e)
            {
                System.out.println("Input not accepted: Try again!");
            }
        }
        return firstName;
    }

    private static String getLastName(BufferedReader buf)
    {
        String lastName;
        char[] tempLastName = {};
        while (true)
        {
            try
            {
                tempLastName = new char[51];
                System.out.print("Enter last name (up to 50 chars): ");
                buf.read(tempLastName, 0, 50);
                lastName = new String(tempLastName);

                lastName = lastName.replaceAll("\n", ""); //remove new line
                //check if name is valid with regex -> https://regexr.com/3kqol
                if(!(Pattern.matches("^((?!.*[\\/\\^\\$\\#\\@\\!\\*\\;\\<\\>\\&\\\\]).{1,50})$", lastName)))
                {
                    throw new InvalidDataException("Invalid input");
                }
                break;
            } catch (Exception e)
            {
                System.out.println("Input not accepted: Try again!");
            }
        }
        return lastName;
    }

    private static int getIntValue(BufferedReader buf)
    {
        String s = "";
        char[] tempInput = {};
        int val;
        while (true)
        {
            try
            {
                tempInput = new char[13];
                System.out.print("Enter an int value (Between -2147483647 to 2147483647): ");
                buf.read(tempInput, 0, 13);
                s = new String(tempInput);
                s = s.replaceAll("\\s", ""); //Strips out all whitespace characters like newline and space
                s = s.replaceAll("\u0000", ""); //Strips out null characters produced by the buffered reader

                if(!isInt(s))
                {
                    throw new InvalidDataException("Invalid input");
                }
                val = Integer.parseInt(s);
                break;
            } catch (Exception e)
            {
                System.out.println("Input not accepted: Try again!");
            }
        }
        return val;
    }

    private static boolean isInt(String s) //helper method for getIntValue
    {
        if (s.equals(null) || s.isEmpty())
        {
            return false;
        }

        try
        {
            Integer.parseInt(s);
        } catch (Exception e)
        {
            return false;
        }

        return true;
    }
}