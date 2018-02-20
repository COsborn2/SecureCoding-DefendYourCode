import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.regex.Pattern;
import java.util.Scanner;


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
        File inputFile = null;
        File outputFile = null;
        Scanner kin = new Scanner(System.in);


         //----------------------------------------------------------------Actual method calls------------------------------
         String firstName = getFirstName(kin);
         String lastName = getLastName(kin);

         int int1 = getIntValue(kin);
         int int2 = getIntValue(kin);

         inputFile = new File(getInFile(kin));
         outputFile = new File(getOutFile(kin, inputFile));

         doPassword(kin);

         //----------------------------------------------------------------System prints for tests--------------------------
        /*
         System.out.println("First name: " + firstName);
         System.out.println("Last name: " + lastName);

         System.out.println("First Int: " + int1);
         System.out.println("Second Int: " + int2);

         System.out.println(inputFile.getAbsolutePath());
         System.out.println(outputFile.getAbsolutePath());
        */

        //------------------------write to file---------------------
        writeToFile(firstName, lastName, int1, int2, inputFile, outputFile);

    }

    private static String getFirstName(Scanner kin) {
        String firstName;
        System.out.println("\nName Rules: Must start with a letter, followed by only letters and the symbols ' and -\ncan not have -- '' '- or -' anywhere in your name.\n");
        while (true) {
            try {
                System.out.print("Enter first name (up to 50 chars): ");
                firstName = kin.nextLine();
                if(Pattern.matches("^[a-zA-Z]{1}[a-zA-Z\\-\\']{0,49}$",firstName))
                {
                    if (Pattern.matches("^.*([\\-\\']{2}).*$", firstName)) {
                        throw new Exception("Name is not valid");
                    }
                }
                else{throw new Exception("Name is not valid");}
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return firstName;
    }

    private static String getLastName(Scanner kin) {
        String lastName;
        System.out.println("\nName Rules: Must start with a letter, followed by only letters and the symbols ' and -\ncan not have -- '' '- or -' anywhere in your name.\n");
        while (true) {
            try {
                System.out.print("Enter last name (up to 50 chars): ");
                lastName = kin.nextLine();
                if(Pattern.matches("^[a-zA-Z]{1}[a-zA-Z\\-\\']{0,49}$",lastName))
                {
                    if (Pattern.matches("^.*([\\-\\']{2}).*$", lastName))
                    {
                        throw new Exception("Name is not valid");
                    }
                }
                else{throw new Exception("Name is not valid");}
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return lastName;
    }

    private static int getIntValue(Scanner kin) {
        String s = "";
        int val;
        System.out.println("\nInt Rules: must contain only numbers with an optional prefix -\nnumber must be between -2147483648 to 2147483647 to be classified as an int.\n");
        while (true) {
            try {
                System.out.print("Enter an int value: ");
                s = kin.nextLine();

                if (!isInt(s)) {
                    throw new Exception("Invalid input");
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

    private static void doPassword(Scanner kin)
    {
        String pass = "";
        String hash = "";
        String hash2 = "";
        byte[] bytes;
        BigInteger bInt;
        System.out.println("\nPassword must be between 8 and 32 characters\nPassword can only contain numbers, letters, and ~!@#$%&*<>?: ");
        while (true)
        {
            try{
                System.out.print("Enter a password: ");
                pass = kin.nextLine();
                if (!Pattern.matches("^[a-zA-Z0-9\\~\\!\\@\\#\\$\\%\\?\\<\\>\\&\\*]{8,32}$", pass))
                {
                    throw new Exception("Password is not valid.");
                }

                MessageDigest m = MessageDigest.getInstance("MD5");
                m.reset();
                m.update(pass.getBytes());
                bytes = m.digest();
                bInt = new BigInteger(1,bytes);

                hash = bInt.toString(16); //password stored as hash

                System.out.print("Re-enter password: ");
                pass = kin.nextLine();

                m.reset();
                m.update(pass.getBytes());
                bytes = m.digest();
                bInt = new BigInteger(1,bytes);

                hash2 = bInt.toString(16);

                if(!hash.equals(hash2))
                {
                    throw new Exception("Passwords do not match. Try again.");
                }

                System.out.println("Passwords matched!");
                writeHash(hash);
                break;
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void writeHash(String hash)
    {
        File file = new File(System.getProperty("user.dir")+"\\passwordHash.txt");
        PrintWriter pwOut = null;
        try {
            if(!file.exists())
            {
                file.createNewFile();
            }
            FileWriter fwOut = new FileWriter(file, true);
            pwOut = new PrintWriter(fwOut);

            pwOut.println(hash+ "\n");
            pwOut.close();
            fwOut.close();
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static String getInFile(Scanner kin)
    {
        String s = "";
        System.out.println("\nFile rules: Requires absolute file path\n" +
                "File must exist in the working directory or a subdirectory\n" +
                "File must be of type .java, .c, or .txt\n" +
                "This programs source file can not be the target\n" +
                "File can not be the passwordHash.txt file\n");
        while (true) {
            try {

                System.out.print("Enter a valid absolute file path for INPUT: ");

                s = kin.nextLine();

                if(!s.contains(System.getProperty("user.dir"))) //Checks to make sure file is at least in the working directory first
                {
                    throw new Exception("This path does not exist in the working directory.");
                }

                if (!new File(s).getAbsoluteFile().exists()) //Checks if file actually exists
                {
                    throw new FileNotFoundException("File does not exist.");
                }

                if(new File(s).isDirectory()) //checks if file is actually a directory
                {
                    throw new FileNotFoundException("Directories are not valid files.");
                }

                if(!(s.endsWith(".txt") | s.endsWith(".java") | s.endsWith(".c"))) //checks to make sure file is of valid type
                {
                    throw new Exception("File must be of type .txt, .java. or .c");
                }

                if(s.contains(System.getProperty("user.dir")+"\\Main.java")) //checks to make sure the file is not the java source
                {
                    throw new Exception("File can not be this application source.");
                }

                if(s.contains(System.getProperty("user.dir")+"\\passwordHash.txt")) //checks to make sure the file is not the passwordHash file
                {
                    throw new Exception("File can not be the passwordHash file.");
                }

                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return s;
    }

    private static String getOutFile(Scanner kin, File inFile)
    {
        String s = "";
        System.out.println("\nFile rules: Requires absolute file path\n" +
                "File must exist in the working directory or a subdirectory\n" +
                "File must be of type .java, .c, or .txt\n" +
                "File can not be the passwordHash.txt file\n" +
                "This programs source file can not be the target\n" +
                "Output file can not be the same as input file\n" +
                "File must already exist\n");
        while (true) {
            try {

                System.out.print("Enter a valid absolute file path for OUTPUT");

                s = kin.nextLine();


                if(!s.contains(System.getProperty("user.dir"))) //Checks to make sure file is at least in the working directory first
                {
                    throw new Exception("This path does not exist in the working directory.");
                }

                if (!new File(s).getAbsoluteFile().exists()) //Checks if file actually exists
                {
                    throw new FileNotFoundException("File does not exist.");
                }

                if(new File(s).isDirectory()) //checks if file is actually a directory
                {
                    throw new FileNotFoundException("Directories are not valid files.");
                }

                if(!(s.endsWith(".txt") | s.endsWith(".java") | s.endsWith(".c"))) //checks to make sure file is of valid type
                {
                    throw new Exception("File must be of type .txt, .java. or .c");
                }

                if(s.contains(System.getProperty("user.dir")+"\\Main.java")) //checks to make sure the file is not the java source
                {
                    throw new Exception("File can not be this application source.");
                }

                if(s.contains(System.getProperty("user.dir")+"\\passwordHash.txt")) //checks to make sure the file is not the passwordHash file
                {
                    throw new Exception("File can not be the passwordHash file.");
                }

                if(s.contains(inFile.getAbsolutePath())) //checks to make sure outfile is not the same as input file
                {
                    throw new Exception("Output file can not be the same as input file.");
                }

                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return s;
    }

    private static Boolean writeToFile(String fName, String lName, int int1, int int2, File inFile, File outFile)
    {
        String inLine;
        PrintWriter pwOut = null;
        BufferedReader brIn = null;
        try {
            FileWriter fwOut = new FileWriter(outFile, true);
            pwOut = new PrintWriter(fwOut);
            //Prints data for name and ints
            pwOut.println("\n<<<<<<<<<<<<Start of File Writing>>>>>>>>>>>>");
            pwOut.println(fName);
            pwOut.println(lName);
            pwOut.println((int1+int2));
            pwOut.println((int1*int2));
            pwOut.println();
            FileReader fwIn = new FileReader(inFile);
            brIn = new BufferedReader(fwIn);

            while((inLine = brIn.readLine()) != null) //grabs line by line of input file and prints to output
            {
                pwOut.println(inLine);
            }

            pwOut.println("<<<<<<<<<<<<<End of File Writing>>>>>>>>>>>>>");
            pwOut.println();

        }catch (IOException e) {
          e.printStackTrace();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } finally { //for closing readers, BR needs to be ina  try block but SOP is to close streams in finally block even though try/catch auto close streams
            try {
                brIn.close();
                pwOut.close();
            }catch(IOException e){
                e.printStackTrace();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return false;
    }
}