import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner fileparser = new Scanner(new File("./test/test.txt"));
            
            String[] tokens = fileparser.nextLine().split(" ");
            System.out.println("N :" + tokens[0] + " M : " + tokens[1] + " P : " + tokens[2]);
            int N = Integer.parseInt(tokens[0]);
            int M = Integer.parseInt(tokens[1]);
            int P = Integer.parseInt(tokens[2]);
            
            tokens = fileparser.nextLine().split(" ");
            String mode = tokens[0];
            System.out.println(mode);

            for(int i = 0; i < P; i++) {
                tokens = fileparser.nextLine().split(" ");
                String last = tokens[0];
                System.out.println(last);
            }

            fileparser.close(); // Close the scanner to prevent resource leak
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}

