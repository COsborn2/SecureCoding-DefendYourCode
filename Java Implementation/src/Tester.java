import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Tester {

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    void test() throws IOException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        
        String test = getInFile(buf);
        
        System.out.println(test);
    }
    
    private static String getInFile(BufferedReader kin) {
        String s = "";
        System.out.println("\nFile rules: Requires absolute file path\n"
                + "File must exist in the working directory or a subdirectory\n"
                + "File must be of type .java, .c, or .txt\n" + "This programs source file can not be the target\n"
                + "File can not be the passwordHash.txt file\n");
        while (true) {
            try {

                System.out.print("Enter a valid absolute file path for INPUT: ");

                s = kin.readLine();

                if (!s.contains(System.getProperty("user.dir"))) // Checks to make sure file is at least in the working
                                                                 // directory first
                {
                    throw new Exception("This path does not exist in the working directory.");
                }

                if (!new File(s).getAbsoluteFile().exists()) // Checks if file actually exists
                {
                    throw new FileNotFoundException("File does not exist.");
                }

                if (new File(s).isDirectory()) // checks if file is actually a directory
                {
                    throw new FileNotFoundException("Directories are not valid files.");
                }

                if (!(s.endsWith(".txt") | s.endsWith(".java") | s.endsWith(".c"))) // checks to make sure file is of
                                                                                    // valid type
                {
                    throw new Exception("File must be of type .txt, .java. or .c");
                }

                if (s.contains(System.getProperty("user.dir") + File.separator + "Main.java")) // checks to make sure
                                                                                               // the file is not
                // the java source
                {
                    throw new Exception("File can not be this application source.");
                }

                if (s.contains(System.getProperty("user.dir") + File.separator + "passwordHash.txt")) // checks to make
                                                                                                      // sure the file
                // is not the passwordHash file
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
}
