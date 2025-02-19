import java.util.Scanner;

public class Block {
    private String[][] block;

    public Block(Scanner fileparser)
    {
        block = new String[3][3];
        String tokens = fileparser.nextLine();
        char blockType = tokens.charAt(0);
        char[] temp = tokens.toCharArray();
        int i = 0;

        // Ambil nilai max dari lebar and tinggi dari blok yang nantinya dibaca untuk resize block pada akhir
        int maxL = 0;
        int maxT = 0;
        while(tokens.chars().anyMatch(ch -> ch == blockType)) {
            for (int j = 0; j < 3; j++) {
                if (j < temp.length) {
                    block[i][j] = Character.toString(temp[j]);
                    if (maxL < temp.length) {
                        maxL = temp.length;
                    }
                } else {
                    block[i][j] = "0";
                }
                
            }
            i++;
            try {
                tokens = fileparser.nextLine();
                temp = tokens.toCharArray();
            } catch (Exception e) {
                break;
            }
        }
        maxT = i;

        // Me-resize block 
        String[][] tempBlock = new String[maxT][maxL];
        for (int j = 0; j < maxT; j++) {
            for (int k = 0; k < maxL; k++) {
                tempBlock[j][k] = block[j][k];
            }
        }
        block = tempBlock;
    }

    // Rotasi block sebanyak 45 derajat searah jarum jam
    public void rotateBlock()
    {
        String[][] temp = new String[block[0].length][block.length];
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[0].length; j++) {
                temp[j][block.length - i - 1] = block[i][j];
            }
        }
        block = temp;
    }

    // Mencermin block secara horizontal
    public void flipBlock()
    {
        String[][] temp = new String[block.length][block[0].length];
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[0].length; j++) {
                temp[i][j] = block[i][block[0].length - j - 1];
            }
        }
        block = temp;
    }
    // Print block (debug)
    public void printBlock()
    {
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[0].length; j++) {
                System.out.print(block[i][j] + " ");
            }
            System.out.println();
        }
    }
}
