import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    private static int N, M, P;
    private static String mode;
    private static Block[] blocks;

    public static void main(String[] args) {
          readInput();



          }
    }

    // BruteForce untuk setiap kemungkinan block dan rotasinya 
    private static void solveBruteForce()
    {
        Board board = new Board(N, M);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < P; k++) {
                    for (int l = 0; l < 8; l++) {
                        if (board.canPlaceBlock(i, j, blocks[k], l)) {
                            board.placeBlocks(i, j, blocks[k], l);
                            if (board.isFull()) {
                                board.printBoard();
                                return;
                            }
                            board.removeBlocks(i, j, blocks[k], l);
                        }
                    }
                }
            }
        }
    }

    private static void readInput() {
        try {
            Scanner fileparser = new Scanner(new File("./test/test2.txt"));

            String[] tokens = fileparser.nextLine().split(" ");
            N = Integer.parseInt(tokens[0]);
            M = Integer.parseInt(tokens[1]);
            P = Integer.parseInt(tokens[2]);

            mode = fileparser.nextLine().trim();

            blocks = new Block[P];

            int blockIndex = 0;
            
            String firstLine = fileparser.nextLine().trim();

            while (blockIndex < P) {
                ArrayList<String> blockLines = new ArrayList<>();

                blockLines.add(firstLine);
                char charBlok = firstLine.charAt(0);

                while (fileparser.hasNextLine()) {
                    String nextLine = fileparser.nextLine().trim();
                    if (nextLine.isEmpty() || nextLine.charAt(0) != charBlok) {
                        if(nextLine.charAt(0) != charBlok) {
                            firstLine = nextLine;
                        }
                        break; 
                    }
                    blockLines.add(nextLine);
                }
                
                String[] blockArray = blockLines.toArray(new String[0]);
                blocks[blockIndex] = new Block(blockArray);
                blockIndex++;
            }

            fileparser.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}