import java.util.Arrays;

public class Block {
    public String[][] block;
    public String[][][] calculatedPossBlocks;
    
    public Block(String[] lines) {
        int rows = lines.length;
        int cols = Arrays.stream(lines).mapToInt(String::length).max().orElse(0);

        block = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (j < lines[i].length()) {
                    block[i][j] = String.valueOf(lines[i].charAt(j));
                }
                else{
                    block[i][j] = ".";
                }
            }
        }
        generatePossibleBlocks();
    }
    
    // Generates all possible transformations (rotations and flips)
    private void generatePossibleBlocks() {
        calculatedPossBlocks = new String[8][3][3]; // 4 rotations * 2 flips
        
        String[][] centeredBlock = centerBlock(block);
        for (int i = 0; i < 4; i++) {
            calculatedPossBlocks[i] = centeredBlock;
            centeredBlock = rotate45(centeredBlock);
        }
        
        // Generate mirrored versions
        for (int i = 0; i < 4; i++) {
            calculatedPossBlocks[i + 4] = flipBlock(calculatedPossBlocks[i]);
        }
    }
    
    // Centers the block in a 3x3 grid
    private String[][] centerBlock(String[][] blk) {
        String[][] centered = { {".", ".", "."}, {".", ".", "."}, {".", ".", "."} };
        int offsetX = (3 - blk.length) / 2;
        int offsetY = (3 - blk[0].length) / 2;
        
        for (int i = 0; i < blk.length; i++) {
            for (int j = 0; j < blk[0].length; j++) {
                centered[i + offsetX][j + offsetY] = blk[i][j];
            }
        }
        return centered;
    }
    
    // Rotates the first layer of a 3x3 block by 45 degrees clockwise
    private String[][] rotate45(String[][] blk) {
        String[][] rotated = { {" ", " ", " "}, {" ", blk[1][1], " "}, {" ", " ", " "} };
        rotated[0][1] = blk[0][0];
        rotated[0][2] = blk[0][1];
        rotated[1][2] = blk[0][2];
        rotated[2][2] = blk[1][2];
        rotated[2][1] = blk[2][2];
        rotated[2][0] = blk[2][1];
        rotated[1][0] = blk[2][0];
        rotated[0][0] = blk[1][0];
        
        return rotated;
    }
    
    // Mirrors the block horizontally
    private String[][] flipBlock(String[][] blk) {
        String[][] flipped = new String[blk.length][blk[0].length];
        for (int i = 0; i < blk.length; i++) {
            for (int j = 0; j < blk[0].length; j++) {
                flipped[i][j] = blk[i][blk[0].length - j - 1];
            }
        }
        return flipped;
    }
    
    // Prints a block (debugging only)
    public void printBlock(String[][] blk) {
        for (String[] row : blk) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void printAllBlocks() {
        for (int i = 0; i < calculatedPossBlocks.length; i++) {
            System.out.println("Transformation " + (i + 1) + ":");
            printBlock(calculatedPossBlocks[i]);
        }
    }
}
