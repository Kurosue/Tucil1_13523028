import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    private static int N, M, P;
    private static String mode;

    public static void main(String[] args) {
          parser();
          System.out.println("N: " + N + " M: " + M + " P: " + P + " Mode: " + mode);
    }

    private static void parser()
    {
        try {
            Scanner fileparser = new Scanner(new File("./test/test.txt"));
            
            String[] tokens = fileparser.nextLine().split(" ");
            N = Integer.parseInt(tokens[0]);
            M = Integer.parseInt(tokens[1]);
            P = Integer.parseInt(tokens[2]);
            
            tokens = fileparser.nextLine().split(" ");
            mode = tokens[0];

            for (int i = 0; i < P; i++){
                // Inisialisasi blok
                Block block = new Block(fileparser);

                // Print Block
                block.rotateBlock();
                block.printBlock();
            }

            fileparser.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

    }
}

