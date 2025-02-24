import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Board {
    public String[][] board;
    private static final String RESET = "\033[0m";
    private static final Color[] COLORS = {
        Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.MAGENTA, Color.CYAN,
        new Color(128, 0, 128), new Color(255, 0, 0), new Color(0, 255, 0), new Color(255, 255, 0),
        new Color(0, 0, 255), new Color(255, 0, 255), new Color(0, 255, 255), new Color(128, 128, 128),
        new Color(255, 165, 0), new Color(255, 140, 0), new Color(255, 69, 0), new Color(255, 255, 224),
        new Color(144, 238, 144), new Color(173, 216, 230), new Color(255, 182, 193),
        new Color(224, 255, 255), new Color(255, 0, 0), new Color(0, 255, 0), new Color(255, 255, 0),
        new Color(0, 0, 255), new Color(255, 0, 255)
    };
    private Map<String, String> colorMap;

    public Board(int N, int M, String mode, String[] maplist) {
        board = new String[N][M];
        if (mode.equals("CUSTOM")) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    board[i][j] = (maplist[i].charAt(j) == 'X') ? "." : "*";
                }
            }
        } else {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    board[i][j] = ".";
                }
            }
        }
        colorMap = new HashMap<>();
    }

    public void saveBoard(String fileName) {
        try (FileWriter myWriter = new FileWriter(fileName)) {
            for (String[] row : board) {
                for (String cell : row) {
                    myWriter.write(cell);
                }
                myWriter.write("\n");
            }
            System.out.println("Board successfully saved to " + new File(fileName).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving board: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveBoardInImage(String fileName) {
        int cellSize = 20;
        int width = board[0].length * cellSize;
        int height = board.length * cellSize;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("Arial", Font.PLAIN, cellSize));

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                String cell = board[i][j];
                int index = cell.charAt(0) - 'A';
                if (cell.equals(".")) {
                    g.setColor(Color.WHITE);
                } else if (index >= 0 && index < COLORS.length) {
                    g.setColor(COLORS[index]);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawString(cell, j * cellSize + 5, i * cellSize + cellSize - 5);
            }
        }
        g.dispose();

        try {
            File outputFile = new File(fileName);
            outputFile.getParentFile().mkdirs(); // Ensure directories exist
            ImageIO.write(image, "jpg", outputFile);
            System.out.println("Image successfully saved to " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving board image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                String cell = board[i][j];
                if (cell.equals(".")) {
                    System.out.print(cell + " ");
                } else {
                    String colorCode;
                    if (cell.matches("[A-Z]")) {
                        colorCode = getColorCode(COLORS[cell.charAt(0) - 'A']);
                    } else {
                        colorCode = "\033[37m"; // White for any other characters
                    }
                    System.out.print(colorCode + cell + RESET + " ");
                }
            }
            System.out.println();
        }
    }

    private String getColorCode(Color color) {
        if (color.equals(Color.RED)) return "\033[31m";
        if (color.equals(Color.GREEN)) return "\033[32m";
        if (color.equals(Color.YELLOW)) return "\033[33m";
        if (color.equals(Color.BLUE)) return "\033[34m";
        if (color.equals(Color.MAGENTA)) return "\033[35m";
        if (color.equals(Color.CYAN)) return "\033[36m";
        // Add more color mappings as needed
        return "\033[37m"; // Default to white
    }

    public boolean checkValidBlock(int x, int y, String[][] bl) {
        // Cek apakak blok valid (dapat diletakkan) pada x dan y dalam board
        for (int i = 0; i < bl.length; i++) {
            for (int j = 0; j < bl[0].length; j++) {
                if (!bl[i][j].equals(".")) {
                    if (x + i >= board.length || y + j >= board[0].length) {
                        return false;
                    }
                    if (!board[x + i][y + j].equals(".")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean checkFull() {
        // Cek kondisi menang atau jika board telah full (tidak ada character .)
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].equals(".")) {
                    return false;
                }
            }
        }
        return true;
    }
    public void placeBlocks(int i, int j, String[][] bl) {
        for (int x = 0; x < bl.length; x++) {
            for (int y = 0; y < bl[0].length; y++) {
                if (!bl[x][y].equals(".")) {
                    board[i + x][j + y] = bl[x][y];
                }
            }
        }
    }

    public void removeBlocks(int i, int j, String[][] bl) {
        for (int x = 0; x < bl.length; x++) {
            for (int y = 0; y < bl[0].length; y++) {
                if (!bl[x][y].equals(".")) {
                    board[i + x][j + y] = ".";
                }
            }
        }
    }
}