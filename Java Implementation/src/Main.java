import java.io.*;
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
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        Scanner kin = new Scanner(System.in);
        System.out.println(System.getProperty("user.dir")+"\\Main.java");
        /*

         //----------------------------------------------------------------Actual method calls------------------------------

         String firstName = getFirstName(buf);
         String lastName = getLastName(buf);

         int int1 = getIntValue(buf);
         int int2 = getIntValue(buf);

         inputFile = new File(getInFile(kin));
         outputFile = new File(getOutFile(kin, inputFile));

         //----------------------------------------------------------------System prints for tests--------------------------

         System.out.println("First name: " + firstName);
         System.out.println("Last name: " + lastName);

         System.out.println("First Int: " + int1);
         System.out.println("Second Int: " + int2);

         System.out.println(inputFile.getAbsolutePath());
         System.out.println(outputFile.getAbsolutePath());
         */

        //------------------------write to file---------------------
        inputFile = new File("C:\\Users\\Doomcrown\\Desktop\\Secure project\\src\\tester.txt");
        outputFile = new File("C:\\Users\\Doomcrown\\Desktop\\Secure project\\src\\tester2.txt");
        writeToFile("Jordan","Caraway", 10,20, inputFile, outputFile);



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
                    throw new Exception("Invalid input");
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
                    throw new Exception("Invalid input");
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

    private static String getInFile(Scanner kin)
    {
        String s = "";
        while (true) {
            try {

                System.out.println("Enter a valid absolute file path for INPUT(Must exist in the working directory or a subdirectory)");
                System.out.print("-File must be a java, c, or text file: ");

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
        while (true) {
            try {

                System.out.println("Enter a valid absolute file path for OUTPUT(Must exist in the working directory or a subdirectory)");
                System.out.print("-File must be a java, c, or text file: ");

                s = kin.nextLine();
                System.out.println(s);

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